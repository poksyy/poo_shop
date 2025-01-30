package model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "product")
@XmlType(propOrder = { "available", "wholesalerPrice", "publicPrice", "stock" })
public class Product {
	private int id;
	private String name;
	private Amount publicPrice;
	private Amount wholesalerPrice;
	private boolean available;
	private int stock;
	private String currency;

	// Static counter to assign unique IDs
	private static int totalProducts;

	public static double EXPIRATION_RATE = 0.60;

	// Constructor for creating a product
	public Product(String name, double wholesalerPrice, boolean available, int stock) {
		this.id = ++totalProducts;
		this.name = name;
		this.wholesalerPrice = new Amount(wholesalerPrice);
		this.publicPrice = new Amount(wholesalerPrice * 2);
		this.available = available;
		this.stock = stock;
	}

	// Constructor for reading from XML (with name only)
	public Product(String name) {
		this.id = ++totalProducts;
		this.name = name;
	}

	// Constructor for sql db
	public Product(int id, String name, Amount publicPrice, Amount wholesalerPrice, int stock) {
		this.id = id;
		this.name = name;
	    this.publicPrice = new Amount(wholesalerPrice.getValue() * 2);
		this.wholesalerPrice = wholesalerPrice;
		this.stock = stock;
		
	}
	
	// Default constructor
	public Product() {
		this.id = ++totalProducts;
	}



	// Override toString method for better display
	@Override
	public String toString() {
		return "\nProduct: " + "\nName\t\t\t" + name + "\nPublicPrice\t\t" + publicPrice + "\nWholesalerPrice\t\t"
				+ wholesalerPrice + "\nStock\t\t\t" + stock + "\n";
	}

	// Getters and Setters
	@XmlAttribute(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Amount getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(Amount publicPrice) {
		this.publicPrice = publicPrice;
	}

	@XmlElement(name = "wholesalerPrice")
	public Amount getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(Amount wholesalerPrice) {
		this.wholesalerPrice = wholesalerPrice;
	}

	public boolean isAvailable() {
		return this.stock > 0;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@XmlElement(name = "stock")
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void expire() {
		double increasedPrice = this.publicPrice.getValue() * EXPIRATION_RATE;
		this.publicPrice.setValue(increasedPrice);
	}

	public void setCurrency(String value) {
		this.currency = value;
	}

	public static int getTotalProducts() {
		return totalProducts;
	}
}
