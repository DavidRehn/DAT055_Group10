package src;
import src.Model.Entities.Chat;

public class ChatCreateMsg extends Sendable{
    private Chat chat;

    public ChatCreateMsg(Chat chat){
        super("createChat");
        this.chat = chat;
    }

    @Override
    public Object getObject(){
        return chat;
    }

    public String GetTitle(){
        return chat.getChatTitle();
    }
}
