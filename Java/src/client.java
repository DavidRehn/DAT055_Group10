package src;

import java.io.*;
import java.net.Socket;

public class client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 3333);
            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream())) 
        {
            // right now this only sends an object (for testing)
            System.out.println("Connected to server");
            objOut.writeObject(new ChatUser("Ben", "ABC123"));
            objOut.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
