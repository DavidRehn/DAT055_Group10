package src;

public class ChatCreateMsg extends Sendable{
    private String chatTitle;

    public ChatCreateMsg(String title){
        super("createChat");
        this.chatTitle = title;
    }

    @Override
    public Object getObject(){
        return chatTitle;
    }
}
