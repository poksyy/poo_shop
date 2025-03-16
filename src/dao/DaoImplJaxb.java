package dao;

import java.util.ArrayList;

import dao.jaxb.JaxbMarshaller;
import dao.jaxb.JaxbUnMarshaller;

import model.Employee;
import model.Product;
import model.ProductList;
import model.Sale;

public class DaoImplJaxb implements Dao {

	@Override
	public void connect() {
	}

	@Override
	public void disconnect() {
	}

	@Override
	public Employee getEmployee(int id, String password) {
		return null;
	}

	@Override
	public ArrayList<Product> getInventory() {
		// Initialize the product list by unmarshalling the XML data
		ProductList productList = new JaxbUnMarshaller().init();

		// Return the list of products if not null, else return an empty list
		return productList != null ? productList.getProducts() : new ArrayList<>();
	}

	// Export the inventory of products by marshalling the inventory to XML data
	public boolean writeInventory(ArrayList<Product> inventory) {
		ProductList productList = new ProductList();
		productList.setProducts(inventory);

		JaxbMarshaller marshaller = new JaxbMarshaller();

		boolean success = marshaller.init(productList);

		if (!success) {
			System.out.println("Error writing inventory.");
		}

		return success;
	}

	@Override
	public Product getProduct(int id) {
		return null;
	}

	@Override
	public void addProduct(Product product) {
	}

	@Override
	public void updateProduct(Product product) {
	}

	@Override
	public void deleteProduct(Product product) {
	}

	@Override
	public void addSale(Sale sale) {
	}

}
