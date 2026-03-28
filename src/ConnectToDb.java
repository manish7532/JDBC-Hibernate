import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDb {
    private static final String url = "jdbc:postgresql://localhost:5432/emp";
    private static final String username = "";
    private static final String password = "";
    static Connection con;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (con == null || con.isClosed()) { // Check if the connection is closed
            con = DriverManager.getConnection(url, username, password);
        }
        System.out.println("Connection Opened.");
        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close(); // Close the connection
                con = null; // Reset the connection object
            } catch (SQLException e) {
                System.out.println("Error while closing connection" + e.getMessage());
            }
        }
    }
}