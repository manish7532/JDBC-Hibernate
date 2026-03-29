import java.sql.*;
import java.util.*;

public class BatchExecutionEx {

    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public BatchExecutionEx() {
        try {
            con = ConnectToDb.getConnection();
        } catch (ClassNotFoundException e) {
            System.out.println("Connection failed ClassNotFoundException " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connection failed SQLException " + e.getMessage());
        }
    }

    public void addEmployeesInBatch(List<Employee> employees) {
        try {
            // while inserting in batch set auto commit false, treats all the batch inserts
            // as a single transaction.
            con.setAutoCommit(false);
            pst = con.prepareStatement(
                    "INSERT INTO employees (first_name, last_name, email, salary, dept_id) VALUES(?,?,?,?,?)");
            for (Employee e : employees) {
                pst.setString(1, e.first_name);
                pst.setString(2, e.last_name);
                pst.setString(3, e.email);
                pst.setDouble(4, e.salary);
                pst.setInt(5, e.dept_id);
                pst.addBatch();
            }
            int[] result = pst.executeBatch();
            con.commit(); // Commit only if all batch items succeeded
            System.out.println("Batch insert successful. Records affected: " + result.length);
        } catch (SQLException e) {
            try {
                if (con != null) {
                    System.err.println("Transaction failed! Rolling back changes...");
                    con.rollback(); // Undo changes if batch fails
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public List<Employee> userInputEmployees() {
        Scanner sc = new Scanner(System.in);
        List<Employee> employeeList = new ArrayList<>();
        while (true) {
            System.out.println("Enter first_name:");
            String first_name = sc.nextLine();
            System.out.println("Enter last_name:");
            String last_name = sc.nextLine();
            System.out.println("Enter email:");
            String email = sc.nextLine();
            System.out.println("Enter salary:");
            double salary = sc.nextDouble();
            System.out.println("Enter Department Id:");
            int dept_id = sc.nextInt();
            employeeList.add(new Employee(first_name, last_name, email, salary, dept_id));
            sc.nextLine();
            System.out.println("Do you want to Enter More Employees(Y/N): ");
            String choice = sc.next();
            if (choice.equalsIgnoreCase("N")) {
                break;
            }
        }
        sc.close();
        return employeeList;
    }

    public List<Employee> createSampleEmployees() {
        return Arrays.asList(new Employee("Charlie", "Brown", "charlie.brown@email.com", 50000, 3),
                new Employee("Diana", "Prince", "diana.prince@email.com", 90000, 1));
    }

    public void closeResources() {
        try {
            if (con != null) {
                ConnectToDb.closeConnection();
                System.out.println("Connection Closed");
            }
        } catch (Exception e) {
            System.out.println("Error while closing connection: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        BatchExecutionEx b = new BatchExecutionEx();
        try {
            List<Employee> emps = b.createSampleEmployees();
            // List<Employee> emps1 = b.userInputEmployees();
            // b.addEmployeesInBatch(emps1);

        } finally {
            b.closeResources(); // Ensure connection is closed
        }
    }
}
