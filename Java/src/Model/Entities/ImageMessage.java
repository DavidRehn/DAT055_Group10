package src.Model.Entities;
import java.time.OffsetDateTime;


public class ImageMessage extends Message {
    private String imgPath;

    public ImageMessage(int senderId, OffsetDateTime timestamp, int chatId, int messageId, String imgPath){
        super(senderId, timestamp, messageId, chatId);
        this.imgPath = imgPath;
    }

    public String GetImgPath(){
        return imgPath;
    }
}
