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
    private static final String CLIENT_IMAGE_DIR = "Java/src/ClientImages/";

    public static void main(String[] args) {
        SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 3333));
            System.out.println("Connected to server");
        } catch (IOException e) {
            System.out.println("Could not connect to server");
        }


        clientModel cModel = new clientModel(socketChannel);
        View view = new View(cModel);
        view.ShowLogin();

        /* ArrayList<String> a = new ArrayList<>();
        a.add("Chat1");
        a.add("Chat2");
        view.UpdateChatList(a);*/
        messageWrapper message = null;
        while (true) {

            message = null;

            try {
                message = (messageWrapper) cModel.receiveObject();
                MsgType msgType = message.getMsg();

                if (msgType == MsgType.UI) {
                    ArrayList<String> chats = (ArrayList<String>) (message.getObject());
                    System.out.println("Received chats: " + chats);
                    if (!cModel.IsLoggedIn()) {
                        cModel.HandleLoginSuccess(chats);
                        System.out.println("Authenticated");
                    } else {
                        cModel.HandleChatListUpdate(chats);
                        System.out.println("Received chats");
                    }

                } else if (msgType == MsgType.MSG) {
                    ArrayList<Message> messages = (ArrayList<Message>) message.getObject();
                    System.out.println(messages);
                    cModel.HandleMessagesUpdate(messages);
                    System.out.println("Received Messages");
                } else if (msgType == MsgType.AUTH_FAIL) {
                    cModel.HandleAuthFailed((String) message.getObject());
                } else if (msgType == MsgType.ADD_IMG) {
                    imageWrapper imgWrapper = (imageWrapper) message.getObject();
                    byte[] imageBytes = imgWrapper.GetImage();
                    ByteArrayInputStream b = new ByteArrayInputStream(imageBytes);
                    BufferedImage image = ImageIO.read(b);
                    String filename = imgWrapper.GetFilename();
                    File outputDir = new File(CLIENT_IMAGE_DIR);
                    if (!outputDir.exists()) {
                        outputDir.mkdirs();
                    }
                    File outputFile = new File(outputDir, filename + ".png");
                    ImageIO.write(image, "png", outputFile);
                }
            } catch (IOException | ClassNotFoundException e) {
            }


        }

    }


}
