package src;

public abstract class Sendable {
    private String messageType;
    private Object obj;

    public Sendable(String messageType, Object obj){
        this. messageType = messageType;
        this.obj = obj;
    }

    public String GetMessageType(){
        return  messageType;
    }

    public Object GetObject(){
        return obj;
    }
}
