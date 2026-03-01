package src;

public class GetMessagesRequest extends Sendable {
    private String chat;

    public GetMessagesRequest(String chat){
        super("getMessages");
        this.chat = chat;
    }

    @Override
    public Object getObject(){
        return chat;
    }
}
