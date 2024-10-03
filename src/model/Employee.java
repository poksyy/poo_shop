package model;

import java.sql.SQLException;

import dao.Dao;
import dao.DaoImplJDBC;
import main.Logable;

public class Employee extends Person implements Logable {
    // Create a DAO instance to interact with the database
    public static Dao dao = new DaoImplJDBC();

	// private final static integer EMPLOYEE_ID = 123;
	// private final static String PASSWORD = "test";
    
    // Constructor
    public Employee(int employeeId, String password) {
        super(name);
    }

    // Static method for login
    public static boolean login(int employeeId, String password) {
        try {
            // Connect to the database
            dao.connect();

            // Get the employee from the database using the provided employeeId and password
            Employee validEmployee = dao.getEmployee(employeeId, password);

            // Disconnect from the database
            dao.disconnect();

            // Return true if the employee is found
            return validEmployee != null;
        } catch (SQLException e) {
            // In case of SQL exception, print the stack trace and return false
            e.printStackTrace();
            return false;
        }
    }
}
