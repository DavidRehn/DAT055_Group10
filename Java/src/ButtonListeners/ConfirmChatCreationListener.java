package src.ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import src.ChatCreateMsg;
import src.GUI;
import src.clientModel;

public class ConfirmChatCreationListener implements ActionListener{
    private final GUI gui;
    private final clientModel cModel;

    public ConfirmChatCreationListener(GUI gui) {
        this.gui = gui;
        cModel = gui.GetClientModel();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        try {
            if (gui.GetChatTitle().length() > 0) {
                cModel.SendObject(new ChatCreateMsg(gui.GetChatTitle()));
                System.out.println("Added chat " + gui.GetChatTitle());
                gui.removeCreateChatroomWindow();
            }
        } catch (IOException a) {
        }
    }
}
