package src;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import src.Model.Entities.User;

public class clientModel {
    private User currentUser;
    private int currentChat;
    private SocketChannel channel;

    public clientModel(SocketChannel channel){
        currentUser = null;
        currentChat = 0;
        this.channel = channel;
    }

    public User GetUser(){
        return currentUser;
    }

    public int GetCurrentChat(){
        return currentChat;
    }

    public void SendObject(Object obj) throws IOException{
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

    public Object receiveObject() throws IOException, ClassNotFoundException {
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
