package src.Model.DAO;
import java.sql.*;
import java.util.Optional;
import src.Model.Entities.ChatUser;     // Så att vi kan returnera objekt ifall användare finns i databasen

// Denna klass använder en metod som finns i Database.java

public class UserDAO{
    private final Database db;

    public UserDAO(Database db){
        this.db = db;
    }

    // Metod för att kolla login
    public Optional<ChatUser> checkIfAccountExists(String name, String login) throws SQLException{
        String sql = "SELECT user_id, name, login FROM Users WHERE name = ? AND login = ?";
        try (Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){

                ps.setString(1,name);
                ps.setString(2,login);

                try(ResultSet rs = ps.executeQuery()){
                    if (rs.next()) {
                        int id = rs.getInt("user_id");
                        String username = rs.getString("name");
                        String userlogin = rs.getString("login");

                        return Optional.of(new ChatUser(username, userlogin, id));
                    }
                }
            }
        return Optional.empty();
    }

    // Metod för att skapa användare 
    public ChatUser createUser(String name, String login) throws SQLException {
        String sql = "INSERT INTO Users (name, login) VALUES (?,?) RETURNING user_id, name, login";
        try (Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1,name);
                ps.setString(2,login);

                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()) {
                        int id = rs.getInt("user_id");
                        String username = rs.getString("name");
                        String userlogin = rs.getString("login");

                        return new ChatUser(username, userlogin, id);
                    }
                }
            }
        throw new SQLException("Creating user failed, no row returned");
    }

    // Metod för att uppdatera ?

    // Metod för att radera

    // Metod för att hämta all info av en användare och returnera objekt
    public Optional<ChatUser> findById(int userId) throws SQLException {
        String sql = "SELECT user_id, name, login FROM Users WHERE user_id = ?";
        try (Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1, userId);

                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()) {
                        int id = rs.getInt("user_id");
                        String username = rs.getString("name");
                        String userlogin = rs.getString("login");

                        return Optional.of(new ChatUser(username, userlogin, id));
                    }
                }
            }
        return Optional.empty();
    }
}