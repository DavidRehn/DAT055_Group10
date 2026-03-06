package src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import src.Model.Entities.Message;

public class clientModel extends Observable {
    private String currentChat;
    private SocketChannel channel;
    private ArrayList<String> chats;
    private ArrayList<Message> messages;
    private String uiEvent;
    private boolean loggedIn;
    private String authError;

    public clientModel(SocketChannel channel) {
        currentChat = null;
        this.channel = channel;
        chats = new ArrayList<>();
        messages = new ArrayList<>();
        uiEvent = "";
        loggedIn = false;
        authError = "";
    }

    public void AddChat(String name) {
        chats.add(name);
    }

    public void RemoveChat(String name) {
        chats.remove(name);
    }

    public ArrayList<String> GetChats() {
        return chats;
    }

    public void SetCurrentChat(String chatName) {
        this.currentChat = chatName;
    }

    public String GetCurrentChat() {
        return currentChat;
    }

    public List<Message> GetMessages() {
        return messages;
    }

    public String GetUIEvent() {
        return uiEvent;
    }

    public boolean IsLoggedIn() {
        return loggedIn;
    }

    public String GetAuthError() {
        return authError;
    }

    public void HandleLoginSuccess(List<String> chatNames) {
        chats = new ArrayList<>(chatNames);
        loggedIn = true;
        authError = "";
        uiEvent = "LOGIN_SUCCESS";
        setChanged();
        notifyObservers(this);
    }

    public void HandleChatListUpdate(List<String> chatNames) {
        chats = new ArrayList<>(chatNames);
        uiEvent = "CHAT_LIST_UPDATED";
        setChanged();
        notifyObservers(this);
    }

    public void HandleMessagesUpdate(List<Message> newMessages) {
        messages = new ArrayList<>(newMessages);
        uiEvent = "MESSAGES_UPDATED";
        setChanged();
        notifyObservers(this);
    }

    public void HandleAuthFailed(String errorMessage) {
        authError = errorMessage;
        uiEvent = "AUTH_FAILED";
        setChanged();
        notifyObservers(this);
    }

    public void SendObject(Object obj) throws IOException {
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
        while (dataBuffer.hasRemaining()) {
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
