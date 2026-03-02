package src;

public class AddImageRequest extends Sendable {
    private byte image[];
    
    public AddImageRequest(byte[] image){
        super("AddFile");
        this.image = image;
    }

    @Override
    public Object getObject(){
        return image;
    }
}
