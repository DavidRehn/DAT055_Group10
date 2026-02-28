package src.Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    public void AddChat(Chat chat){
        String sql = "INSERT INTO ChatsS VALUES (?) RETURNING chat_id, title, created_at";
    }


}
