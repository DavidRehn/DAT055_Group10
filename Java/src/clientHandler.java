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
            
            UserRequest request = reciveMsg();
            System.out.println("Received: " + request.getUsername());
            
            
            
            
        } catch (Exception e) {
            System.out.println("Server could not print object");
        }
    }
    
    
    void sendMsg(Serializable output) throws IOException {

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    
        try (ObjectOutputStream objOut = new ObjectOutputStream(byteStream)) {
            objOut.writeObject(output);
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
    
    UserRequest reciveMsg() throws IOException, ClassNotFoundException{
        ByteBuffer lenBuffer = ByteBuffer.allocate(4);
        while (lenBuffer.hasRemaining()){
            if(clientSocket.read(lenBuffer)==-1){
                throw new EOFException("Connection closed prematurely");
            }
        }
        lenBuffer.flip();
        int len = lenBuffer.getInt();
        ByteBuffer msgBuffer = ByteBuffer.allocate(len);
        while(msgBuffer.hasRemaining()){
            if(clientSocket.read(msgBuffer)==-1){
                throw new EOFException("Data transfere incomplete");
            }
        }
        msgBuffer.flip();
        byte[] byteStream = new byte[len];
        msgBuffer.get(byteStream);
        
        
        try(ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(byteStream))){
            return (UserRequest) objIn.readObject();
        }
        
        
    }
    
    
    
    
}
