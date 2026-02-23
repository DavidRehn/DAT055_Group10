package src.Model.Entities;

/** Represents a user of a chat system.
 */
public class ChatUser extends User {
    private final int id;
    /** Creates a chatuser from the given parameters.
     * @param username Name of the user.
     * @param login Password.
     */
    public ChatUser(String username, String login, int id){
        super(username, login);
        this.id = id;
    }

    public int getId(){
        return this.id;
    }
}
