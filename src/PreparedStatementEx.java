import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PreparedStatementEx {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public PreparedStatementEx() {
        try {
            con = ConnectToDb.getConnection();
        } catch (ClassNotFoundException e) {
            System.out.println("Connection failed ClassNotFoundException " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connection failed SQLException " + e.getMessage());
        }
    }

    public void addEmployee(Employee employee) {
        try {
            pst = con.prepareStatement("INSERT INTO employees VALUES(?,?,?,?,?,?,?)");
            pst.setInt(1, employee.emp_id);
            pst.setString(2, employee.first_name);
            pst.setString(3, employee.last_name);
            pst.setString(4, employee.email);
            pst.setDouble(5, employee.salary);
            pst.setTimestamp(6, java.sql.Timestamp.valueOf(employee.hire_date));
            pst.setInt(7, employee.dept_id);
            int n = pst.executeUpdate();
            System.out.println("INSERTED " + n + " new employee Record successfully");
        } catch (SQLException e) {
            System.out.println("SQLException Occurred" + e.getMessage());
        } finally {
            try {
                if (pst != null)
                    pst.close();
            } catch (SQLException e) {
                System.out.println("Error while closing PreparedStatement: " + e.getMessage());
            }
        }
    }

    public void getAllEmployees() {
        try {
            pst = con.prepareStatement("SELECT * FROM employees");
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.print(rs.getInt("emp_id") + "\t");
                System.out.print(rs.getString("first_name") + " ");
                System.out.print(rs.getString("last_name") + "\t");
                System.out.print(rs.getString("email") + "\t");
                System.out.print(rs.getDouble("salary") + "\t");
                System.out.print(rs.getTimestamp("hire_date") + "\t");
                System.out.print(rs.getInt("dept_id") + "\n");
            }
        } catch (SQLException e) {
            System.out.println("SQLException Occurred" + e.getMessage());
        } finally {
            try {
                if (pst != null)
                    pst.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
            }
        }
    }

    public void getEmployeeByFirstName(String first_name) {
        try {
            pst = con.prepareStatement("SELECT * FROM employees WHERE first_name = ?");
            pst.setString(1, first_name);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.print(rs.getInt("emp_id") + "\t");
                System.out.print(rs.getString("first_name") + " ");
                System.out.print(rs.getString("last_name") + "\t");
                System.out.print(rs.getString("email") + "\t");
                System.out.print(rs.getDouble("salary") + "\t");
                System.out.print(rs.getTimestamp("hire_date") + "\t");
                System.out.print(rs.getInt("dept_id") + "\n");
            }
        } catch (SQLException e) {
            System.out.println("SQLException Occurred" + e.getMessage());
        } finally {
            try {
                if (pst != null)
                    pst.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
            }
        }
    }

    public void getEmployeeById(int emp_id) {
        try {
            pst = con.prepareStatement("SELECT * FROM employees WHERE emp_id = ?");
            pst.setInt(1, emp_id);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.print(rs.getInt("emp_id") + "\t");
                System.out.print(rs.getString("first_name") + " ");
                System.out.print(rs.getString("last_name") + "\t");
                System.out.print(rs.getString("email") + "\t");
                System.out.print(rs.getDouble("salary") + "\t");
                System.out.print(rs.getDate("hire_date") + "\t");
                System.out.print(rs.getInt("dept_id") + "\n");
            }
        } catch (SQLException e) {
            System.out.println("SQLException Occurred" + e.getMessage());
        } finally {
            try {
                if (pst != null)
                    pst.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
            }
        }
    }

    public void getEmployeeWithMaxSalary() {
        try {
            pst = con.prepareStatement("SELECT * FROM employees WHERE salary IN (SELECT MAX(salary) from employees)");
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.print(rs.getInt("emp_id") + "\t");
                System.out.print(rs.getString("first_name") + " ");
                System.out.print(rs.getString("last_name") + "\t");
                System.out.print(rs.getString("email") + "\t");
                System.out.print(rs.getDouble("salary") + "\t");
                System.out.print(rs.getTimestamp("hire_date") + "\t");
                System.out.print(rs.getInt("dept_id") + "\n");
            }
        } catch (SQLException e) {
            System.out.println("SQLException Occurred" + e.getMessage());
        } finally {
            try {
                if (pst != null)
                    pst.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
            }
        }
    }

    public void getSecondMostSalariedEmployee() {
        try {
            pst = con.prepareStatement(
                    "SELECT * FROM employees WHERE salary IN (SELECT MAX(salary) from employees where salary <(SELECT MAX(salary) from employees))");
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.print(rs.getInt("emp_id") + "\t");
                System.out.print(rs.getString("first_name") + " ");
                System.out.print(rs.getString("last_name") + "\t");
                System.out.print(rs.getString("email") + "\t");
                System.out.print(rs.getDouble("salary") + "\t");
                System.out.print(rs.getTimestamp("hire_date") + "\t");
                System.out.print(rs.getInt("dept_id") + "\n");
            }
        } catch (SQLException e) {
            System.out.println("SQLException Occurred" + e.getMessage());
        } finally {
            try {
                if (pst != null)
                    pst.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
            }
        }
    }

    // get all employees in 'dept_name' who are working on the 'proj_name'
    public void getAllEmployeesByDeptAndProject(String dept_name, String proj_name) {
        try {
            pst = con.prepareStatement("SELECT " +
                    "e.first_name, " +
                    "e.last_name, " +
                    "e.salary, " +
                    "d.dept_name, " +
                    "p.proj_name, " +
                    "p.budget " +
                    "FROM employees e " +
                    "JOIN departments d ON e.dept_id =d.dept_id " +
                    "JOIN project_assignments pa ON e.emp_id =pa.emp_id " +
                    "JOIN projects p ON pa.proj_id = p.proj_id " +
                    "WHERE d.dept_name = ? AND p.proj_name = ?");
            pst.setString(1, dept_name);
            pst.setString(2, proj_name);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.print(rs.getString("first_name") + " ");
                System.out.print(rs.getString("last_name") + "\t");
                System.out.print(rs.getDouble("salary") + "\t");
                System.out.print(rs.getString("dept_name") + "\t");
                System.out.print(rs.getString("proj_name") + "\t");
                System.out.print(rs.getDouble("budget") + "\n");
            }
        } catch (SQLException e) {
            System.out.println("SQLException Occurred" + e.getMessage());
        } finally {
            try {
                if (pst != null)
                    pst.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
            }
        }
    }

    // get all employees who are working on the 'proj_name'
    public void getAllEmployeesByProject(String proj_name) {
        try {
            pst = con.prepareStatement("SELECT " +
                    "e.first_name, " +
                    "e.last_name, " +
                    "e.salary, " +
                    "p.proj_name, " +
                    "pa.role, " +
                    "p.budget " +
                    "FROM employees e " +
                    "JOIN project_assignments pa ON e.emp_id = pa.emp_id " +
                    "JOIN projects p ON pa.proj_id = p.proj_id " +
                    "WHERE p.proj_name = ?");
            pst.setString(1, proj_name);
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.print(rs.getString("first_name") + " ");
                System.out.print(rs.getString("last_name") + "\t");
                System.out.print(rs.getDouble("salary") + "\t");
                System.out.print(rs.getString("proj_name") + "\t");
                System.out.print(rs.getString("role") + "\t");
                System.out.print(rs.getDouble("budget") + "\n");
            }
        } catch (SQLException e) {
            System.out.println("SQLException Occurred" + e.getMessage());
        } finally {
            try {
                if (pst != null)
                    pst.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
            }
        }
    }

    public void deleteEmployeeById(int emp_id) {
        try {
            pst = con.prepareStatement("DELETE FROM employees WHERE emp_id = ?");
            pst.setInt(1, emp_id);
            pst.executeUpdate(); // let's see what error we get
            System.out.println("1 Record Deleted");
        } catch (SQLException e) {
            System.out.println("SQLException Occurred" + e.getMessage());
        } finally {
            try {
                if (pst != null)
                    pst.close();
            } catch (SQLException e) {
                System.out.println("Error while closing preparedStatement: " + e.getMessage());
            }
        }
    }

    public void updateEmployees(LocalDateTime date) {
        try {
            pst = con.prepareStatement("UPDATE employees set hire_date = ? ");
            pst.setTimestamp(1, java.sql.Timestamp.valueOf(date));
            pst.executeUpdate();
            System.out.println("Updated Hire Dates of all employees to now().");
        } catch (SQLException e) {
            System.out.println("SQLException Occurred" + e.getMessage());
        } finally {
            try {
                if (pst != null)
                    pst.close();
            } catch (SQLException e) {
                System.out.println("Error while closing preparedStatement: " + e.getMessage());
            }
        }
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
        PreparedStatementEx p = new PreparedStatementEx();
        try {
            // Employee emp1 = new Employee(6, "John", "Doe", "john@email.com", 97000, LocalDateTime.now(), 1);

            // update all employees hire_date to now() timestamp;
            // p.updateEmployees(LocalDateTime.now());

            // Insert 1 employee
            // p.addEmployee(emp1);

            // get employee by firstName
            // p.getEmployeeByFirstName(emp1.first_name);śśś

            // get employee by id
            // p.getEmployeeById(1);

            // get all employees
            // p.getAllEmployees();

            // get the highest salaried employee
            // p.getEmployeeWithMaxSalary();

            // get second-highest salaried employee
            // p.getSecondMostSalariedEmployee();

            // get all employees in 'Engineering' who are working on the 'Cloud Migration'
            // project and show their current salary
            // p.getAllEmployeesByDeptAndProject("Engineering", "Cloud Migration");

            // get all employees who are working on the 'AI ChatBot' project and show their
            // current salary
            p.getAllEmployeesByProject("AI Chatbot");

            // delete by id
            // p.deleteEmployeeById(6);

        } finally {
            p.closeResources(); // Ensure connection is closed
        }
    }
}
