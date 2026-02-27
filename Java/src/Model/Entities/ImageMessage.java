<<<<<<< HEAD:Java/src/ImageMessage.java
package src;
import java.time.OffsetDateTime;
=======
package src.Model.Entities;

>>>>>>> 0806e57f536c317900d81c8a20bf3c3c028326a7:Java/src/Model/Entities/ImageMessage.java
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
