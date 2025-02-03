package model;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Transient
    private Amount publicPrice;
    
    @Transient
    private Amount wholesalerPrice;
    
    @Column(name = "price") // New field that stores the integer value from wholesalerPrice
    private double price;

    @Column(name = "stock")
    private int stock;
    
    @Transient // This field is not stored in the database
    private boolean available;
    
    @Transient
    private String currency;

    // Static counter to assign unique IDs (not stored in the database)
    @Transient
    private static int totalProducts;

    @Transient
    public static double EXPIRATION_RATE = 0.60;

    // Default constructor
    public Product() {
        this.id = ++totalProducts;
    }
    
    // Constructor to create a product
	public Product(String name, double wholesalerPrice, boolean available, int stock) {
		this.id = ++totalProducts;
		this.name = name;
		this.wholesalerPrice = new Amount(wholesalerPrice);
		this.publicPrice = new Amount(wholesalerPrice * 2);
		this.available = available;
		this.stock = stock;
	}

    // Constructor for SQL database
	public Product(int id, String name, Amount publicPrice, Amount wholesalerPrice, int stock) {
		this.id = id;
		this.name = name;
	    this.publicPrice = new Amount(wholesalerPrice.getValue() * 2);
		this.wholesalerPrice = wholesalerPrice;
		this.stock = stock;
		
	}
	
	// Constructor for reading from XML (with name only)
	public Product(String name) {
		this.id = ++totalProducts;
		this.name = name;
	}
    

    @Override
    public String toString() {
        return "\nProduct: " + "\nName\t\t\t" + name + "\nPublicPrice\t\t" + publicPrice +
               "\nWholesalerPrice\t\t" + wholesalerPrice + "\nPrice\t\t\t" + price + "\nStock\t\t\t" + stock + "\n";
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Amount getWholesalerPrice() {
        return wholesalerPrice;
    }

    public void setWholesalerPrice(Amount wholesalerPrice) {
        this.wholesalerPrice = wholesalerPrice;
        this.price = wholesalerPrice.getValue(); // Update price when wholesalerPrice changes
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return this.stock > 0;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

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
