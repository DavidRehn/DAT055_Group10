package src;

import src.Model.DAO.DataStorage;
import src.Model.Entities.Message;
public class AddMessageRequest extends Sendable implements RunnableRequest{
    private Message message;

    public AddMessageRequest(Message message) {
        super("AddMsg");
        this.message = message;
    }

    @Override
    public Object getObject() {
        return message;
    }
    @Override
    public void runRequest(DataStorage D_CON, HandlerInterface h){
        System.out.println(h.getUser());
        Message r = (Message) this.getObject();
        System.out.println(r.GetChat());
        System.out.println(D_CON.ChatUserExists(h.getUser(), r.GetChat()));
        if (D_CON.ChatUserExists(h.getUser(), r.GetChat())) {
            System.out.println("a");
            r.SetSender(h.getUser().getUserName());
            D_CON.AddMessage(r);
            h.broadcastMessages(r.GetChat());
            System.out.println("Added message");
        }
    }
}
