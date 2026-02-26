import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.util.List;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JPanel loginPanel, chatList, selectedChat;
    private JTextField username, message;
    private JPasswordField password;
    public JButton loginButton, addChatButton, addImgButton, sendButton;
    private JLabel usernameLabel, passwordLabel;
    private JButton chatButton;
    

    public GUI () {
        loginPanel = new JPanel();
        chatList = new JPanel();
        selectedChat = new JPanel();
        loginButton = new JButton("Log in");
        addChatButton = new JButton("Add Chat");
        username = new JTextField();
        password = new JPasswordField();
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        message = new JTextField();
        addImgButton = new JButton("+");
        sendButton = new JButton("Send");


        this.setTitle("Chat program");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1650, 1080); //Sets the program to full screen by default
        this.setVisible(true);
        this.setResizable(false);
    }

    public void showLogInScreen() {

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



    public void removeLogInScreen() {
        this.remove(loginPanel);
        this.remove(loginButton);
        this.remove(username);
        this.remove(password);
        this.remove(usernameLabel);
        this.remove(passwordLabel);
    }


    public void showHomeScreen(List<String> a) {
        int gapp = 10;
        chatList.setBounds(0, 100, 400, 1080);
        chatList.setBorder(BorderFactory.createLineBorder(Color.black));
        chatList.setLayout(null);
        
        
        addChatButton.setBounds(0, 0, 400, 100);
        addChatButton.setFont(new Font("Consolas", Font.BOLD, 35));
        addChatButton.setFocusable(false);
        
        ArrayList<JButton> buttons;
        buttons = new ArrayList<>();
        
        for(int i =0; i < a.size();i++){
            chatButton = new JButton(a.get(i));
            chatButton.setFont(new Font("Consolas", Font.PLAIN, 20));
            chatButton.setBounds(0, i*75+gapp, 350, 75);
            chatButton.setFocusable(false);
            //chatButton.addActionListener(new chatManagement());
            buttons.add(chatButton);
            
            chatList.add(buttons.get(i) );
        }

        this.add(chatList);
        this.add(addChatButton);
    }

    public void showChatroom() {
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


   public static void main(String[] args) { //används bara för test
        GUI gui = new GUI();
        //gui.showLogInScreen();
        //gui.removeLogInScreen();
        List<String> Chats = new ArrayList<>();
        Chats.add("gg");
        Chats.add("hh");
        Chats.add("thh");
        Chats.add("yy");
        gui.showHomeScreen(Chats);
        //gui.showChatroom();
    }
}
