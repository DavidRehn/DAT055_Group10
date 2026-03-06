package src.Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import src.Model.Entities.Chat;
import src.Model.Entities.ChatUser;
import src.Model.Entities.GroupChat;
import src.Model.Entities.ImageMessage;
import src.Model.Entities.Message;
import src.Model.Entities.TextMessage;
import src.Model.Entities.User;

public class Database implements DataStorage {
    private final String URL;
    private final String USER;
    private final String PASSWORD;

    public Database() {
        this.URL = "jdbc:postgresql://localhost:5432/postgres";
        this.USER = "postgres";
        this.PASSWORD = "postgres";
        try (Connection ignored = openConnection()) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
    }

    @Override
    public void AddChat(Chat chat) {
        String sql = "INSERT INTO Chats VALUES (?)";
        try (Connection conn = openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chat.getChatTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to add chat: " + chat.getChatTitle(), e);
        }
    }

    public boolean ChatExists(String title) {
        String sql = "SELECT EXISTS(SELECT FROM Chats WHERE title = ?)";
        try (Connection conn = openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Chat GetChat(String title) {
        String sql = "SELECT * FROM Chats WHERE title = ?";
        Chat temp = null;
        try (Connection conn = openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    temp = new GroupChat(title);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public void AddUser(User user) {
        String sql = "INSERT INTO Users VALUES (?, ?)";
        try (Connection conn = openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.GetLogin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to add user: " + user.getUserName(), e);
        }
    }

    public User GetUser(String name) {
        String sql = "SELECT * FROM Users WHERE name = ?";
        User temp = null;
        try (Connection conn = openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
    public void AddUserToChat(String chatTitle, String username) {
        String sql = "INSERT INTO Chat_Members VALUES (?, ?)";
        try (Connection conn = openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatTitle);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to add user " + username + " to chat " + chatTitle, e);
        }
    }

    @Override
    public int RemoveUser(String username) {
        String sql = "DELETE FROM Users WHERE name = ?";
        int rowsAffected = 0;
        try (Connection conn = openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public boolean UserExists(User user) {
        User found = GetUser(user.getUserName());
        return found != null && found.GetLogin().equals(user.GetLogin());
    }

    @Override
    public boolean ChatUserExists(User user, String chat) {
        String sql = "SELECT EXISTS(SELECT FROM Chat_Members WHERE name = ? AND chat = ?)";
        try (Connection conn = openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, chat);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void AddMessage(Message message) {
        String sql = "INSERT INTO Messages VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, message.GetSender());
            ps.setString(2, message.GetChat());
            ps.setString(3, message.GetType());
            ps.setTimestamp(5, message.GetTime());
            if (message.GetType().equals("text")) {
                TextMessage temp = (TextMessage) message;
                ps.setString(4, temp.GetContent());
            } else if (message.GetType().equals("image")) {
                ImageMessage temp = (ImageMessage) message;
                ps.setString(4, temp.GetImgPath());
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> GetAllChats() {
        String sql = "SELECT * FROM Chats";
        ArrayList<String> chats = new ArrayList<>();
        try (Connection conn = openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
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
    public ArrayList<Message> GetMessages(String chat) {
        String sql = "SELECT * FROM Messages WHERE chat = ? ORDER BY message_date ASC";
        ArrayList<Message> messages = new ArrayList<>();
        try (Connection conn = openConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String sender = rs.getString(1);
                    String chatName = rs.getString(2);
                    String msgType = rs.getString(3);
                    Timestamp ts = rs.getTimestamp(5);
                    LocalDateTime time = ts.toLocalDateTime();
                    if (msgType.equals("text")) {
                        String text = rs.getString(4);
                        Message m = new TextMessage(time, chatName, text, msgType);
                        m.SetSender(sender);
                        messages.add(m);
                    } else if (msgType.equals("image")) {
                        String imageUrl = rs.getString(4);
                        ImageMessage m = new ImageMessage(time, chatName, msgType);
                        m.SetImagePath(imageUrl);
                        m.SetSender(sender);
                        messages.add(m);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
