package src;

import src.Model.Entities.ImageMessage;

public class AddImageRequest extends Sendable {
    private ImageMessage message;
    private byte[] image;
    private String imageName;
    
    public AddImageRequest(byte[] image, String name, ImageMessage message){
        super("AddImage");
        this.image = image;
        this.imageName = name;
        this.message = message;
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
}
