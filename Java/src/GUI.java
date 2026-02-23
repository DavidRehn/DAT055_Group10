import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame {
    private JFrame frame;
    private JPanel loginPanel, chatList, selectedChat;
    private JTextField username, password;
    public JButton loginButton, addChatButton;


    public GUI () {
        frame = new JFrame();
        loginPanel = new JPanel();
        chatList = new JPanel();
        selectedChat = new JPanel();
        loginButton = new JButton("Log in");
        addChatButton = new JButton("Add Chat");
        username = new JTextField("Enter Username");
        password = new JTextField("Enter Passowrd");



        frame.setTitle("Chat program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(1650, 1080); //Sets the program to full screen by default
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void showLogInScreen() {

        loginPanel.setBounds(375, 200, 750, 250);
        loginPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        loginButton.setBounds(1200, 300, 200, 100);
        loginButton.setFont(new Font("Consolas", Font.PLAIN, 35));
        
        username.setPreferredSize(new Dimension(750, 125));
        username.setFont(new Font("Consolas", Font.PLAIN, 35));
        
        password.setPreferredSize(new Dimension(750, 125));
        password.setFont(new Font("Consolas", Font.PLAIN, 35));

        loginPanel.add(username);
        loginPanel.add(password);
        
        frame.add(loginPanel);
        frame.add(loginButton);
    }


    public void hideLogInScreen() {
        loginPanel.setVisible(false);
        username.setVisible(false);
        password.setVisible(false);
        loginButton.setVisible(false);
    }


    public void showMainScreen() {
        chatList.setBounds(0, 100, 400, 1080);
        chatList.setBorder(BorderFactory.createLineBorder(Color.black));
        addChatButton.setBounds(0, 0, 400, 100);
        frame.add(chatList);
        frame.add(addChatButton);
    }


   public static void main(String[] args) { //används bara för test
        GUI gui = new GUI();
        gui.showLogInScreen();
        gui.hideLogInScreen();
        gui.showMainScreen();
    }
}
