package src;
import java.io.IOException;
import java.util.ArrayList;
import src.Model.DAO.DataStorage;
import src.Model.Entities.Message;
public class GetMessagesRequest extends Sendable implements RunnableRequest{
    private String chat;

    public GetMessagesRequest(String chat) {
        super("getMessages");
        this.chat = chat;
    }

    @Override
    public Object getObject() {
        return chat;
    }
    @Override
    public void runRequest(DataStorage D_CON, HandlerInterface h){
        try {
        String chat = (String) this.getObject();
        h.setFocus(chat);
        if (!D_CON.ChatUserExists(h.getUser(), chat)) {
            D_CON.AddUserToChat(chat, h.getUser().getUserName());
            System.out.println("Added user " + h.getUser().getUserName() + " to chat " + chat);
            }
        ArrayList<Message> messages = D_CON.GetMessages(chat);
        h.sendObject(new messageWrapper(messages, MsgType.MSG));
        } catch (IOException e) {}
    }
}
