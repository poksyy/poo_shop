package model;

import main.Logable;

public class Employee extends Person implements Logable {

	private final static int EMPLOYEE_ID = 123;
    private final static String PASSWORD = "test";

    public static boolean login(int user, String password) {
        return user == EMPLOYEE_ID && password.equals(PASSWORD);
    }
}
