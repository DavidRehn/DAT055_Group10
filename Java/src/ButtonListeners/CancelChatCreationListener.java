package src.ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.GUI;

public class CancelChatCreationListener implements ActionListener{
    private final GUI gui;

    public CancelChatCreationListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        gui.removeCreateChatroomWindow();
    }
}
