package src;

public class ChatCreateMsg extends Sendable implements RunnableRequest{
    private String chatTitle;

    public ChatCreateMsg(String title) {
        super("createChat");
        this.chatTitle = title;
    }

    @Override
    public Object getObject() {
        return chatTitle;
    }
    @Override
    public void runRequest(DataStorage D_CON, HandlerInterface h){
        try {
        if (!D_CON.ChatExists((String) this.getObject())) {
            try {
                D_CON.AddChat(new GroupChat((String) this.getObject()));
                D_CON.AddUserToChat((String) this.getObject(), h.getUser().getUserName());
                h.broadcastChatsToUser();
            } catch (RuntimeException ex) {
                h.sendObject(new messageWrapper("Could not create chat.", "AUTH_FAIL"));
                }
        } else {
            h.sendObject(new messageWrapper("Chat already exists", "AUTH_FAIL"));
            }
        } catch (IOException e) {}
    }
}
