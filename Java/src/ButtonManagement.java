package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
        Object obj = e.getSource();
        

        if(obj instanceof JButton ){
            String command = e.getActionCommand();
            if (command.equals("Login")){
                try {
                    cModel.SendObject(new LoginRequest(new ChatUser(gui.GetUsername(), gui.GetPassword(), 1)));
                    //System.out.println(new ChatUser(gui.GetUsername(), gui.GetPassword(), 1));
                    System.out.println("Sent login request");
                } catch (Exception a) {
                    a.printStackTrace();
                }
                gui.removeLogInScreen();
                ArrayList<String> a = new ArrayList<>();
                a.add("chat1");
                a.add("chat2");
                a.add("chat3");
                a.add("chat4");
                gui.showHomeScreen(a);
            }
        }
        
    }
}
