package src.Model.Entities;
import java.time.OffsetDateTime;


public class ImageMessage extends Message {
    private String imgPath;

    public ImageMessage(String sender, OffsetDateTime timestamp, String chat, int messageId, String imgPath, String type){
        super(sender, timestamp, messageId, chat, type);
        this.imgPath = imgPath;
    }

    public String GetImgPath(){
        return imgPath;
    }
}
