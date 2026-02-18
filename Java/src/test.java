package src;
import java.sql.*;
import java.util.Properties;

public class test {

    public test(){
        // 
    }

// Idén är att ingen connection med databasen är permanent, utan vid behov så använder modellen denna metod för att göra det som behövs
// med databasen, sen stäng kopplingen i metoden som anropade getConnection automatiskt. 
    private Connection getConnection() throws SQLException{
        String url = "jdbc:postgresql://localhost:5432/postgres"; //mydb??
        String username = "postgres";
        String password = "postgres";
        return DriverManager.getConnection(url, username, password);
    }

    // Exempel på hur gruppchatt skulle kunna skapas (utan medlemmar i början)
    public Chat createGroupChat(String title) throws SQLException{
        String sql = """
                INSERT INTO Chats (chat_type, title)
                VALUES ('group', ?)
                RETURNING chat_id, chat_type, title, created_at
                """;

        try(Connection conn = getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1, title);

                try(ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    return new Chat(
                        rs.getInt("chat_id"),
                        rs.getString("chat_type"),
                        rs.getString("title"),
                        rs.getObject("created_at", java.time.OffsetDateTime.class)
                    );
                }
            }
        }
    }

    // Exempel på hur man lägger till medlemmar till en gruppchatt
    public void addMember(int chat_id, int user_id) throws SQLException {
        String sql = "INSERT INTO Chat_Members (chat_id, user_id) VALUES (?,?)";
        try(Connection conn = getConnection()){
             try(PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1, chat_id);
                ps.setInt(2, user_id);
                ps.executeUpdate();
             }
        }
    }

    // Two functions returning the contents of the user/chat tables. Useful for database testing but shouldnt be used in final.
    public String getAllUsers() throws SQLException {
        String sql = "SELECT * From users";
        try(Connection conn = getConnection()){
             try(PreparedStatement ps = conn.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    
                    String temp="";
                    temp = temp+"ID: "+rs.getString("user_id")+" Name: "+rs.getString("name")+"\n";
                    while(rs.next()){
                
                        temp = temp+"ID: "+rs.getString("user_id")+" Name: "+rs.getString("name")+"\n";
                
                        }
                    return temp;
                }
                return "failed";
             }
        }
    }
    public String getAllChats() throws SQLException {
        String sql = "SELECT * From chats";
        try(Connection conn = getConnection()){
             try(PreparedStatement ps = conn.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    
                    
                    String temp="";
                    temp = temp+"ID: "+rs.getString("chat_id")+" Name: "+rs.getString("title")+"\n";
                    while(rs.next()){
                
                        temp = temp+"ID: "+rs.getString("chat_id")+" Name: "+rs.getString("title")+"\n";
                
                        }
                    return temp;
                    
                }
                return "failed";
             }
        }
    }
        
    public static void main(String[] args) throws Exception {
        test app = new test();
        String sql = "INSERT INTO Users (name, login) VALUES (?,?)";
        try(Connection conn = app.getConnection()){
             try(PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1, "John");
                ps.setString(2, "123abc");
                ps.executeUpdate();
             }
              try(PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1, "Ron");
                ps.setString(2, "456def");
                ps.executeUpdate();
             }
        }

        
        Chat groupchat1 = app.createGroupChat("AwesomeGruppChatt"); 
        app.addMember(1, 1);
        app.addMember(1, 2);
    }
}

