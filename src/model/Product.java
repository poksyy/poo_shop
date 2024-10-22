package model;

public class Product {
    private int id;
    private String name;
    private Amount publicPrice;
    private Amount wholesalerPrice;
    private boolean available;
    private int stock;
    private static int totalProducts;
    
    public static double EXPIRATION_RATE = 0.60;

    // Constructor
    public Product(String name, double wholesalerPrice, boolean available, int stock) {
        super();
        this.id = totalProducts + 1;
        this.name = name;
        this.wholesalerPrice = new Amount(wholesalerPrice);
        this.publicPrice = new Amount(wholesalerPrice * 2);
        this.available = available;
        this.stock = stock;
        totalProducts++;
    }
    
    public Product(String name) {
    	this.name = name;
    }

    // Override toString method
    @Override
    public String toString() {
        return "\nProduct:" +
               "\nName\t\t\t" + name +
               "\nPublicPrice\t\t" + publicPrice +
               "\nWholesalerPrice\t\t" + wholesalerPrice +
               "\nStock\t\t\t" + stock + "\n";
    }

    // Getters and Setters
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
    }

    public boolean isAvailable() {
        return available;
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

    public static int getTotalProducts() {
        return totalProducts;
    }

    public static void setTotalProducts(int totalProducts) {
        Product.totalProducts = totalProducts;
    }

    public void expire() {
        double increasedPrice = this.publicPrice.getValue() * EXPIRATION_RATE;
        this.publicPrice.setValue(increasedPrice);
    }

	public void setCurrency(String value) {
		// TODO Auto-generated method stub
		
	}
}
