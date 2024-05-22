package dao;

import model.Employee;

public interface Dao {
	public void connect();

	public Employee login(int user, String password);
	 
	public void disconnect();
}
