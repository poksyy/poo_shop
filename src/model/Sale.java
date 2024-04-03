package model;

import java.util.ArrayList;

public class Sale {
    private static int saleIdCounter = 1;
    private int saleId;
	private String client;
	private ArrayList<Product> products;
	private Amount amount;
	private String date;
	private boolean saved;

    public Sale(int saleId, String client, ArrayList<Product> products, Amount totalAmount, String date) {
        this.saleId = saleId;
        this.client = client;
        this.products = products;
        this.amount = new Amount(totalAmount);
        this.date = date;
        this.saved = false;
    }

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
    
    public int getSaleId() {
        return saleId;
    }
    
	@Override
	public String toString() {
	    StringBuilder stringBuilder = new StringBuilder();
	    stringBuilder.append("\nSale:")
	      .append("\nClient\t\t\t").append(client)
	      .append("\nProducts\t\t");

	    for (Product product : products) {
	    	stringBuilder.append("\n").append(product.toString());
	    }

	    stringBuilder.append("\nAmount\t\t\t").append(amount)
	      .append("\nDate\t\t\t").append(date);

	    return stringBuilder.toString();
	}

	

}
