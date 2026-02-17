package src;

import java.io.*;
import java.net.*;

public class server{
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(3333);
            System.out.println("Server is running");
        } catch (IOException e) { 
            System.out.println("Could not start server");
        }

        while (true) { 
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Client could not connect" + e);
            }
            new ServerThread(clientSocket).start();
            System.out.println("Client connected");
        }
    }
}



