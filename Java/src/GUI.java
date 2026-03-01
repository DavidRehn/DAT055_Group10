package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import src.Model.Entities.Message;
import src.Model.Entities.TextMessage;


public class GUI extends JFrame {
    private JPanel loginPanel, chatList, createChatroomPanel, messageWindow;
    public JTextField username, message, chatroomName;
    public JPasswordField password;
    public JButton loginButton, createChatButton, addImgButton, 
                   sendButton, chatButton, cancelButton, confirmButton;;
    private JLabel usernameLabel, passwordLabel, chatroomNameLabel, createChatroomLabel;
    private clientModel cModel;
    private ButtonManagement buttonListener;
    private JScrollPane chatScroll; 
    private JScrollPane messagePane;
    

    public GUI (clientModel cModel) {
        //Methods: showLogInScreen
        loginPanel = new JPanel();
        username = new JTextField();
        
        //Method: showHomeScreen(List<String> a)
        chatList = new JPanel();
        message = new JTextField();

        messageWindow = new JPanel();

        chatScroll = new JScrollPane(chatList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        messagePane = new JScrollPane(messageWindow, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        messageWindow.setLayout(new BoxLayout(messageWindow, BoxLayout.Y_AXIS));
        
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
        chatList.setBounds(0, 100, 400, 900);
        chatList.setBorder(BorderFactory.createLineBorder(Color.black));
        chatList.setLayout(null);

        chatScroll.setBounds(0, 100, 400, 900);
        chatList.setLayout(null);
        chatScroll.setViewportView(chatList);
        
        // "Add Chat" creation.
        createChatButton.setBounds(0, 0, 400, 100);
        createChatButton.setFont(new Font("Consolas", Font.BOLD, 35));
        createChatButton.setFocusable(false);
        createChatButton.addActionListener(buttonListener);
        
        //where the join chat buttons from "a" are stored.
        UpdateChats(chatNames);

        chatScroll.setViewportView(chatList);
        JScrollBar bar = chatScroll.getVerticalScrollBar();
        

        
        
        createCreateChatroomWindow();
        this.add(chatScroll);
        this.add(createChatButton);
        
        this.revalidate();
        this.repaint();
    }

    public void showChatroom(List<Message> messages) { // Should be called when a chatroom (button) is pressed, might need a param
        // JButton
        addImgButton.setBounds(400, 925, 75, 75);
        addImgButton.setFont(new Font("Consolas", Font.BOLD, 35));
        addImgButton.setFocusable(false);
        addImgButton.setActionCommand("addImage");
        addImgButton.addActionListener(buttonListener);
        this.add(addImgButton);

        sendButton.setBounds(1395, 925, 75, 75);
        sendButton.setFont(new Font("Consolas", Font.BOLD, 15));
        sendButton.setFocusable(false);
        sendButton.addActionListener(buttonListener);
        this.add(sendButton);
        
        
        // JTextField
        message.setBounds(475, 925, 920, 75);
        message.setFont(new Font("Consolas", Font.PLAIN, 20));

    
        System.out.println(messageWindow.getPreferredSize());
        messagePane.setBounds(401, 0, 1250, 924);
        messagePane.getVerticalScrollBar().setUnitIncrement(20);
        UpdateMessages(messages);
        messagePane.setViewportView(messageWindow);
        JScrollBar mScrollBar = messagePane.getVerticalScrollBar();



        this.add(message);  
        this.add(messagePane);
        this.revalidate();
        this.repaint();
    }

    public void createCreateChatroomWindow() { // Should be called whenever the 'Create Chat' button is pressed"
                                             // Might need to add something to make it so you cant click anywhere else in the JFrame outside the panel when this window is showing
        //JPanel
        createChatroomPanel.setBounds(600, 150, 500, 200);
        createChatroomPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        createChatroomPanel.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        
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
        confirmButton.addActionListener(buttonListener);

        //createChatroomPanel.add(createChatroomLabel, BorderLayout.NORTH); //JLabel
        createChatroomPanel.add(chatroomName, BorderLayout.CENTER);        //JLabel
        //createChatroomPanel.add(chatroomNameLabel, BorderLayout.NORTH);   //JLabel
        //createChatroomPanel.add(cancelButton, BorderLayout.SOUTH);        //JButton
        //createChatroomPanel.add(confirmButton, BorderLayout.SOUTH);

        top.add(createChatroomLabel);
        createChatroomPanel.add(chatroomNameLabel, BorderLayout.CENTER);
        bottom.add(cancelButton);
        bottom.add(confirmButton);
        createChatroomPanel.add(top, BorderLayout.NORTH);
        createChatroomPanel.add(bottom, BorderLayout.SOUTH);
        
    }

    public void UpdateChats(List<String> chats){
        chatList.removeAll();
        for (int i = 0; i < chats.size(); i++){
            chatButton = new JButton(chats.get(i));
            //The visuals of the chat buttons
            chatButton.setFont(new Font("Consolas", Font.PLAIN, 20));
            chatButton.setBounds(0, i*75+10, 400, 75);
            chatButton.setFocusable(false);
            chatButton.setActionCommand("setChatFocus");
            chatButton.setName(chats.get(i));
            
            chatButton.addActionListener(buttonListener);
            
            chatList.add(chatButton);
        }
        int totalHeight = chats.size() * 75 + 10;
        chatList.setPreferredSize(new Dimension(400, totalHeight));
        chatList.revalidate();
        chatList.repaint();
    }

    public void UpdateMessages(List<Message> messages){
        System.out.println(messages);
        int mesasgeHeight = 0;
        messageWindow.removeAll();
        for (int i = 0; i < messages.size(); i++){
            JPanel message = new JPanel();
            message.setLayout(new BorderLayout());
            JPanel top = new JPanel();
            message.setPreferredSize(new Dimension(1000, 100));
            top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
            top.setPreferredSize(new Dimension(messageWindow.getWidth(), 25));
            top.setMinimumSize(new Dimension(messageWindow.getWidth(), 25));
            top.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            String msgType = messages.get(i).GetType();
            if(msgType.equals("text")){
                System.out.println(messages.get(i).GetTimestamp());
                TextMessage m = (TextMessage)messages.get(i);
                JLabel sender = new JLabel(m.GetSender());
                top.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                top.add(sender);
                top.add(Box.createHorizontalGlue());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm"); 
                JLabel timeLabel = new JLabel(m.GetTimestamp().format(formatter));
                timeLabel.setHorizontalAlignment(JLabel.RIGHT);
                top.add(timeLabel);
                message.add(new JLabel(m.GetContent()), BorderLayout.CENTER);
                message.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }else if (msgType.equals("image")){
                mesasgeHeight = 400;
            }
            message.add(top, BorderLayout.NORTH);
            messageWindow.add(message);
            messageWindow.add(Box.createVerticalStrut(15));
        }
        messageWindow.revalidate();
        messageWindow.repaint();
    }

    public void showCreateChatroomWindow(){
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
