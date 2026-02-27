package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
//import java.swing.*; 

public class ButtonManagement implements ActionListener  {
    //private List<String> chatNames;// = new ArrayList<>();
    private String[] chatJoinButtons;
    private clientModel cModel;

    public ButtonManagement(clientModel cModel){
        this.cModel = cModel;
    }
    
    public void actionPerformed(ActionEvent e){
        System.out.println("    in ButtonManagment");        
        Object obj = e.getSource();
        
        
        
        if(obj instanceof JButton ){
            JButton loginButton = (JButton)obj;
            System.out.println("djdjdj "+e.getActionCommand());
        }
        
    }
}
