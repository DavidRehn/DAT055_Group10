package src;

public class AddImageRequest extends Sendable {
    private byte[] image;
    private String imageName;
    
    public AddImageRequest(byte[] image, String name){
        super("AddImage");
        this.image = image;
        this.imageName = name;
    }

    @Override
    public Object getObject(){
        return image;
    }

    public String GetFileName(){
        return imageName;
    }
}
