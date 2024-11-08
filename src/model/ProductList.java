package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Specifies the root element name for XML marshalling/unmarshalling
@XmlRootElement(name = "products")
public class ProductList {

	// List to hold all products
	private ArrayList<Product> products = new ArrayList<>();

	// Contructor to initialize JAXB
	public ProductList() {
	}

	// Constructor to initialize with a list of products
	public ProductList(ArrayList<Product> products) {
		this.products = products;
	}

	// Setter method for the products list
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	// Getter method to retrieve the list of products, annotated for XML
	// unmarshalling/marshalling
	// Specifies that each item in the list is mapped to a 'product' XML element
	@XmlElement(name = "product")
	public ArrayList<Product> getProducts() {
		return products;
	}
}
