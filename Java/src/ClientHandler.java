package src;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class ClientHandler {
    private ArrayList<SocketChannel> clients;

    public ClientHandler(){
        clients = new ArrayList<>();
    }

    public void AddClient(SocketChannel client){
        clients.add(client);
    }

    public ArrayList<SocketChannel> GetClients(){
        return clients;
    }
}
