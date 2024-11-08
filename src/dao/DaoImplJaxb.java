package dao;

import java.util.ArrayList;

import dao.jaxb.JaxbUnMarshaller;

import model.Employee;
import model.Product;
import model.ProductList;

public class DaoImplJaxb implements Dao {

	@Override
	public void connect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	// Get the inventory of products by unmarshalling the XML data
	@Override
	public ArrayList<Product> getInventory() {
		// Initialize the product list by unmarshalling the XML data
		ProductList productList = new JaxbUnMarshaller().init();

		// Return the list of products if not null, else return an empty list
		return productList != null ? productList.getProducts() : new ArrayList<>();
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

}
