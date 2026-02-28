package src;
import src.Model.Entities.Chat;

public class ChatCreateMsg extends Sendable{
    private Chat chat;

    public ChatCreateMsg(String msgType, Chat chat){
        super(msgType);
        this.chat = chat;
    }

    @Override
    public Object getObject(){
        return chat;
    }
}
