package src;

public class ChatFocusMsg extends Sendable{
    private String chatTitle;

    public ChatFocusMsg(String title){
        super("chatFocus");
        this.chatTitle = title;
    }

    @Override
    public Object getObject(){
        return chatTitle;
    }
}
