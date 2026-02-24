package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server{
    public static void main(String[] args) {
        ServerSocket serverSocket;
        ExecutorService threads = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);

        try {
            serverSocket = new ServerSocket(3333);
            ServerThread serverThread = new ServerThread(serverSocket);
            serverThread.start();
            System.out.println("Server is running");
        } catch (IOException e) {
            System.out.println("Could not start server");
        }
    }
}




