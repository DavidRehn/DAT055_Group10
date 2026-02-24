package src;

import java.net.Socket;

public abstract class Connection {
    private int clientId;
    private Socket clientSocket;

    public Connection(int clientId, Socket socket){
        this.clientId = clientId;
        this.clientSocket = socket;
    }

    public int GetClientId(){
        return clientId;
    }

    public Socket GetClientSocket(){
        return clientSocket;
    }
}
