package src.Model.Entities;

import java.time.OffsetDateTime;

public class TextMessage extends Message{
    private String contentText;

    public TextMessage(String sender, OffsetDateTime time, int messageId, String chat, String text, String type){
        super(sender, time, messageId, chat, type);
        this.contentText = text;
    }

    public String GetContent(){
        return contentText;
    }
}
