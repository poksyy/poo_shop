package model;

import java.sql.SQLException;

import dao.Dao;
import dao.DaoImplJDBC;
import main.Logable;

public class Employee extends Person implements Logable {
	// create a Dao instance to interact with the database
	public static Dao dao = new DaoImplJDBC();

	// private final static integer EMPLOYEE_ID = 123;
	// private final static String PASSWORD = "test";

	public Employee(int employeeId, String password) {
		// call the constructor of the superclass (Person)
		super(name);
	}

	public static boolean login(int employeeId, String password) {
		// return user == EMPLOYEE_ID && password.equals(PASSWORD);

		try {
			// connect to db
			dao.connect();

			// get the employee from the db using the provided employeeId and password
			Employee validEmployee = dao.getEmployee(employeeId, password);

			// disconnect from db
			dao.disconnect();

			// return true if the employee is found
			return validEmployee != null;
		} catch (SQLException e) {
			// in case of SQL exception, print the stack trace and return false
			e.printStackTrace();
			return false;
		}
	}
}
