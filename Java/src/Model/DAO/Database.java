package src.Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import src.Model.Entities.*;

public class Database implements DataStorage{
    private final String URL;
    private final String USER;
    private final String PASSWORD;
    private Connection conn;

    public Database(){
        this.URL = "jdbc:postgresql://localhost:5432/postgres";
        this.USER = "postgres";
        this.PASSWORD = "postgres";
        try {
            this.conn = DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void AddChat(Chat chat){
        String sql = "INSERT INTO Chats VALUES (?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, chat.getChatTitle());
            ps.executeQuery();
        } catch (SQLException e) {
        }
    }

    public boolean ChatExists(String title){
        String sql = "SELECT EXISTS(SELECT FROM Chats WHERE title = ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Chat GetChat(String title){
        String sql = "SELECT * FROM Chats WHERE title = ?";
        Chat temp = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    String resultTitle = rs.getString("title");
                    temp = new GroupChat(title);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
    
    @Override
    public void AddUser(User user){
        String sql = "INSERT INTO Users VALUES (?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.GetLogin());
            ps.executeQuery();
        } catch (SQLException e) {
        }
    }

    public User GetUser(String name){
        String sql = "SELECT * FROM Users WHERE name = ?";
        User temp = null;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    String resultName = rs.getString("name");
                    String login = rs.getString("login");
                    temp = new ChatUser(name, login);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public void AddUserToChat(String chatTitle, String username){
        String sql = "INSERT INTO Chat_Members VALUES (?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, chatTitle);
            ps.setString(2, username);
            ps.executeQuery();
        } catch (SQLException e) {
        }
    }

    @Override
    public int RemoveUser(String username){
        String sql = "DELETE FROM Users WHERE name = ?";
        int rowsAffected = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
        }
        return rowsAffected;
    }

    @Override
    public boolean UserExists(User user){
        return !(GetUser(user.getUserName()) == null);
    }
    
    @Override
    public boolean ChatUserExists(User user, String chat){
        String sql = "SELECT * FROM Chat_members WHERE title = ? And name = ?";
        boolean temp = false;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, chat);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    temp = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
    @Override
    public void AddMessage(Message message){
        String sql = "INSERT INTO Messages VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, message.GetSender());
            ps.setInt(2, message.GetMessageID());
            ps.setString(3, message.GetChat());
            ps.setString(4, message.GetType());
            if(message.GetType().equals("text")){
                TextMessage temp = (TextMessage) message;
                ps.setString(5, temp.GetContent());
            } else if(message.GetType().equals("image")){
                ImageMessage temp = (ImageMessage) message;
                ps.setString(5, temp.GetImgPath());
            }
            ps.executeQuery();
        } catch (SQLException e) {
        }
    }
    
    @Override
    public ArrayList<String> GetAllChats(){
        String sql = "SELECT * FROM Chats";
        ArrayList<String> chats = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                      String title = rs.getString("title");
                      chats.add(title);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
            
        return chats;
    }

    @Override
    public ArrayList<Message> GetMessages(String chat){
        String sql = "SELECT * FROM Messages WHERE chat = ?";
        ArrayList<Message> messages = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, chat);
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    String sender = rs.getString(1);
                    int id = rs.getInt(2);
                    String chatName = rs.getString(3);
                    String msgType = rs.getString(4);
                    OffsetDateTime time = (OffsetDateTime)rs.getObject(6, OffsetDateTime.class);
                    if(msgType.equals("text")){
                      String text = rs.getString(5);
                      Message m = new TextMessage(sender, time, id, chatName, text, msgType);
                      messages.add(m);
                    }else if(msgType.equals("image")){
                      String imageUrl = rs.getString(5);
                      Message m = new ImageMessage(sender, time, chatName, id, imageUrl, msgType);
                      messages.add(m);
                    }
                }
            }catch(SQLException a){
                a.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
