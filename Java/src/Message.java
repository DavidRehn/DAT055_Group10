package src;

import java.io.Serializable;
import java.time.LocalDateTime;
/** Abstract class to represent a message in a chat system.
 *  Message does not have any content fields.
 */
public abstract class Message implements Serializable{
    private int sender_id;
    private LocalDateTime timestamp;
    private int message_id;
    private int chat_id;

    /** Returns the sender of the message.
     * @return Sender id.
    */
    public int GetSenderID(){
        return sender_id;
    }

    /** Returns the timestamp when the message was sent.
     * @return timestamp.
     */
    public LocalDateTime GetTimestamp(){
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
