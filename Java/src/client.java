package src;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class client {
    public static void main(String[] args) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 3333));
            System.out.println("Connected to server");
        } catch (IOException e){
            System.out.println("Could not connect to server");
        }

        


        // for testing
        try {
            SendObject(socketChannel, new ChatUser("Ben", "ABC123"));
            System.out.println("Object sent");
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        
    }

    private static void SendObject(SocketChannel channel, Object obj) throws IOException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objOut = new ObjectOutputStream(byteStream)) {
        objOut.writeObject(obj);
        }

        byte[] byteArray = byteStream.toByteArray();
        ByteBuffer lenBuffer = ByteBuffer.allocate(4);
        lenBuffer.putInt(byteArray.length);
        lenBuffer.flip();
        while (lenBuffer.hasRemaining()) {
            channel.write(lenBuffer);
	    }
        ByteBuffer msgBuffer = ByteBuffer.wrap(byteArray);
        while (msgBuffer.hasRemaining()) {
	        channel.write(msgBuffer);
	    }
    }
}
