package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class GUI extends JFrame {
    private JPanel loginPanel, chatList, /*selectedChat,*/ createChatroomPanel;
    public JTextField username, message, chatroomName;
    public JPasswordField password;
    public JButton loginButton, createChatButton, addImgButton, 
                   sendButton, chatButton, cancelButton, confirmButton;;
    private JLabel usernameLabel, passwordLabel, chatroomNameLabel, createChatroomLabel;
    private clientModel cModel;
    private ButtonManagement buttonListener;
    

    public GUI (clientModel cModel) {
        //Methods: showLogInScreen
        loginPanel = new JPanel();
        username = new JTextField();
        
        //Method: showHomeScreen(List<String> a)
        chatList = new JPanel();
        message = new JTextField();
        
        //Methods: showCreateChatroomWindow()
        createChatroomPanel = new JPanel();
        chatroomName = new JTextField();
        
        this.setTitle("Chat program");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1650, 1080); //Sets the program to full screen by default
        this.setVisible(true);
        this.setResizable(false);
        this.cModel = cModel;
        this.buttonListener = new ButtonManagement(this);
    }
    
    public void showLogInScreen() { //Should be called when initializing the program
        // Create instances of the attributes used in this method
        loginPanel = new JPanel();
        loginButton = new JButton("Login");
         username = new JTextField();
        password = new JPasswordField();
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        

        loginPanel.setBounds(375, 200, 750, 250);
        loginButton.setBounds(1200, 300, 200, 100);
        loginButton.setFont(new Font("Consolas", Font.PLAIN, 35));
        loginButton.setFocusable(false);
        loginButton.addActionListener(buttonListener);
    
        
        username.setPreferredSize(new Dimension(750, 125));
        username.setFont(new Font("Consolas", Font.PLAIN, 35));

        password.setPreferredSize(new Dimension(750, 125));
        password.setFont(new Font("Consolas", Font.PLAIN, 35));

        usernameLabel.setBounds(150, 200, 100, 100);
        passwordLabel.setBounds(150, 325, 100, 100);

        usernameLabel.setFont(new Font("Consolas", Font.BOLD, 17));
        passwordLabel.setFont(new Font("Consolas", Font.BOLD, 17));

        
        loginPanel.add(username);
        loginPanel.add(password);
        
        this.add(loginPanel);
        this.add(loginButton);
        this.add(usernameLabel);
        this.add(passwordLabel);

        this.revalidate();
        this.repaint();

    }
    

    
    public void removeLogInScreen() { // Should be called when successfully logging in from the log in screen
        this.remove(loginPanel);
        this.remove(loginButton);
        this.remove(username);
        this.remove(password);
        this.remove(usernameLabel);
        this.remove(passwordLabel);
    }
    

    public void showHomeScreen(List<String> chatNames) { // Should be called after successfully logging in and after removing log in screen
        //remakes "a" type, into one that ButtonManagement konstruktor can handle.
        chatList = new JPanel();
        
        createChatButton = new JButton("Create Chat");
        message = new JTextField();
        addImgButton = new JButton("+");
        sendButton = new JButton("Send");

        //JPanel settupp.
        chatList.setBounds(0, 100, 400, 1080);
        chatList.setBorder(BorderFactory.createLineBorder(Color.black));
        chatList.setLayout(null);
        
        // "Add Chat" creation.
        createChatButton.setBounds(0, 0, 400, 100);
        createChatButton.setFont(new Font("Consolas", Font.BOLD, 35));
        createChatButton.setFocusable(false);
        createChatButton.addActionListener(buttonListener);
        
        //where the join chat buttons from "a" are stored.
        ArrayList<JButton> buttons;
        buttons = new ArrayList<>();
        
        
        for(int i =0; i < chatNames.size();i++){
            chatButton = new JButton(chatNames.get(i));
            //The visuals of the chat buttons
            chatButton.setFont(new Font("Consolas", Font.PLAIN, 20));
            chatButton.setBounds(0, i*75+10, 400, 75);
            chatButton.setFocusable(false);
            chatButton.setActionCommand("setChatFocus");
            
            chatButton.addActionListener(buttonListener);
            buttons.add(chatButton);
            
            chatList.add(buttons.get(i));
        }

        this.add(chatList);
        this.add(createChatButton);
        this.revalidate();
        this.repaint();
    }

    public void showChatroom() { // Should be called when a chatroom (button) is pressed, might need a param
        // JButton
        addImgButton.setBounds(400, 750, 75, 75);
        addImgButton.setFont(new Font("Consolas", Font.BOLD, 35));
        addImgButton.setFocusable(false);
        addImgButton.setActionCommand("addImage");
        addImgButton.addActionListener(buttonListener);
        this.add(addImgButton);

        sendButton.setBounds(1395, 750, 75, 75);
        sendButton.setFont(new Font("Consolas", Font.BOLD, 15));
        sendButton.setFocusable(false);
        sendButton.addActionListener(buttonListener);
        this.add(sendButton);
        
        
        // JTextField
        message.setBounds(475, 750, 920, 75);
        message.setFont(new Font("Consolas", Font.PLAIN, 20));
        this.add(message);  
        this.revalidate();
        this.repaint();
    }

    public void showCreateChatroomWindow() { // Should be called whenever the 'Create Chat' button is pressed"
                                             // Might need to add something to make it so you cant click anywhere else in the JFrame outside the panel when this window is showing
        //JPanel
        createChatroomPanel.setBounds(600, 150, 500, 200);
        createChatroomPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        //JLabel
        createChatroomLabel = new JLabel("Create chatroom");
        createChatroomLabel.setBounds(150, 10, 300, 40);
        createChatroomLabel.setFont(new Font("Consolas", Font.PLAIN, 25));
        
        //JLabel
        chatroomNameLabel = new JLabel("Name: ");
        chatroomNameLabel.setBounds(25, 80, 75, 20);
        chatroomNameLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        
        //JTextField
        chatroomName.setBounds(100, 70, 300, 50);
        chatroomName.setFont(new Font("Consolas", Font.PLAIN, 15));

        //JButton
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(25, 150, 75, 20);
        cancelButton.setActionCommand("cancelChatCreation");
        cancelButton.addActionListener(buttonListener);

        
        //JButton
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(400, 150, 75, 20);
        confirmButton.setActionCommand("confirmChatCreation");
        createChatButton.addActionListener(buttonListener);

        createChatroomPanel.add(createChatroomLabel); //JLabel
        createChatroomPanel.add(chatroomName);        //JLabel
        createChatroomPanel.add(chatroomNameLabel);   //JLabel
        createChatroomPanel.add(cancelButton);        //JButton
        createChatroomPanel.add(confirmButton);
        this.add(createChatroomPanel);
        this.revalidate();
        this.repaint();
    }

    public void removeCreateChatroomWindow() { //Should be called whenever 'cancel' or 'confirm' is pressed in a CreateChatroomWindow"
        this.remove(createChatroomPanel);
        this.revalidate();
        this.repaint();
    }

    public clientModel GetClientModel(){
        return cModel;
    }

    public String GetPassword(){
        return new String(password.getPassword());
    }

    public String GetUsername(){
        return username.getText();
    }

    public String GetChatTitle(){
        return chatroomName.getText();
    }
}
