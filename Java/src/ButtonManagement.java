package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import src.Model.Entities.ChatUser;
//import java.swing.*; 

public class ButtonManagement implements ActionListener  {
    //private List<String> chatNames;// = new ArrayList<>();
    private String[] chatJoinButtons;
    private GUI gui;
    private clientModel cModel;

    public ButtonManagement(GUI gui){
        this.gui = gui;
        this.cModel = gui.GetClientModel();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("    in ButtonManagment");        
        Object obj = e.getSource();
        

        if(obj instanceof JButton ){
            String command = e.getActionCommand();
            if (command.equals("Login")){
                try {
                    //cModel.SendObject(new LoginRequest(new ChatUser(gui.GetUsername(), gui.GetPassword(), 1)));
                    cModel.SendObject(new LoginRequest(new ChatUser("Ben", "ABC123", 1)));
                    System.out.println("Sent");
                } catch (IOException a) {
                    a.printStackTrace();
                }
            }
        }
        
    }
}
