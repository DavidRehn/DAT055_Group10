package src;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

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
        

        while (true){
            Sendable message = null;
            try {
                message = (Sendable) cModel.receiveObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            String mesasgeType = message.getMsgType();

            if (mesasgeType.equals("GlobalChatList") && cModel.GetUser() != null){
                //view.UpdateChatList((ArrayList<String>) (message.GetObject());
            }
        }
        
    }



    
    
}
