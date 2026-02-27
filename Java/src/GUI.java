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
    private JPanel loginPanel, chatList, selectedChat, createChatroomPanel;
    public JTextField username, message, chatroomName;
    public JPasswordField password;
    public JButton loginButton, createChatButton, addImgButton, sendButton, chatButton, cancelButton, confirmButton;;
    private JLabel usernameLabel, passwordLabel, chatroomNameLabel, createChatroomLabel;
    

    public GUI () {
        this.setTitle("Chat program");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1650, 1080); //Sets the program to full screen by default
        this.setVisible(true);
        this.setResizable(false);
    }

    public void showLogInScreen() { //Should be called when initializing the program
        // Create instances of the attributes used in this method
        loginPanel = new JPanel();
        loginButton = new JButton("Log in");
        username = new JTextField();
        password = new JPasswordField();
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        

        loginPanel.setBounds(375, 200, 750, 250);
        loginButton.setBounds(1200, 300, 200, 100);
        loginButton.setFont(new Font("Consolas", Font.PLAIN, 35));
        loginButton.setFocusable(false);
    
        
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
    }



    public void removeLogInScreen() { // Should be called when successfully logging in from the log in screen
        this.remove(loginPanel);
        this.remove(loginButton);
        this.remove(username);
        this.remove(password);
        this.remove(usernameLabel);
        this.remove(passwordLabel);
    }


    public void showHomeScreen(List<String> a) { // Should be called after successfully logging in and after removing log in screen
        chatList = new JPanel();
        createChatButton = new JButton("Create Chat");
        message = new JTextField();
        addImgButton = new JButton("+");
        sendButton = new JButton("Send");


        chatList.setBounds(0, 100, 400, 1080);
        chatList.setBorder(BorderFactory.createLineBorder(Color.black));
        chatList.setLayout(null);
        
        
        createChatButton.setBounds(0, 0, 400, 100);
        createChatButton.setFont(new Font("Consolas", Font.BOLD, 35));
        createChatButton.setFocusable(false);
        
        ArrayList<JButton> buttons;
        buttons = new ArrayList<>();
        
        
        for(int i =0; i < a.size();i++){
            chatButton = new JButton(a.get(i));
            chatButton.setFont(new Font("Consolas", Font.PLAIN, 20));
            chatButton.setBounds(0, i*75+10, 400, 75);
            chatButton.setFocusable(false);
            //chatButton.addActionListener(new chatManagement());
            buttons.add(chatButton);
            
            chatList.add(buttons.get(i));
        }

        this.add(chatList);
        this.add(createChatButton);
    }

    public void showChatroom() { // Should be called when a chatroom (button) is pressed, might need a param
        addImgButton.setBounds(400, 750, 75, 75);
        addImgButton.setFont(new Font("Consolas", Font.BOLD, 35));
        addImgButton.setFocusable(false);

        sendButton.setBounds(1395, 750, 75, 75);
        sendButton.setFont(new Font("Consolas", Font.BOLD, 15));
        sendButton.setFocusable(false);

        message.setBounds(475, 750, 920, 75);
        message.setFont(new Font("Consolas", Font.PLAIN, 20));

        this.add(addImgButton);
        this.add(message);
        this.add(sendButton);
    }

    public void showCreateChatroomWindow() { // Should be called whenever the 'Create Chat' button is pressed"
                                             // Might need to add something to make it so you cant click anywhere else in the JFrame outside the panel when this window is showing
        createChatroomPanel = new JPanel();
        chatroomName = new JTextField();
        chatroomNameLabel = new JLabel("Name: ");
        cancelButton = new JButton("Cancel");
        confirmButton = new JButton("Confirm");
        createChatroomLabel = new JLabel("Create chatroom");

        createChatroomPanel.setBounds(600, 150, 500, 200);
        createChatroomPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        createChatroomLabel.setBounds(150, 10, 300, 40);
        createChatroomLabel.setFont(new Font("Consolas", Font.PLAIN, 25));

        chatroomNameLabel.setBounds(25, 80, 75, 20);
        chatroomName.setBounds(100, 70, 300, 50);

        chatroomNameLabel.setFont(new Font("Consolas", Font.BOLD, 15));
        chatroomName.setFont(new Font("Consolas", Font.PLAIN, 15));

        cancelButton.setBounds(25, 150, 75, 20);
        confirmButton.setBounds(400, 150, 75, 20);

        createChatroomPanel.add(createChatroomLabel);
        createChatroomPanel.add(chatroomName);
        createChatroomPanel.add(chatroomNameLabel);
        createChatroomPanel.add(cancelButton);
        createChatroomPanel.add(confirmButton);
        this.add(createChatroomPanel);
    }

    public void removeCreateChatroomWindow() { //Should be called whenever 'cancel' or 'confirm' is pressed in a CreateChatroomWindow"
        this.remove(createChatroomPanel);
    }


   public static void main(String[] args) { // Testing
        GUI gui = new GUI();
        //gui.showLogInScreen();
        //gui.removeLogInScreen();
        List<String> Chats = new ArrayList<>();
        /*
        List<String> Chats = new ArrayList<>();
        Chats.add("Chat 1");
        Chats.add("Chat 2");
        Chats.add("Chat 3");
        Chats.add("Chat 4");
        gui.showHomeScreen(Chats);
        */
        gui.showHomeScreen(Chats);
        gui.showCreateChatroomWindow();
        //gui.showChatroom();
    }
}
