package src;

import java.time.LocalDateTime;
/**
 * Describes the Message class of a chat system, contains a message, sender and time of creation
 */
public class Message{
    private int sender_id;
    private LocalDateTime timestamp;
    private int message_id;
    private int chat_id;
    //private String msg_type;
    //private String content;

    public Message(int sender_id, LocalDateTime timestamp, int message_id, int chat_id, String msg_type, String content){
        this.sender_id = sender_id;
        this.timestamp = timestamp;
        this.message_id = message_id;
        this.chat_id = chat_id;
        //this.msg_type = msg_type;
        //this.content = content;
    }

    public int GetSenderID(){
        return sender_id;
    }

    public LocalDateTime GetTimestamp(){
        return timestamp;
    }

    public int GetMessageID(){
        return message_id;
    }

    public int GetChatID(){
        return chat_id;
    }
};
