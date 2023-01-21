import java.sql.*;

public class DBConnection {
    private static DBConnection instance;
    public Connection connection;

    private DBConnection() {
        try {
            // CONNECT TO MYSQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRUD_APP", "root", "");
            System.out.println("Connected to database");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }
}
