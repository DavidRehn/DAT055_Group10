package src.Model.Entities;

import java.io.Serializable;

/** Abstract class that describes the User class of a chat system
*/
public abstract class User implements Serializable{
    private String name;
    private String login;
    
    /** Creates a user from the given parameters.
     * @param name Username.
     * @param login Password.
     */
    public User(String name, String login){
        this.name = name;
        this.login = login;
    }

    /** Rerturns the username of the user.
     * @return Username.
     */
    public String getUserName(){
        return this.name;
    }

    public String GetLogin(){
        return login;
    }

    /** Returns a string containing the users name ans password.
     * @return String.
     */
    @Override
    public String toString(){
        return ("[" + name + ", " + login + "]");
    }

    // ADD METHOD TO GET USER ID? OR SAVE IT IN THE CLASS AS WELL 
};



