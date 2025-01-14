package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {

	private Connection connection;

	public DaoImplJDBC() {
	}

	// Connection management
	@Override
	public void connect() {
		// Database connection
		String url = "jdbc:mysql://localhost:3306/pooshop";
		String user = "root";
		String pass = "";
		try {
			this.connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.err.println("Error connecting to the database.");
			e.getMessage();
		}
	}

	@Override
	public void disconnect() {
		// Database disconnection
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.println("Error disconnecting to the database.");
				e.getMessage();
			}
		}
	}

	// Employee management
	@Override
	public Employee getEmployee(int id, String password) {
		Employee employee = null;

		// query to select employeeId and password from db
		String query = "select * from employee where id = ? AND password = ?";

		// set in ps with the select values
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setInt(1, id);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					// create an object passing the values to the Employee constructor
					employee = new Employee(rs.getInt(1), rs.getString(2));
				}
			}
		} catch (SQLException e) {
			// in case of SQL exception, print the stack trace
			e.printStackTrace();
		}
		return employee;
	}

	// Product management
	@Override
	public Product getProduct(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {

		// query to insert the product to an export table
		String query = "INSERT INTO historical_inventory (id_product, name, wholesalerPrice, stock, created_at) "
				+ "VALUES (?, ?, ?, ?, NOW())";

		// set in ps with the select values
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			for (Product product : inventory) {
				ps.setInt(1, product.getId());
				ps.setString(2, product.getName());
				ps.setDouble(3, product.getWholesalerPrice().getValue());
				ps.setInt(4, product.getStock());
				ps.addBatch();
			}

			// if the registration succeeds, display a message
			int[] result = ps.executeBatch();
			System.out.println("Inventory exported successfully: " + result.length + " products inserted.");

			return true;
		} catch (SQLException e) {
			// in case of SQL exception, print the stack trace
			System.err.println("Unable to export inventory to file: " + e.getMessage());
			return false;
		}
	}

	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> products = new ArrayList<>();

		// query to select the products from the db and add them to the array products
		String query = "SELECT * FROM products";

		// set in ps with the select values
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					products.add(new Product(rs.getInt(1), rs.getString(2), new Amount(rs.getDouble(3)),
							new Amount(rs.getDouble(3)), rs.getInt(5)));
				}
			}
		} catch (SQLException e) {
			// in case of SQL exception, print the stack trace
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public void addProduct(Product product) {
	    
		// query to insert a new product into the database
	    String insertQuery = "INSERT INTO products (name, wholesalerPrice, stock) VALUES (?, ?, ?)";

	    try (PreparedStatement insertPs = connection.prepareStatement(insertQuery)) {
	        insertPs.setString(1, product.getName());
	        insertPs.setDouble(2, product.getWholesalerPrice().getValue());
	        insertPs.setInt(3, product.getStock());

	        // execute the insert statement
	        int rowsInserted = insertPs.executeUpdate();

	        if (rowsInserted > 0) {
	            System.out.println("Product added successfully: " + product.getName());
	        }
	    } catch (SQLException e) {
	        // in case of SQL exception, print the stack trace
	        System.err.println("Unable to add product: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	@Override
	public void updateProduct(Product product) {
		
	    // query to update only the stock of an existing product in the database
	    String query = "UPDATE products SET stock = ? WHERE name = ?";

	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	    	
	        // set the stock value and the product ID in the prepared statement
	        ps.setInt(1, product.getStock());
	        ps.setString(2, product.getName());

	        // execute the update statement
	        int rowsUpdated = ps.executeUpdate();

	        if (rowsUpdated > 0) {
	            System.out.println("Product stock updated successfully: " + product.getName());
	        } else {
	            System.err.println("Product with name " + product.getName() + " not found for update.");
	        }
	    } catch (SQLException e) {
	        // in case of SQL exception, print the stack trace
	        System.err.println("Unable to update product stock: " + e.getMessage());
	        e.printStackTrace();
	    }
	}



	@Override
	public void deleteProduct(Product product) {
	    
		// query to delete a product by its id
	    String query = "DELETE FROM products WHERE id = ?";

	    try (PreparedStatement ps = connection.prepareStatement(query)) {
	        // set the product id in the prepared statement
	        ps.setInt(1, product.getId());

	        // execute the delete statement
	        int rowsDeleted = ps.executeUpdate();

	        if (rowsDeleted > 0) {
	            System.out.println("Product (id: " + product.getId() + ") deleted successfully: " +  product.getName());
	        } else {
	            System.err.println("Product with name " +  product.getName() + " and " + product.getId() + "not found for deletion.");
	        }
	    } catch (SQLException e) {
	        // in case of SQL exception, print the stack trace
	        System.err.println("Unable to delete product: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}
