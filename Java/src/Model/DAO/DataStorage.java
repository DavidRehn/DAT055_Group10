package src.Model.DAO;
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
}
