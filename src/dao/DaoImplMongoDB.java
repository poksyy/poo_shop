package dao;

import java.util.ArrayList;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import model.Employee;
import model.Product;

public class DaoImplMongoDB implements Dao{

    private MongoClient mongoClient;
    private MongoDatabase database;
    
    @Override
    public void connect() {
        try {
        	// Get the URI from an environment variable
        	String mongoUri = System.getenv("MONGO_URI");
            if (mongoUri == null || mongoUri.isEmpty()) {
    			System.err.println("Error connecting to the database.");
            }

            mongoClient = MongoClients.create(mongoUri);
            database = mongoClient.getDatabase("poo-shop");
            } catch (Exception e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    @Override
    public void disconnect() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

	@Override
	public ArrayList<Product> getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employee getEmployee(int id, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProduct(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

}
