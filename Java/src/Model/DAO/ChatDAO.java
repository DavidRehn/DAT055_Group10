package src.Model.DAO;
import src.Model.Entities.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.OffsetDateTime;

public class ChatDAO {
    private final Database db;

    public ChatDAO(Database db){
        this.db = db;
    }

    // Metod för att hämta alla chatter för en användares id
    public List<GroupChat> getChatsForUser(int user_id) throws SQLException{
        String sql = """
                    SELECT * FROM Chats c
                    JOIN Chat_Members cm ON cm.chat_id = c.chat_id
                    WHERE cm.user_id = ?
                    ORDER BY c.created_at DESC
                """;
        List<GroupChat> chats = new ArrayList<>();
        
        try (Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1,user_id);

                try(ResultSet rs = ps.executeQuery()){
                    while (rs.next()) {
                      int chatID = rs.getInt("chat_id");
                      String title = rs.getString("title");
                      OffsetDateTime created_at = rs.getObject("created_at",OffsetDateTime.class);

                      chats.add(new GroupChat(chatID, title, created_at));
                    }
                }
            }
        return chats;
    }

    public Optional<GroupChat> getChatByID(int chat_id) throws SQLException {
        String sql = "SELECT * FROM Chats WHERE chat_id = ?";
        GroupChat temp;
        try (Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1,chat_id);

                try(ResultSet rs = ps.executeQuery()){
                    if (rs.next()) {
                        int chatID = rs.getInt("chat_id");
                        String title = rs.getString("title");
                        OffsetDateTime created_at = rs.getObject("created_at",java.time.OffsetDateTime.class);

                        return Optional.of(new GroupChat(chatID, title, created_at));
                    }
                }
            }
        return Optional.empty();
    }

   /*  public Optional<Integer> getIdByTitle(String title) throws SQLException {
        String sql = "SELECT chat_id FROM Chats WHERE title = ?";
         try (Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1,title);
                try(ResultSet rs = ps.executeQuery()){
                    if (rs.next()) {
                        return Optional.of(rs.getInt("chat_id"));
                    }
                }
            }
        return Optional.empty();
    }
    */

    // Metod för att skapa en GroupChat
    public Optional<GroupChat> createGroupChat(String title) throws SQLException {
        String sql = "INSERT INTO Chats (title) VALUES (?) RETURNING chat_id, title, created_at";
        try (Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,title);

             try(ResultSet rs = ps.executeQuery()) {
                    if(rs.next()){
                    return Optional.of(new GroupChat(
                        rs.getInt("chat_id"),
                        rs.getString("title"),
                        rs.getObject("created_at", java.time.OffsetDateTime.class)
                    ));
                }
                }
            }
        return Optional.empty();
    }

    // Metod för att radera en chat

    // Metod för att lägga till användare till en chat
    public boolean AddMemberToChat(int chat_id, int user_id) throws SQLException{
        String sql = "INSERT INTO Chat_Members (chat_id, user_id) VALUES (?,?)";
        try (Connection conn = db.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

        ps.setInt(1,chat_id);
        ps.setInt(2,user_id);

        int rowsAffected = ps.executeUpdate();

        return rowsAffected == 1;   // True om insert fungerade
        }
    }
}
