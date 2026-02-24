package src;
import java.net.Socket;

public class ClientConnection extends Connection {
    
    public ClientConnection(int clientId, Socket socket){
        super(clientId, socket);
    }
}
