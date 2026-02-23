package src.Model.Entities;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import src.Message;

/** Represents a Groupchat containing multiple Users.
 */
public class GroupChat extends Chat {
    private ArrayList<User> members;
    private ArrayList<Message> messages;

    /** Creates a groupchat from the given parameters.
     * @param id Chat ID.
     * @param title Name of the chat.
     * @param createdAt Timestamp when the chat was created.
     */
    public GroupChat(int id, String title, OffsetDateTime createdAt){
        super(id, title, createdAt);
        members = new ArrayList<>();
    }

    /** Returns the list of members of the groupchat.
     * @return List of members.
     */
    public ArrayList<User> GetMembers(){
        return members;
    }

    // NEEDS AN ADD USER METHOD TO ADD USER TO THE CHAT, SIMILIAR TO test.java ADDMEMBER

    /** Returns the list of sent messages.
     * @return List of messages.
     */
    public ArrayList<Message> GetMessages(){
        return messages;
    }
}
