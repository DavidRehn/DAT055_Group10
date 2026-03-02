package src;

import src.Model.Entities.ImageMessage;

public class AddImageRequest extends Sendable {
    private ImageMessage message;
    private byte[] image;
    private String imageName;
    private String chat;
    
    public AddImageRequest(byte[] image, String name, ImageMessage message, String chat){
        super("AddImage");
        this.image = image;
        this.imageName = name;
        this.message = message;
        this.chat = chat;
    }

    @Override
    public Object getObject(){
        return message;
    }

    public byte[] GetImage(){
        return image;
    }

    public String GetFileName(){
        return imageName;
    }

    public void UpdateTimestamp(){
        message.UpdateTimestamp();
    }

    public String GetChat(){
        return chat;
    }
}
