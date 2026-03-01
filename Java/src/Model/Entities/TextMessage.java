package src.Model.Entities;

import java.time.LocalDateTime;

public class TextMessage extends Message{
    private String contentText;

    public TextMessage(String sender, LocalDateTime time, String chat, String text, String type){
        super(sender, time, chat, type);
        this.contentText = text;
    }

    public String GetContent(){
        return contentText;
    }
}
