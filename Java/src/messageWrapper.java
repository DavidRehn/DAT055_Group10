package src;

public class messageWrapper implements Sendable{
    private String messageType;
    private Object obj;

    @Override
    public String GetMessageType(){
        return messageType;
    }

    public Object GetObject(){
        return obj;
    }
}
