package src;
import src.Model.Entities.User;

public class clientModel {
    private User currentUser;
    private int currentChat;

    public clientModel(){
        currentUser = null;
        currentChat = 0;
    }

    public User GetUser(){
        return currentUser;
    }

    public int GetCurrentChat(){
        return currentChat;
    }
}
