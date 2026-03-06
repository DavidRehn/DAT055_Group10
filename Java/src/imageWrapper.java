package src;

import java.io.Serializable;

public class imageWrapper implements Serializable {
    private byte[] image;
    private String fileName;

    public imageWrapper(byte[] image, String filename) {
        this.image = image;
        this.fileName = filename;
    }

    public byte[] GetImage() {
        return image;
    }

    public String GetFilename() {
        return fileName;
    }
}
