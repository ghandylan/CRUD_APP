import java.sql.*;

public class DBConnection {
    public static void main(String[] args) {
        try {
            // CONNECT TO MYSQL
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRUD_APP", "root", "");
            System.out.println("Connected to database");
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}