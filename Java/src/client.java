package src;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import src.Model.Entities.Message;

public class client {
    public static void main(String[] args) {
        SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 3333));
            System.out.println("Connected to server");
        } catch (IOException e){
            System.out.println("Could not connect to server");
        }
        

        clientModel cModel = new clientModel(socketChannel);
        View view = new View(cModel);
        view.ShowLogin();

        /* ArrayList<String> a = new ArrayList<>();
        a.add("Chat1");
        a.add("Chat2");
        view.UpdateChatList(a);*/
        Sendable message = null;
        try {
            message = (Sendable) cModel.receiveObject();
            view.RemoveLoginScreen();
            view.ShowHomeScreen((ArrayList<String>) (message.getObject()));
            System.out.println("Logged in");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        boolean firstMsgUpdate = true;
        while (true){
            
            message = null;
            
            try {
                message = (Sendable) cModel.receiveObject();
                String msgType = message.getMsgType();

                if(msgType.equals("UI")){
                    view.UpdateChatList((ArrayList<String>) (message.getObject()));
                    System.out.println("Received chats");

                }else if(msgType.equals("MSG")){
                    ArrayList<Message> messages = (ArrayList<Message>)message.getObject();
                    System.out.println(messages);
                    if(firstMsgUpdate){
                        view.ShowChatroom(messages);
                        firstMsgUpdate = false;
                    }
                    view.UpdateMessages(messages);
                    System.out.println("Received Messages");
                }else if(msgType.equals("addImg")){
                    imageWrapper imgWrapper = (imageWrapper)message.getObject();
                    byte[] imageBytes = imgWrapper.GetImage();
                    ByteArrayInputStream b = new ByteArrayInputStream(imageBytes);
                    BufferedImage image = ImageIO.read(b);
                    String filename = imgWrapper.GetFilename();
                    File outputFile = new File(new File("src/ClientImages/"), filename + ".png");
                    ImageIO.write(image, "png", outputFile);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            
        }
        
    }



    
    
}
