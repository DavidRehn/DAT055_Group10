package src;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;

import src.Model.DAO.*;
import src.Model.Entities.ChatUser;
import src.Model.Entities.GroupChat;
import src.Model.Entities.ImageMessage;
import src.Model.Entities.Message;
import src.Model.Entities.User;

public class clientHandler implements Runnable {
    private static final CopyOnWriteArrayList<clientHandler> CONNECTED_HANDLERS = new CopyOnWriteArrayList<>();
    private static final String SERVER_IMAGE_DIR = "Java/src/ServerImages/";
    private static final String CLIENT_IMAGE_DIR = "Java/src/ClientImages/";

    final private SocketChannel clientSocket;
    final private Selector se;
    final private SelectionKey sk;

    private boolean authenticated;
    private User user;
    private String currentChatFocus;
    final private DataStorage D_CON;

    public clientHandler(Selector s, SocketChannel c, DataStorage d) throws IOException {
        clientSocket = c;
        c.configureBlocking(false);
        se = s;
        D_CON = d;
        authenticated = false;
        currentChatFocus = null;
        sk = clientSocket.register(s, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        CONNECTED_HANDLERS.add(this);
        s.wakeup();
    }

    @Override
    public void run() {
        try {
            Sendable request = (Sendable) receiveObject();
            System.out.println("Received request: " + request.getMsgType());
            ((RunnableRequest)request).runRequest(D_CON,this);
            
            /*if (authenticated) {

                if (request.getMsgType().equals("createChat")) {
                    ChatCreateMsg r = (ChatCreateMsg) request;
                    if (!D_CON.ChatExists((String) r.getObject())) {
                        try {
                            D_CON.AddChat(new GroupChat((String) r.getObject()));
                            D_CON.AddUserToChat((String) r.getObject(), user.getUserName());
                            broadcastChatsToUser();
                        } catch (RuntimeException ex) {
                            sendObject(new messageWrapper("Could not create chat.", "AUTH_FAIL"));
                        }
                    } else {
                        sendObject(new messageWrapper("Chat already exists", "AUTH_FAIL"));
                    }

                } else if (request.getMsgType().equals("getMessages")) {

                    String chat = (String) request.getObject();
                    currentChatFocus = chat;
                    if (!D_CON.ChatUserExists(user, chat)) {
                        D_CON.AddUserToChat(chat, user.getUserName());
                        System.out.println("Added user " + user.getUserName() + " to chat " + chat);
                    }
                    ArrayList<Message> messages = D_CON.GetMessages(chat);
                    sendObject(new messageWrapper(messages, "MSG"));

                } else if (request.getMsgType().equals("AddMsg")) {
                    System.out.println(user);

                    Message r = (Message) request.getObject();
                    System.out.println(r.GetChat());
                    System.out.println(D_CON.ChatUserExists(user, r.GetChat()));
                    System.out.println(this);
                    if (D_CON.ChatUserExists(user, r.GetChat())) {
                        System.out.println("a");
                        r.SetSender(user.getUserName());
                        D_CON.AddMessage(r);
                        broadcastMessages(r.GetChat());
                        System.out.println("Added message");
                    }
                } else if (request.getMsgType().equals("AddImage")) {
                    AddImageRequest r = (AddImageRequest) request;
                    ImageMessage m = (ImageMessage) r.getObject();
                    m.SetSender(user.getUserName());
                    byte[] img = (byte[]) r.GetImage();
                    String fileName = r.GetFileName();
                    ByteArrayInputStream b = new ByteArrayInputStream(img);
                    BufferedImage image = ImageIO.read(b);
                    File outputDir = new File(SERVER_IMAGE_DIR);
                    if (!outputDir.exists()) {
                        outputDir.mkdirs();
                    }
                    File outputFile = new File(outputDir, fileName + ".png");
                    ImageIO.write(image, "png", outputFile);
                    m.SetImagePath(CLIENT_IMAGE_DIR + fileName + ".png");
                    D_CON.AddMessage(m);
                    System.out.println("saved image");

                    // Send back the image to be saved at client side (Should be done by observable)
                    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                    ImageIO.write(image, "png", bOut);    // Automaticly converts all valid image formats to png (should already be png if sent from client)
                    byte[] imageBytes = bOut.toByteArray();
                    broadcastImage(r.GetChat(), imageBytes, fileName);
                    broadcastMessages(r.GetChat());
                    System.out.println("Sent image response");
                }


            } else {
                if (request.getMsgType().equals("login")) {
                    LoginRequest r = (LoginRequest) request;
                    if (D_CON.UserExists((User) r.getObject())) {
                        authenticated = true;
                        user = (ChatUser) r.getObject();
                        System.out.println("User logged in: " + r.GetUsername());
                        sendObject(new messageWrapper(D_CON.GetAllChats(), "UI"));
                    } else {
                        sendObject(new messageWrapper("Invalid username or password", "AUTH_FAIL"));
                    }

                } else {
                    System.out.println("Invalid user request");
                }
                System.out.println(request.toString());
            }
        */
        } catch (IOException e) {
            sk.cancel();
            CONNECTED_HANDLERS.remove(this);
            System.out.println("Connection terminated");
        } catch (Exception e) {
            System.out.println("Error while handling request");
            e.printStackTrace();
            try {
                sendObject(new messageWrapper("Server error: " + e.getClass().getSimpleName(), "AUTH_FAIL"));
            } catch (IOException ignored) {
            }
        } finally {
            if (!sk.isValid()) {
                CONNECTED_HANDLERS.remove(this);
            }
            if (sk.isValid()) {
                sk.interestOps(SelectionKey.OP_READ);
                se.wakeup();
            }
        }
    }


    private void sendObject(Object obj) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objOut = new ObjectOutputStream(byteStream)) {
            objOut.writeObject(obj);
        }

