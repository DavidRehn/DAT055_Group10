package src;
import src.Model.Entities.Message;

public class AddMessageRequest extends Sendable {
    private Message message;

    public AddMessageRequest(Message message){
        super("AddMsg");
        this.message = message;
    }

    @Override
    public Object getObject(){
        return message;
    }
    
}
