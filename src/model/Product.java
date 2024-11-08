package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

//Annotates the class as a root element in XML serialization with the name "product"
@XmlRootElement(name = "product")
//Specifies the order in which the properties will appear in the XML document
@XmlType(propOrder = { "id", "name", "wholesalerPrice", "publicPrice", "available", "stock", "totalProducts" })
public class Product {
	private int id;
	private String name;
	private Amount publicPrice;
	private Amount wholesalerPrice;
	private boolean available;
	private int stock;

	private static int totalProducts = 0;
	public static double EXPIRATION_RATE = 0.60;

	// Constructor currently in use when creating a product
	public Product(String name, double wholesalerPrice, boolean available, int stock) {
		super();
		this.id = ++totalProducts;
		this.name = name;
		this.wholesalerPrice = new Amount(wholesalerPrice);
		this.publicPrice = new Amount(wholesalerPrice * 2);
		this.available = available;
		this.stock = stock;
	}

	// Constructor currently in use for reading the product from XML SAX
	public Product(String name) {
		this.id = ++totalProducts;
		this.name = name;
	}

	// Constructor currently in use for reading the product from XML JAXB
	public Product() {
		this.id = ++totalProducts;
	}

	// Override toString method
	@Override
	public String toString() {
		return "\nProduct:" + "\nName\t\t\t" + name + "\nPublicPrice\t\t" + publicPrice + "\nWholesalerPrice\t\t"
				+ wholesalerPrice + "\nStock\t\t\t" + stock + "\n";
	}

	// Getters and Setters
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

	// Specifies that the stock property should be marshalled/unmarshalled as an XML
	// element named "wholesalerPrice"
	@XmlElement(name = "wholesalerPrice")
	public Amount getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(Amount wholesalerPrice) {
		this.wholesalerPrice = wholesalerPrice;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	// Specifies that the stock property should be marshalled/unmarshalled as an XML
	// element named "stock"
	@XmlElement(name = "stock")
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(int totalProducts) {
		this.totalProducts = totalProducts;
	}

	public void expire() {
		double increasedPrice = this.publicPrice.getValue() * EXPIRATION_RATE;
		this.publicPrice.setValue(increasedPrice);
	}

	public void calculatePublicPrice() {
		// Check if wholesalerPrice is not null
		if (this.wholesalerPrice != null) {
			// Calculate the public price as double the wholesaler price
			this.publicPrice = new Amount(this.wholesalerPrice.getValue() * 2); // Calcula como el doble del
																				// wholesalerPrice
		}
	}

	public void setCurrency(String value) {
		// TODO Auto-generated method stub

	}
}
