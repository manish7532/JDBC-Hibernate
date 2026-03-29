import java.time.LocalDateTime;

public class Employee {
    int emp_id;
    String first_name;
    String last_name;
    String email;
    Double salary;
    LocalDateTime hire_date;
    int dept_id;

    public Employee(int emp_id, String first_name, String last_name, String email, double salary,
            LocalDateTime hire_date, int dept_id) {
        this.emp_id = emp_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.salary = salary;
        this.hire_date = hire_date;
        this.dept_id = dept_id;
    }

    // Partial constructor
    public Employee(String first_name, String last_name, String email, double salary, int dept_id) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.salary = salary;
        this.dept_id = dept_id;
    }
}
