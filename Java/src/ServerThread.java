package src;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerThread extends Thread{
    private ServerSocketChannel serverSocket;
    private final ClientHandler clientHandler;
    private final Selector selector;
    

    public ServerThread() throws IOException{
        this.clientHandler = new ClientHandler();
        selector = Selector.open();
    }

    @Override
    public void run(){
        SocketChannel clientSocket;
        Iterator<SelectionKey> iter = null;
        Set<SelectionKey> selectedKeys;
        ExecutorService threads = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);

        // Starts the server
        try {
            serverSocket = ServerSocketChannel.open().bind(new InetSocketAddress(3333));
            serverSocket.configureBlocking(false);
            System.out.println("Server is running");
        } catch (IOException e) {
            System.out.println("Could not start server");
        }

        while(true) { 
            try {
                selector.select();
                selectedKeys = selector.selectedKeys();
                iter = selectedKeys.iterator();
            } catch (IOException e) {
            }
            while(iter.hasNext()){
                SelectionKey key = iter.next();
                iter.remove();

                if(key.isAcceptable()){
                    try {
                        clientSocket = serverSocket.accept();
                        clientHandler.AddClient(clientSocket);
                        System.out.println("Client connected");
                    }catch (IOException e) {
                        System.out.println("Client could not connect");
                    }
                }else if (key.isReadable()){
                    clientSocket = (SocketChannel) key.channel();

                    // read from socket here
                }
            }
            


            
        }
    }
}
