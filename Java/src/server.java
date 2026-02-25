package src;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server{
    public static void main(String[] args) {
        ServerSocketChannel serverSocket;
        ExecutorService threads = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);

        try {
            serverSocket = ServerSocketChannel.open().bind(new InetSocketAddress(3333));
            ServerThread serverThread = new ServerThread(serverSocket);
            serverThread.start();
            System.out.println("Server is running");
        } catch (IOException e) {
            System.out.println("Could not start server");
        }
    }
}




