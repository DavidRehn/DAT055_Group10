package src.ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import src.GetMessagesRequest;
import src.clientModel;

public class ChatFocusListener implements ActionListener{
    private final clientModel cModel;

    public ChatFocusListener(clientModel cModel) {
        this.cModel = cModel;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        JButton b = (JButton) e.getSource();
        String name = b.getName();
        cModel.SetCurrentChat(name);
        try {
            cModel.SendObject(new GetMessagesRequest(name));
        } catch (IOException a) {
            System.out.println("Could not send message");
        }
        System.out.println("Set focus to " + name);
    }
}
