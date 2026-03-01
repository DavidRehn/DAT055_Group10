package src.Model.DAO;
import java.util.ArrayList;
import src.Model.Entities.*;

public interface DataStorage {
    public void AddChat(Chat chat);

    public void AddUserToChat(String chatTitle, String username);

    public void AddUser(User user);

    public int RemoveUser(String username);

    public boolean UserExists(User user);

    public Chat GetChat(String title);

    public void AddMessage(Message message);

    public ArrayList<String> GetAllChats();

    public boolean ChatExists(String title);

    public ArrayList<Message> GetMessages(String chat);
}
