package src;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private ServerSocket serverSocket;
    private ArrayList<Connection> connections;

    public ServerThread(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        this.connections = new ArrayList<>();
    }

    @Override
    public void run(){
        Socket clientSocket;
        ObjectOutputStream objOut;
        while (true) { 
            try {
                clientSocket = serverSocket.accept();
                objOut = new ObjectOutputStream(clientSocket.getOutputStream());
                int id = 0; // Placeholder
                connections.add(new ClientConnection(id, clientSocket));
                System.out.println("Client with id " + id + "connected");
            } catch (IOException e) {
                System.out.println("Client could not connect");
            }
        }

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
