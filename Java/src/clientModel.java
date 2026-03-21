package src;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import src.Model.Entities.ImageMessage;
import src.Model.Entities.Message;

public class clientModel extends Observable {
    private String currentChat;
    private final SocketChannel channel;
    private ArrayList<String> chats;
    private ArrayList<Message> messages;
    private String uiEvent;
    private boolean loggedIn;
    private String authError;
    private AddImageRequest chosenImage;

    public clientModel(SocketChannel channel) {
        currentChat = null;
        this.channel = channel;
        chats = new ArrayList<>();
        messages = new ArrayList<>();
        uiEvent = "";
        loggedIn = false;
        authError = "";
        chosenImage = null;
    }

    public void AddChat(String name) {
        chats.add(name);
    }

    public void RemoveChat(String name) {
        chats.remove(name);
    }

    public ArrayList<String> GetChats() {
        return chats;
    }

    public void SetCurrentChat(String chatName) {
        this.currentChat = chatName;
    }

    public String GetCurrentChat() {
        return currentChat;
    }

    public List<Message> GetMessages() {
        return messages;
    }

    public String GetUIEvent() {
        return uiEvent;
    }

    public boolean IsLoggedIn() {
        return loggedIn;
    }

    public String GetAuthError() {
        return authError;
    }

    public AddImageRequest getChosenImage(){
        return chosenImage;
    }

    public void setChosenImage(AddImageRequest img){
        this.chosenImage = img;
    }

    public void HandleLoginSuccess(List<String> chatNames) {
        chats = new ArrayList<>(chatNames);
        loggedIn = true;
        authError = "";
        uiEvent = "LOGIN_SUCCESS";
        setChanged();
        notifyObservers(this);
    }

    public void HandleChatListUpdate(List<String> chatNames) {
        chats = new ArrayList<>(chatNames);
        uiEvent = "CHAT_LIST_UPDATED";
        setChanged();
        notifyObservers(this);
    }

    public void HandleMessagesUpdate(List<Message> newMessages) {
        messages = new ArrayList<>(newMessages);
        uiEvent = "MESSAGES_UPDATED";
        setChanged();
        notifyObservers(this);
    }

    public void HandleAuthFailed(String errorMessage) {
        authError = errorMessage;
        uiEvent = "AUTH_FAILED";
        setChanged();
        notifyObservers(this);
    }

    public void SendObject(Object obj) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objOut = new ObjectOutputStream(byteStream)) {
            objOut.writeObject(obj);
        }

        byte[] byteArray = byteStream.toByteArray();
        ByteBuffer lenBuffer = ByteBuffer.allocate(4);
        lenBuffer.putInt(byteArray.length);
        lenBuffer.flip();
        while (lenBuffer.hasRemaining()) {
            channel.write(lenBuffer);
        }
        ByteBuffer msgBuffer = ByteBuffer.wrap(byteArray);
        while (msgBuffer.hasRemaining()) {
            channel.write(msgBuffer);
        }
    }

    public Object receiveObject() throws IOException, ClassNotFoundException {
        // Get length of object
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        while (lengthBuffer.hasRemaining()) {
            channel.read(lengthBuffer);
        }
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        // Get object
        ByteBuffer dataBuffer = ByteBuffer.allocate(length);
        while (dataBuffer.hasRemaining()) {
            channel.read(dataBuffer);
        }
        dataBuffer.flip();
        byte[] bytes = new byte[length];
        dataBuffer.get(bytes);
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return objIn.readObject();
        }
    }

    public void saveImage(GUI gui){
        clientModel cModel = gui.GetClientModel();
        System.out.println("add image");
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(gui);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            // Removes the format from the filename (because all valid files are converted to png)
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0) {
                fileName = fileName.substring(0, dotIndex);
            }
            try {
                BufferedImage img = ImageIO.read(selectedFile);
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ImageIO.write(img, "png", b);    // Automaticly converts all valid image formats to png
                byte[] imageBytes = b.toByteArray();
                if (img != null) {
                    cModel.setChosenImage(new AddImageRequest(imageBytes, fileName, new ImageMessage(LocalDateTime.now(), cModel.GetCurrentChat(), "image"), cModel.GetCurrentChat()));
                } else {
                    System.out.println("Not a supported image format.");
                }
            } catch (IOException a) {
                System.out.println("Error reading file.");
            }
        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("Canceled file upload");
        }
    }
}
