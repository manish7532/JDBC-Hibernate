import java.sql.*;

public class StatementEx {

    public static void main(String[] args) {

        try {
            Connection con;
            con = ConnectToDb.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT first_name, email, dept_id, salary, hire_date FROM employees;");
            int c = 0;
            while (rs.next()) {
                c++;
                System.out.print("Record(" + c + ")" + "\t");
                System.out.print(rs.getString("first_name") + "\t");
                System.out.print(rs.getString("email") + "\t");
                System.out.print(rs.getInt("dept_id") + "\t");
                System.out.print(rs.getFloat("salary") + "\t");
                System.out.print(rs.getDate("hire_date") + "\t");
                System.out.println();
            }
            st.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("SQLException occurred" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException occurred" + e.getMessage());
        }


    }
}