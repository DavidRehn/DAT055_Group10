package src;
import java.io.Serializable;
import java.time.OffsetDateTime;

/** Abstract class thet describes the Chat class of a chat system, contains users (members) and messages.
 */
public class Chat implements Serializable{
    private int chat_id;
    private String title;
    private OffsetDateTime createdAt;

    public Chat(int id, String title, OffsetDateTime timestamp){
        this.chat_id = id;
        this.title = title;
        this.createdAt = timestamp;
    }

    /** Returns the chat ID.
     * @return ID.
     */
    public int getChatId(){
        return this.chat_id;
    }

    /** Returns the title of the chat.
     * @return Title.
     */
    public String getChatTitle(){
        return this.title;
    }

    /** Returns the timestamp when the chat was created.
     * @return Timestamp when created.
     */
    public OffsetDateTime getCreatedAt(){
        return this.createdAt;
    }
}