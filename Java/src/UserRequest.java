package src;

import java.io.Serializable;

/** Request to get a user.
 */
public class UserRequest implements Serializable, Request{
    private String username;

    /** Create a request for user with givan username.
     * @param username Username.
     */
    public UserRequest(String username){
        this.username = username;
    }

    /** Retruns the requested username.
     * @return
     */
    public String GetUsername(){
        return username;
    }

    
    @Override
    public String toString(){
        return ("[Request: " + username + "]");
    }
}
