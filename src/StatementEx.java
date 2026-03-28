import java.sql.*;

public class StatementEx {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = ConnectToDb.getConnection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT first_name, email, dept_id, salary, hire_date FROM employees;");
            int c = 0;
            while (rs.next()) {
                c++;
                System.out.print("Record(" + c + ")" + "\t");
                System.out.print(rs.getString("first_name") + "\t");
                System.out.print(rs.getString("email") + "\t");
                System.out.print(rs.getInt("dept_id") + "\t");
                System.out.print(rs.getFloat("salary") + "\t");
                System.out.print(rs.getTimestamp("hire_date") + "\t");
                System.out.println();
            }
            st.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("SQLException occurred" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException occurred" + e.getMessage());
        } finally {
            // Close ResultSet, Statement, and Connection
            try {
                if (rs != null)
                    rs.close();
                if (st != null)
                    st.close();
                if (con != null)
                    ConnectToDb.closeConnection();
                System.out.println("closed resources");
            } catch (SQLException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
            }
        }

    }
}