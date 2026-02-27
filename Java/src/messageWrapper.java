package src;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
public class messageWrapper extends Sendable implements Serializable
{
    ArrayList<String> Chats;
    messageWrapper(ArrayList<String> L){
        super("UI");
        Chats.addAll(L);
    }
    public Object getObject(){
        return Chats;
    }
    public String toString(){
        return Chats.toString();
    }
}
