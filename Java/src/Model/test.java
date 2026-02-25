package src.Model;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import src.Model.DAO.*;
import src.Model.Entities.*;

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
        String sql = "SELECT * From Chats";
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

        

        Database db = new Database();               // Måste skapas vid början
        UserDAO uDAO = new UserDAO(db);             // Måste också skapas och db användas som argument
        ChatDAO cDAO = new ChatDAO(db);


        Optional<ChatUser> userOpt = uDAO.checkIfAccountExists("John","123abc");

        if (userOpt.isPresent()){
            ChatUser user = userOpt.get();
            System.out.println("Login success");
        }
        else{
            System.out.println("Login fail");
        }

        uDAO.createUser("Elliot","haha");


        Optional<ChatUser> userOpt2 = uDAO.findById(2);

        if (userOpt2.isPresent()){
            ChatUser user2 = userOpt2.get();
            System.out.println("User found");
            System.out.println(user2.getId());
        }
        else{
            System.out.println("User not found");
        }

        System.out.println(uDAO.DeleteUser("Elliot"));

        cDAO.createGroupChat("Ny gruppchatt");
        cDAO.AddMemberToChat(1,2);
        List<GroupChat> temp = cDAO.getChatsForUser(2);
        System.out.println(temp.get(0).getChatTitle());
        

        
    }
}

