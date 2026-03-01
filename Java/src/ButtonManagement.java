package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.swing.JButton;
import src.Model.Entities.ChatUser;
import src.Model.Entities.TextMessage;
//import java.swing.*; 

public class ButtonManagement implements ActionListener  {
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
                String username = gui.GetUsername();
                String password = gui.GetPassword();
                if(username.length() > 0 && password.length() > 0){
                    try {
                        cModel.SendObject(new LoginRequest(new ChatUser(username, password)));
                        System.out.println("Sent login request");
                    } catch (Exception a) {
                        a.printStackTrace();
                    }
                    /* 
                    gui.removeLogInScreen();
                    ArrayList<String> a = new ArrayList<>();
                    a.add("chat1");
                    a.add("chat2");
                    a.add("chat3");
                    a.add("chat4");
                    gui.showHomeScreen(a);
                    gui.showChatroom();*/
                }
            }else if (command.equals("Create Chat")) {
                gui.showCreateChatroomWindow();
            }else if(command.equals("setChatFocus")){
                JButton b = (JButton)obj;
                String name = b.getName();
                cModel.SetCurrentChat(name);
                try {
                    cModel.SendObject(new GetMessagesRequest(name));
                } catch (IOException a) {
                    System.out.println("Could not send message");
                }
                System.out.println("Set focus to " + name);
            }else if(command.equals("cancelChatCreation")){
                gui.removeCreateChatroomWindow();
            }else if (command.equals("confirmChatCreation")){
                try {
                    cModel.SendObject(new ChatCreateMsg(gui.GetChatTitle()));
                    System.out.println("Added chat " + gui.GetChatTitle());
                    gui.removeCreateChatroomWindow();
                } catch (Exception a) {
                    a.printStackTrace();
                }
            }else if(command.equals("Send")){
                try {
                    LocalDateTime time = LocalDateTime.now();
                    String msg = gui.message.getText();
                    if (msg.length() > 0){
                        cModel.SendObject(new AddMessageRequest(new TextMessage(time, cModel.GetCurrentChat(), msg, "text")));
                        System.out.println("Sent text mesage");
                    }
                    //Lägg till kod för bilder
                    //
                } catch (IOException a) {
                    a.printStackTrace();
                }

            }else if(command.equals("addImage")){

            }else{
                System.out.println("Invalid command");
            }
        }
        
    }
}
