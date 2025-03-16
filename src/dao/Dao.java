package dao;

import java.util.ArrayList;

import model.Employee;
import model.Product;
import model.Sale;

public interface Dao {
	// Connection management
	public void connect();

	public void disconnect();

	// Inventory management
	public ArrayList<Product> getInventory();

	public boolean writeInventory(ArrayList<Product> inventory);

	// Employee management
	public Employee getEmployee(int id, String password);

	// Product management
	public Product getProduct(int id);

	public void addProduct(Product product);

	public void updateProduct(Product product);

	public void deleteProduct(Product product);

	// Sales management
	public void addSale(Sale sale);

}
