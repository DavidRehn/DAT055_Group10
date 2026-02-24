package src;

import java.net.Socket;

public abstract class Connection {
    private int clientId;
    private Socket clientSocket;

    public Connection(int client, Socket socket){
        this.clientId = client;
        this.clientSocket = socket;
    }

    public int GetClientId(){
        return clientId;
    }

    public Socket GetClientSocket(){
        return clientSocket;
    }
}
