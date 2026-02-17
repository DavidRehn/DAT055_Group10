package src;

import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3333);
            System.out.println("Server is running");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            DataInputStream dIn = new DataInputStream(socket.getInputStream());
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

            serverSocket.close();
            socket.close();
            dIn.close();
            dOut.flush();
        } catch (IOException e) {
        }
    }
}
