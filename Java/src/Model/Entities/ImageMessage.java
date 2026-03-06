package src.Model.Entities;

import java.time.LocalDateTime;


public class ImageMessage extends Message {
    private String imgPath;

    public ImageMessage(LocalDateTime timestamp, String chat, String type) {
        super(timestamp, chat, type);
        this.imgPath = imgPath;
    }

    public String GetImgPath() {
        return imgPath;
    }

    public void SetImagePath(String path) {
        this.imgPath = path;
    }

}