        byte[] byteArray = byteStream.toByteArray();
        ByteBuffer lenBuffer = ByteBuffer.allocate(4);
        lenBuffer.putInt(byteArray.length);
        lenBuffer.flip();
        while (lenBuffer.hasRemaining()) {
            clientSocket.write(lenBuffer);
        }
        ByteBuffer msgBuffer = ByteBuffer.wrap(byteArray);
        while (msgBuffer.hasRemaining()) {
            clientSocket.write(msgBuffer);
        }
    }

    private void broadcastMessages(String chat) {
        ArrayList<Message> messages = D_CON.GetMessages(chat);
        for (clientHandler handler : CONNECTED_HANDLERS) {
            if (handler.authenticated && chat.equals(handler.currentChatFocus)) {
                try {
                    handler.sendObject(new messageWrapper(messages, "MSG"));
                } catch (IOException e) {
                    handler.sk.cancel();
                    CONNECTED_HANDLERS.remove(handler);
                }
            }
        }
    }

    private void broadcastChatsToUser() {
        ArrayList<String> chats = D_CON.GetAllChats();
        for (clientHandler handler : CONNECTED_HANDLERS) {
            if (handler.authenticated) {
                try {
                    handler.sendObject(new messageWrapper(chats, "UI"));
                } catch (IOException e) {
                    handler.sk.cancel();
                    CONNECTED_HANDLERS.remove(handler);
                }
            }
        }
    }

    private void broadcastImage(String chat, byte[] imageBytes, String fileName) {
        messageWrapper wrapper = new messageWrapper(new imageWrapper(imageBytes, fileName), "addImg");
        for (clientHandler handler : CONNECTED_HANDLERS) {
            if (handler.authenticated && chat.equals(handler.currentChatFocus)) {
                try {
                    handler.sendObject(wrapper);
                } catch (IOException e) {
                    handler.sk.cancel();
                    CONNECTED_HANDLERS.remove(handler);
                }
            }
        }
    }

    private Object receiveObject() throws IOException, ClassNotFoundException {
        // Get length of object
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        while (lengthBuffer.hasRemaining()) {
            if (clientSocket.read(lengthBuffer) == -1) {
                throw new EOFException("Connection closed early");
            }
        }
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        // Get object
        ByteBuffer dataBuffer = ByteBuffer.allocate(length);
        while (dataBuffer.hasRemaining()) {
            if (clientSocket.read(dataBuffer) == -1) {
                throw new EOFException("Data transfere incomplete");
            }
        }
        dataBuffer.flip();
        byte[] bytes = new byte[length];
        dataBuffer.get(bytes);
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return objIn.readObject();
        }
    }
public User getUser(){
    return this.user;
}
public Boolean getAuthenticated(){
    return this.authenticated;
}
public String getFocus(){
    return this.currentChatFocus;
}
public String getServerImageDir(){
    return SERVER_IMAGE_DIR;
}
public String getClientImageDir(){
    return CLIENT_IMAGE_DIR;
}
public void setUser(User u){
    this.user=u;
}
public void setAuthenticated(Boolean a){
    this.authenticated=a;
}
public void setFocus(String c){
    this.currentChatFocus;
}


}
