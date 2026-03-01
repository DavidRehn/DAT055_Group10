package src;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
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
            System.out.println((ArrayList<String>) (message.getObject()));
            System.out.println("Logged in");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        while (true){
            
            message = null;
            try {
                message = (Sendable) cModel.receiveObject();
                System.out.println("received message");
                String msgType = message.getMsgType();
                if(msgType.equals("UI")){
                    view.UpdateChatList((ArrayList<String>) (message.getObject()));
                    System.out.println("Received chats");
                    System.out.println((ArrayList<String>) (message.getObject()));
                }else if(msgType.equals("MSG")){
                    System.out.println((ArrayList<Message>) message.getObject());
                }
                
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            
        }
        
    }



    
    
}
