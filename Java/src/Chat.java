package src;
/**
 * Describes the Chat class of a chat system, contains users (members) and messages
 */

import java.io.Serializable;
import java.time.OffsetDateTime;

public class Chat implements Serializable{
    private final int chat_id;
    private final String chatType;
    private final String title;
    private final OffsetDateTime createdAt;

    public Chat(int chat_id, String chatType, String title, OffsetDateTime createdAt){
        this.chat_id = chat_id;
        this.chatType = chatType;
        this.title = title;
        this.createdAt = createdAt;
    }

    public int getChatId(){
        return this.chat_id;
    }

    public String getChatType(){
        return this.chatType;
    }

    public String getChatTitle(){
        return this.title;
    }

    public OffsetDateTime getCreatedAt(){
        return this.createdAt;
    }
}