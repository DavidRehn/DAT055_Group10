package src;
/**
 * Describes the User class of a chat system
*/
public class User{
    // TODO
    private String name;
    private String login;
    
    // Konstruktor
    public User(String name, String login){
        this.name = name;
        this.login = login;
    }

    public String getUserName(){
        return this.name;
    }

};



