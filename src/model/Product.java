package model;

public class Product {
	private int id;
	private String name;
	private double publicPrice;
	private double wholesalerPrice;
	private boolean available;
	private int stock;
	private static int totalProducts;

	static double EXPIRATION_RATE = 0.60;

	public Product(String name, double wholesalerPrice, boolean available, int stock) {
		super();
		this.id = totalProducts + 1;
		this.name = name;
		this.wholesalerPrice = wholesalerPrice;
		this.publicPrice = wholesalerPrice * 2;
		this.available = available;
		this.stock = stock;
		totalProducts++;
	}

	@Override
	public String toString() {
		return "\nProduct:" + "\nName\t\t\t" + name + "\nPublicPrice\t\t" + publicPrice + "\nWholesalerPrice\t\t"
				+ wholesalerPrice + "\nStock\t\t\t" + stock + "\n";
	}

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

	public double getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(double publicPrice) {
		this.publicPrice = publicPrice;
	}

	public double getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(double wholesalerPrice) {
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
		this.publicPrice = this.getPublicPrice() * EXPIRATION_RATE;
	}
}
