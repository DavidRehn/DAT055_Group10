package src;
import java.net.*;

public class ServerThread extends Thread{
    private ServerSocket serverSocket;

    public ServerThread(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run(){
        System.out.println("test");
        /* 
        while (true) { 
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Client connected");
            } catch (IOException e) {
                System.out.println("Client could not connect" + e);
            }
            
        }
        */

        /* 
        ObjectInputStream objIn = null;
        ObjectOutputStream objOut = null;

        try {
            objIn = new ObjectInputStream(clientSocket.getInputStream());
            objOut = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Could not create ObjStream");
        }

        // For testing
        try {
            Object request = objIn.readObject();
            System.out.println("Received: " + request);

            serverTest st = new serverTest();
            if(request.getClass().equals(UserRequest.class)){
                UserRequest r = (UserRequest)request;
                objOut.writeObject(st.GetUser(r.GetUsername()));
                objOut.flush();
            }

            objIn.close();
            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Server could not print object");
        }
            */
    }
}
