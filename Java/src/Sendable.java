package src;

import java.io.Serializable;
public abstract class Sendable implements Serializable {
    private String messageType;
    public Sendable(String msgType){
        messageType=msgType;
    }
    public String getMsgType(){
        return  messageType;
    }
    public abstract Object getObject();
}
