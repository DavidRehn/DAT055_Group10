package src;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.Model.DAO.*;
import src.Model.Entities.ImageMessage;
public class AddImageRequest extends Sendable implements RunnableRequest{
    private ImageMessage message;
    private byte[] image;
    private String imageName;
    private String chat;

    public AddImageRequest(byte[] image, String name, ImageMessage message, String chat) {
        super("AddImage");
        this.image = image;
        this.imageName = name;
        this.message = message;
        this.chat = chat;
    }

    @Override
    public Object getObject() {
        return message;
    }

    public byte[] GetImage() {
        return image;
    }

    public String GetFileName() {
        return imageName;
    }

    public void UpdateTimestamp() {
        message.UpdateTimestamp();
    }

    public String GetChat() {
        return chat;
    }
    
    public void runRequest(DataStorage D_CON, HandlerInterface h){
        try {
            ImageMessage m = (ImageMessage) this.getObject();
            m.SetSender(h.getUser().getUserName());
            byte[] img = (byte[]) this.GetImage();
            String fileName = this.GetFileName();
            ByteArrayInputStream b = new ByteArrayInputStream(img);
            BufferedImage image = ImageIO.read(b);
            File outputDir = new File(h.getServerImageDir());
            if (!outputDir.exists()) {
                outputDir.mkdirs();
                }
            File outputFile = new File(outputDir, fileName + ".png");
            ImageIO.write(image, "png", outputFile);
            m.SetImagePath(h.getClientImageDir() + fileName + ".png");
            D_CON.AddMessage(m);
            System.out.println("saved image");
        
            // Send back the image to be saved at client side (Should be done by observable)
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bOut);    // Automaticly converts all valid image formats to png (should already be png if sent from client)
            byte[] imageBytes = bOut.toByteArray();
            h.broadcastImage(this.GetChat(), imageBytes, fileName);
            h.broadcastMessages(this.GetChat());
            System.out.println("Sent image response");
        } catch (IOException e) {}
    }
}
