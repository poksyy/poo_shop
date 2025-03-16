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
import model.Sale;

public class DaoImplMongoDB implements Dao {

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
	public Product getProduct(int id) {
		return null;
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
	                wholesalerPrice = new Amount(
	                    wholesalerPriceDoc.getDouble("value"),
	                    wholesalerPriceDoc.getString("currency")
	                );
	            }

	            Document publicPriceDoc = (Document) doc.get("publicPrice");
	            Amount publicPrice = null;
	            if (publicPriceDoc != null) {
	                publicPrice = new Amount(
	                    publicPriceDoc.getDouble("value"),
	                    publicPriceDoc.getString("currency")
	                );
	            }

	            int id = doc.getInteger("id", 0);
	            String name = doc.getString("name");
	            boolean available = doc.getBoolean("available", false);
	            int stock = doc.getInteger("stock", 0);
	            Date createdAt = doc.getDate("created_at");

	            Product product = new Product(id, name, wholesalerPrice, publicPrice, available, stock, createdAt);

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
		try {
			MongoCollection<Document> collection = database.getCollection("historical_inventory");

			ArrayList<Document> historyDocuments = new ArrayList<>();

			for (Product product : inventory) {
				Document historyDoc = new Document().append("idProduct", product.getId())
						.append("name", product.getName())
						.append("wholesalerPrice",
								new Document("value", product.getWholesalerPrice().getValue()).append("currency", "€"))
						.append("stock", product.getStock()).append("timestamp", new Date());

				historyDocuments.add(historyDoc);
			}

			if (!historyDocuments.isEmpty()) {
				collection.insertMany(historyDocuments);
				System.out.println(
						"Inventory history successfully exported: " + inventory.size() + " products inserted.");
				return true;
			}

		} catch (Exception e) {
			System.err.println("Unable to export inventory history: " + e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Employee getEmployee(int id, String password) {
		Employee employee = null;

		try {
			MongoCollection<Document> collection = database.getCollection("employees");

			Document query = new Document("id", id).append("password", password);

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
	public void addProduct(Product product) {
		try {
			MongoCollection<Document> collection = database.getCollection("inventory");

			Document productDoc = new Document().append("id", product.getId()).append("name", product.getName())
					.append("wholesalerPrice",
							new Document("value", product.getWholesalerPrice().getValue()).append("currency", "€"))
					.append("stock", product.getStock()).append("available", product.isAvailable())
					.append("created_at", new Date());

			collection.insertOne(productDoc);
		} catch (Exception e) {
			System.err.println("Unable to add product: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void updateProduct(Product product) {
		try {
			MongoCollection<Document> collection = database.getCollection("inventory");

			Document query = new Document("id", product.getId());

			Document updateFields = new Document().append("name", product.getName())
					.append("wholesalerPrice",
							new Document("value", product.getWholesalerPrice().getValue()).append("currency", "€"))
					.append("stock", product.getStock()).append("available", product.isAvailable());

			Document updateOperation = new Document("$set", updateFields);

			collection.updateOne(query, updateOperation);
			System.out.println("Product updated successfully (ID: " + product.getId() + ").");

		} catch (Exception e) {
			System.err.println("Unable to update product: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProduct(Product product) {
		try {
			MongoCollection<Document> collection = database.getCollection("inventory");

			Document query = new Document("id", product.getId());

			collection.deleteOne(query);
			System.out.println("Product deleted successfully (ID: " + product.getId() + ").");

		} catch (Exception e) {
			System.err.println("Unable to delete product: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void addSale(Sale sale) {
		try {
			MongoCollection<Document> collection = database.getCollection("sales");

			ArrayList<Document> productDocs = new ArrayList<>();
			for (Product product : sale.getProducts()) {
				Document productDoc = new Document().append("id", product.getId()).append("name", product.getName())
						.append("publicPrice",
								new Document("value", product.getPublicPrice().getValue()).append("currency", "€"))
						.append("stock", product.getStock()).append("available", product.isAvailable());
				productDocs.add(productDoc);
			}

			Document saleDoc = new Document().append("saleId", sale.getSaleId())
					.append("clientName", sale.getClientName()).append("products", productDocs)
					.append("amount", new Document("value", sale.getAmount().getValue()).append("currency", "€"))
					.append("date", sale.getDate()).append("saved", sale.isSaved());

			collection.insertOne(saleDoc);
			System.out.println("Sale added successfully!");

		} catch (Exception e) {
			System.err.println("Error adding sale: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
