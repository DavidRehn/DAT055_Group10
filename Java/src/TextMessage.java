package src;

import java.time.OffsetDateTime;

public class TextMessage extends Message{
    private String contentText;

    public TextMessage(int senderId, OffsetDateTime time, int messageId, int chatId, String text){
        super(senderId, time, messageId, chatId);
        this.contentText = text;
    }

    public String GetContent(){
        return contentText;
    }
}
