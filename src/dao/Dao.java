package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Employee;
import model.Product;

public interface Dao {
	public void connect() throws SQLException;

	public ArrayList<Product> getInventory();
	
	public boolean writeInventory(ArrayList<Product> inventory);
	
	public Employee getEmployee(int id, String password);

	public Product getProduct(int id);

	public void disconnect() throws SQLException;
}
