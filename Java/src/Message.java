package src;

import java.io.Serializable;
import java.time.OffsetDateTime;
/** Abstract class to represent a message in a chat system.
 *  Message does not have any content fields.
 */
public abstract class Message implements Serializable{
    private int sender_id;
    private OffsetDateTime timestamp;
    private int message_id;
    private int chat_id;

    public Message(int senderId, OffsetDateTime time, int messageId, int chatId){
        this.sender_id = senderId;
        this.timestamp = time;
        this.message_id = messageId;
        this.chat_id = chatId;
    }

    /** Returns the sender of the message.
     * @return Sender id.
    */
    public int GetSenderID(){
        return sender_id;
    }

    /** Returns the timestamp when the message was sent.
     * @return timestamp.
     */
    public OffsetDateTime GetTimestamp(){
        return timestamp;
    }

    /** Returns the ID of the message.
     * @return Message ID.
     */
    public int GetMessageID(){
        return message_id;
    }

    /** Returns the chat the message belongs to.
     * @return Chat ID.
     */
    public int GetChatID(){
        return chat_id;
    }
};
