package src;

public class DeleteUserMsg extends Sendable{
    public DeleteUserMsg(){
        super("DeleteUserMsg");
    }

    @Override
    public Object getObject(){
        return ("DeleteUserMsg");
    }
}
