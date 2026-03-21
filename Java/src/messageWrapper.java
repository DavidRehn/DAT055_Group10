package src;

import java.io.Serializable;

public class messageWrapper extends Sendable implements Serializable {
    Object obj;
    MsgType msgType;

    messageWrapper(Object obj, MsgType msgType) {
        super("");
        this.obj = obj;
        this.msgType = msgType;
    }

    @Override
    public Object getObject() {
        return obj;
    }

    public MsgType getMsg(){
        return msgType;
    }

    @Override
    public String toString() {
        return ("[" + getMsgType() + ", " + obj.toString() + "]");
    }
}
