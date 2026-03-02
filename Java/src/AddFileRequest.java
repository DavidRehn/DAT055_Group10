package src;

import java.io.File;

public class AddFileRequest extends Sendable {
    private File file;
    
    public AddFileRequest(File file){
        super("AddFile");
        this.file = file;
    }

    @Override
    public Object getObject(){
        return file;
    }
}
