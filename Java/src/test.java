package src;
import java.sql.*;
import java.util.Properties;

public class test {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/postgres"; //mydb??
        String username = "postgres";
        String password = "postgres";

        try (Connection conn = DriverManager.getConnection(url,username,password)) {

            try (PreparedStatement ps = conn.prepareStatement("SELECT now()");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) System.out.println(rs.getString(1));
            }

             try(PreparedStatement ps = conn.prepareStatement("INSERT INTO Users VALUES (?,?)");){
            ps.setString(1, "Elliot");
            ps.setString(2,"123");
            ps.executeUpdate();
            }
                catch (SQLException e) {
                    System.out.println("Failure");
                    }
    }
}
}

