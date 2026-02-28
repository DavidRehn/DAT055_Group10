package src.Model.Entities;

import java.util.ArrayList;

/** Represents a Groupchat containing multiple Users.
 */
public class GroupChat extends Chat {
    private ArrayList<User> members;
    private ArrayList<Message> messages;

    /** Creates a groupchat from the given parameters.
     * @param title Name of the chat.
     */
    public GroupChat(String title){
        super(title);
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
