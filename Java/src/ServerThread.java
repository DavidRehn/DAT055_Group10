package src;
import java.io.*;
import java.net.*;

public class ServerThread extends Thread{
    private Socket clientSocket;

    public ServerThread(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){
        ObjectInputStream objIn = null;

        try {
            objIn = new ObjectInputStream(clientSocket.getInputStream());
        } catch (Exception e) {
            System.out.println("Could not create ObjStream");
        }
        // Only receives an objects and prints it (for testing)
        try {
            User u = (User)objIn.readObject();
            System.out.println(u);
        } catch (Exception e) {
            System.out.println("Server could not print object");
        }
        
    }
}
