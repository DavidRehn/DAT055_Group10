import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.nio.*;
import java.net.Socket;


public class clientHandler implements Runnable{
    final private SocketChannel clientSocket;
    final private SelectionKey sk;
    public clientHandler(Selector s, SocketChannel c) throws IOException{
        clientSocket = c;
        c.configureBlocking(false);
        
        sk = clientSocket.register(s, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        s.wakeup();
    }
    
    @Override
    public void run(){
        
        
        try {
            
            Object request = receiveObject();
            System.out.println("Received");
            sendObject(new UserRequest("a"));
            
            
            
        } catch (Exception e) {
            System.out.println("Server could not print object");
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
            clientSocket.read(lengthBuffer);
        }
        lengthBuffer.flip();
        int length = lengthBuffer.getInt();

        // Get object
        ByteBuffer dataBuffer = ByteBuffer.allocate(length);
        while (dataBuffer.hasRemaining()){
            clientSocket.read(dataBuffer);
        }
        dataBuffer.flip();
        byte[] bytes = new byte[length];
        dataBuffer.get(bytes);
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return objIn.readObject();
        }
        }
    
    
    
    
}
