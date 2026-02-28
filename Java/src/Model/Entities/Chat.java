package src.Model.Entities;
import java.io.Serializable;

/** Abstract class thet describes the Chat class of a chat system, contains users (members) and messages.
 */
public class Chat implements Serializable{
    private String title;

    public Chat(String title){
        this.title = title;
    }

    /** Returns the title of the chat.
     * @return Title.
     */
    public String getChatTitle(){
        return this.title;
    }
}