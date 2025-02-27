package dao;

import java.util.ArrayList;
import java.util.Date;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.Amount;
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
        ArrayList<Product> inventory = new ArrayList<>();
        
        try {
            MongoCollection<Document> collection = database.getCollection("inventory");
            FindIterable<Document> documents = collection.find();
            
            for (Document doc : documents) {
                Document wholesalerPriceDoc = (Document) doc.get("wholesalerPrice");
                Amount wholesalerPrice = null;
                if (wholesalerPriceDoc != null) {
                    wholesalerPrice = new Amount(wholesalerPriceDoc.getDouble("value"), wholesalerPriceDoc.getString("currency"));
                }

                int id = doc.getInteger("id", 0);
                String name = doc.getString("name");
                boolean available = doc.getBoolean("available", false);
                int stock = doc.getInteger("stock", 0);
                Date createdAt = doc.getDate("created_at");

                Product product = new Product(
                    id, 
                    name,
                    wholesalerPrice,
                    available,
                    stock,
                    createdAt
                );
                
                inventory.add(product);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving inventory: " + e.getMessage());
            e.printStackTrace();
        }
        
        return inventory;
    }

	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		// TODO Auto-generated method stub
		return false;
	}

    @Override
    public Employee getEmployee(int id, String password) {
        Employee employee = null;

        try {
            MongoCollection<Document> collection = database.getCollection("employees");

            Document query = new Document("id", id)
                                .append("password", password);

            FindIterable<Document> documents = collection.find(query);

            for (Document doc : documents) {
                employee = new Employee(doc.getInteger("id"), doc.getString("password"));
                break;
            }

        } catch (Exception e) {
            System.err.println("Error retrieving employee: " + e.getMessage());
            e.printStackTrace();
        }

        return employee;
    }

	@Override
	public Product getProduct(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProduct(Product product) {
	    try {
	        MongoCollection<Document> collection = database.getCollection("inventory");

	        Document productDoc = new Document()
	                .append("id", product.getId())
	                .append("name", product.getName())
	                .append("wholesalerPrice", new Document("value", product.getWholesalerPrice().getValue())
	                                             .append("currency", "€"))
	                .append("stock", product.getStock())
	                .append("available", product.isAvailable())
	                .append("created_at", new Date());

	        collection.insertOne(productDoc);
	    } catch (Exception e) {
	        System.err.println("Unable to add product: " + e.getMessage());
	        e.printStackTrace();
	    }
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
