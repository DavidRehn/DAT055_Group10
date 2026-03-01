package src;
import java.io.Serializable;
public class messageWrapper extends Sendable implements Serializable
{
    Object obj;
    messageWrapper(Object obj, String msgType){
        super(msgType);
        this.obj = obj;
    }

    @Override
    public Object getObject(){
        return obj;
    }

    @Override
    public String toString(){
        return ("[" + getMsgType() + ", " +  obj.toString() + "]");
    }
}
