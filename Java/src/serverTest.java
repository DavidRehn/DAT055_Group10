package src;
import java.util.ArrayList;

public class serverTest {
    private ArrayList<User> users;

    public serverTest(){
        users = new ArrayList<>();
        users.add(new ChatUser("Ben", "ABC123"));
        users.add(new ChatUser("Tom", "1234"));
        users.add(new ChatUser("Sam", "Password123"));
    }

    public User GetUser(String username){
        for (User u: users){
            if (u.getUserName().equals(username)){
                return u;
            }
        }
        return null;
    }
}
