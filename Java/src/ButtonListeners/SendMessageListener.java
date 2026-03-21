package src.ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import src.AddImageRequest;
import src.AddMessageRequest;
import src.GUI;
import src.Model.Entities.TextMessage;
import src.clientModel;

public class SendMessageListener implements ActionListener {
    private final GUI gui;
    private final clientModel cModel;

    public SendMessageListener(GUI gui) {
        this.gui = gui;
        this.cModel = gui.GetClientModel();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        AddImageRequest msgRequest = cModel.getChosenImage();
        try {
            LocalDateTime time = LocalDateTime.now();
            String msg = gui.getMessage();
            if (msg.length() > 0) {
                cModel.SendObject(new AddMessageRequest(new TextMessage(time, cModel.GetCurrentChat(), msg, "text")));
                gui.resetMessage();
                System.out.println("Sent text mesage");
            }
            if (msgRequest != null) {
                msgRequest.UpdateTimestamp();
                cModel.SendObject(msgRequest);
                cModel.setChosenImage(null);
                System.out.println("Sent image mesage");
            }
        } catch (IOException a) {
        }
    }
}
