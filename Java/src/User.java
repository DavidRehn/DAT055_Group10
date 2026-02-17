package src;

import java.io.Serializable;

/**
 * Describes the User class of a chat system
*/
public class User implements Serializable{
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

    @Override
    public String toString(){
        return ("[" + name + ", " + login + "]");
    }
};



