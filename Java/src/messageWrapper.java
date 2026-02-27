package src;

public class messageWrapper implements Sendable{
    private String messageType;

    @Override
    public String GetMessageType(){
        return messageType;
    }
}
