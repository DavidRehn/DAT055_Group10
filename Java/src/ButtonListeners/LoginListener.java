package src.ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import src.GUI;
import src.LoginRequest;
import src.Model.Entities.ChatUser;
import src.clientModel;

public class LoginListener implements ActionListener{
    private final GUI gui;
    private final clientModel cModel;

    public LoginListener(GUI gui) {
        this.gui = gui;
        this.cModel = gui.GetClientModel();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String username = gui.GetUsername();
        String password = gui.GetPassword();
        if (username.length() > 0 && password.length() > 0) {
            try {
                cModel.SendObject(new LoginRequest(new ChatUser(username, password)));
                System.out.println("Sent login request");
            } catch (IOException a) {
            }
        }
    }
}
