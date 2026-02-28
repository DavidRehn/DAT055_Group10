package src;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.*;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import src.Model.DAO.*;

public class server{

    public static void main(String[] args) {
        Database SQLDatabase = new Database();
        ServerSocketChannel serverSocket = null;
        SocketChannel clientSocket = null;
        ExecutorService threads = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        
        
        Selector selector;
        Set<SelectionKey> selectedKeys;
        Iterator<SelectionKey> iter;
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(3333));
            serverSocket.configureBlocking(false);
            
            selector = Selector.open();
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            
            
            System.out.println("Server is running");
            while(true) { 
                try {
                    selector.select();
                    selectedKeys = selector.selectedKeys();
                    iter = selectedKeys.iterator();
                    while(iter.hasNext()){
                        SelectionKey key = iter.next();
                        iter.remove();
                        try {
                            // Set up connection
                            if(key.isAcceptable()){
                                SocketChannel client = serverSocket.accept();
                                new clientHandler(selector, client, SQLDatabase);
                                System.out.println("Client connected");
                            } 
                            // Read from channel
                            else if(key.isReadable()){
                                
                                key.interestOps(SelectionKey.OP_WRITE);
                                System.out.println("Reading");
                                threads.execute((clientHandler)key.attachment());
                               
                                }
                            } catch(Exception e){
                                System.out.println("Client closed connection");
                                key.cancel();
                                try{ key.channel().close(); } catch(IOException a){}
                            }
                            
                            }
                } catch (IOException e) {
                    
                    }
                

            }
        } catch (IOException e) { 
            System.out.println("Could not start server");
        }
        
        
    }
}
