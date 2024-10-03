package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Employee;
import model.Product;

public interface Dao {
	// Connection management
	public void connect() throws SQLException;
	public void disconnect() throws SQLException;

	// Inventory management
	public ArrayList<Product> getInventory();
	public boolean writeInventory(ArrayList<Product> inventory);

	// Employee management
	public Employee getEmployee(int id, String password);

	// Product management
	public Product getProduct(int id);
}
