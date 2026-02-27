package src;
import src.Model.Entities.User;

public class clientModel {
    private User currentUser;

    public clientModel(){
        currentUser = null;
    }

    public User GetUser(){
        return currentUser;
    }
}
