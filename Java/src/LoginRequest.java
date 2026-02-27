package src;
import java.io.Serializable;
import src.Model.Entities.ChatUser;
public class LoginRequest extends Sendable implements Serializable
{
    ChatUser user;
    LoginRequest(ChatUser u){
        super("login");
        user=u;
    }
    public Object getObject(){
        return user;
    }
    public String toString(){
        return user.toString();
    }
}
