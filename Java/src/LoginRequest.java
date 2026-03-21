package src;

import java.io.IOException;
import src.Model.DAO.DataStorage;
import src.Model.Entities.ChatUser;
import src.Model.Entities.User;
public class LoginRequest extends Sendable implements RunnableRequest {
    ChatUser user;

    public LoginRequest(ChatUser u) {
        super("login");
        user = u;
    }

    public Object getObject() {
        return user;
    }

    public String toString() {
        return user.toString();
    }

    public String GetUsername() {
        return user.getUserName();
    }

    public String GetLogin() {
        return user.GetLogin();
    }

    public ChatUser GetUser() {
        return user;
    }
    public void runRequest(DataStorage D_CON, HandlerInterface h){
        try {
            if (D_CON.UserExists((User) this.getObject())) {
                h.setAuthenticated(true);
                h.setUser((ChatUser) this.getObject());
                System.out.println("User logged in: " + this.GetUsername());
                h.sendObject(new messageWrapper(D_CON.GetAllChats(), MsgType.UI));
                } else {
                    h.sendObject(new messageWrapper("Invalid username or password", MsgType.AUTH_FAIL));
                    }
        } catch (IOException e) {}
    }
}
