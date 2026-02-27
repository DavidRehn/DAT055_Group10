package src;

import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.*;
import java.net.Socket;


public class clientHandler implements Runnable{
    final private SocketChannel clientSocket;
    final private Selector se;
	final private SelectionKey sk;

	private boolean authenticated;
    private ChatUser user;
	
    public clientHandler(Selector s, SocketChannel c) throws IOException{
        clientSocket = c;
        c.configureBlocking(false);
        se=s;
		authenticated = false;
        sk = clientSocket.register(s, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        s.wakeup();
    }
    
    @Override
    public void run(){
        Database SQLJava = new Database();
        UserDAO userDAO1 = new UserDAO(SQLJava);
        ChatDAO Chats = new ChatDAO(SQLJava);
        try {
            try {
				
                if(authenticated){
                    Sendable request = (Sendable)receiveObject();
                    System.out.println("User sent: "+request.toString());
                    System.out.println("Server sent: "+Chats.getAllChats());

                    sendObject(Chats.getAllChats());
                }else{
                    Sendable request = (Sendable)receiveObject();
                    
                    
                    if(request.getMsgType().equals("login")){
                        if(!userDAO1.checkIfAccountExists("A","2").isEmpty()){
                            authenticated = true;
                            user=userDAO1.checkIfAccountExists("A","2").get();
                            System.out.println("User connected: "+user.toString());
                            authenticated=true;
                        }else{
                            System.out.println("User login failed");
                        }
                    }else{
                        System.out.println("Invalid user request");
                    }
                    System.out.println(request.toString());              
                }
                
                
                
                
                sk.interestOps(SelectionKey.OP_READ);
                se.wakeup();
            }catch (IOException e) {
                sk.cancel();
                System.out.println("Connection terminated");
            }
            
            
        } catch (Exception e) {
            
            System.out.println("Server could not print object: "+e.toString());
        }
    }
    
    
    private void sendObject(Object obj) throws IOException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objOut = new ObjectOutputStream(byteStream)) {
        objOut.writeObject(obj);
        }

        byte[] byteArray = byteStream.toByteArray();
        ByteBuffer lenBuffer = ByteBuffer.allocate(4);
        lenBuffer.putInt(byteArray.length);
        lenBuffer.flip();
        while (lenBuffer.hasRemaining()) {
            clientSocket.write(lenBuffer);
        }
        ByteBuffer msgBuffer = ByteBuffer.wrap(byteArray);
        while (msgBuffer.hasRemaining()) {
            clientSocket.write(msgBuffer);
        }
    }
    
    private Object receiveObject() throws IOException, ClassNotFoundException {
        // Get length of object
        ByteBuffer lengthBuffer = ByteBuffer.allocate(4);
        while (lengthBuffer.hasRemaining()) {
            if(clientSocket.read(lengthBuffer)==-1){
			throw new EOFException("Connection closed early");
			}
        }
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        // Get object
        ByteBuffer dataBuffer = ByteBuffer.allocate(length);
        while (dataBuffer.hasRemaining()){
            if(clientSocket.read(dataBuffer)==-1){
			throw new EOFException("Data transfere incomplete");
			}
        }
        dataBuffer.flip();
        byte[] bytes = new byte[length];
        dataBuffer.get(bytes);
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return objIn.readObject();
        }
        }
    
    
    
    
}
