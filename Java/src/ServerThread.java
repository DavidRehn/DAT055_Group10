package src;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerThread extends Thread{
    private ServerSocketChannel serverSocket;
    private final Selector selector;
    

    public ServerThread() throws IOException{
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
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
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
            while(iter.hasNext()){      // For all channels with new actions
                SelectionKey key = iter.next();
                iter.remove();

                //if (!key.isValid()) continue; // skip cancelled keys

                try {
                    // Set up connection
                    if(key.isAcceptable()){
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        System.out.println("Client connected");
                    } 
                    // Read from channel
                    else if(key.isReadable()){
                        SocketChannel client = (SocketChannel) key.channel();
                        try {
                            Object obj = receiveObject(client);
                            System.out.println((ChatUser) obj); // for testing  
                        } catch (ClassNotFoundException e) {}
                    }
                } catch(IOException e){
                    System.out.println("Client closed connection");
                    key.cancel();
                    try{ key.channel().close(); } catch(IOException a){}
                }
            }
        }
    }

    private Object receiveObject(SocketChannel channel) throws IOException, ClassNotFoundException {
        // Get length of object
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        while (lengthBuffer.hasRemaining()) {
            channel.read(lengthBuffer);
        }
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        // Get object
        ByteBuffer dataBuffer = ByteBuffer.allocate(length);
        while (dataBuffer.hasRemaining()){
            channel.read(dataBuffer);
        }
        dataBuffer.flip();
        byte[] bytes = new byte[length];
        dataBuffer.get(bytes);
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return objIn.readObject();
        }
    }
}

