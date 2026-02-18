package src;

import java.io.*;
import java.net.Socket;

public class client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 3333);
            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream())) 
        {
            // right now this only sends an object (for testing)
            String username = "Sam";
            System.out.println("Connected to server");
            objOut.writeObject(new UserRequest(username));
            objOut.flush();
            System.out.println("Requested user: " + username);
            
            User u = (User)objIn.readObject();
            System.out.println("Received user: " + u);

            objIn.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
