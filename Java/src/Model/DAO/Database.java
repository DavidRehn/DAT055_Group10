package src.Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final String URL;
    private final String USER;
    private final String PASSWORD;

    public Database(){
        this.URL = "jdbc:postgresql://localhost:5432/postgres";
        this.USER = "postgres";
        this.PASSWORD = "postgres";
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.URL, this.USER, this.PASSWORD);
    }
}
