import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDb {
    private static final String url = "jdbc:postgresql://localhost:5432/emp";
    private static final String username = "";
    private static final String password = "";
    static Connection con;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (con == null) {
            con = DriverManager.getConnection(url, username, password);
            return con;
        } else {
            return con;
        }
    }
}
