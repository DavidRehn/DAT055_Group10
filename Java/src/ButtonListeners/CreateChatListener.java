package src.ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.GUI;

public class CreateChatListener implements ActionListener{
    private final GUI gui;

    public CreateChatListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        gui.showCreateChatroomWindow();
    }
}
