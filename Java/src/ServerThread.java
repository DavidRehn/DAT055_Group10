package src;
import java.io.*;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerThread extends Thread{
    private ServerSocketChannel serverSocket;
    private ClientHandler clientHandler;

    public ServerThread(ServerSocketChannel serverSocket){
        this.serverSocket = serverSocket;
        this.clientHandler = new ClientHandler();
    }

    @Override
    public void run(){
        SocketChannel clientSocket;
        while (true) { 
            try {
                clientSocket = serverSocket.accept();
                clientHandler.AddClient(clientSocket);
                System.out.println("Client connected");
            } catch (IOException e) {
                System.out.println("Client could not connect");
            }
        }
    }
}
