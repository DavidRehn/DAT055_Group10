package src.Model.Entities;

import java.io.Serializable;
import java.time.LocalDateTime;

/** Abstract class to represent a message in a chat system.
 *  Message does not have any content fields.
 */
public abstract class Message implements Serializable{
    private String sender;
    private LocalDateTime timestamp;
    private String chat;
    private String msgType;

    public Message(String sender, LocalDateTime time, String chat, String type){
        this.sender = sender;
        this.timestamp = time;
        this.chat = chat;
        this.msgType = type;
    }

    /** Returns the sender of the message.
     * @return Sender id.
    */
    public String GetSender(){
        return sender;
    }

    /** Returns the timestamp when the message was sent.
     * @return timestamp.
     */
    public LocalDateTime GetTimestamp(){
        return timestamp;
    }

    /** Returns the chat the message belongs to.
     * @return Chat ID.
     */
    public String GetChat(){
        return chat;
    }

    public String GetType(){
        return msgType;
    }
};
