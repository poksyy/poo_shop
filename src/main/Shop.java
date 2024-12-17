package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.Dao;
import dao.DaoImplFile;
import dao.DaoImplJDBC;
import dao.DaoImplJaxb;
//import dao.DaoImplFile;
import dao.DaoImplXML;
import model.Amount;
import model.Client;
import model.ClientPremium;
import model.Product;
import model.Sale;

import view.LoginView;

import util.Constants;

public class Shop {

	private static Shop shop;
	private static Scanner sc = new Scanner(System.in);

	private Amount cash;
	private DateTimeFormatter myFormatObj;
	private int saleIdCounter = 1;

	public ArrayList<Product> inventory = new ArrayList<>();
	private ArrayList<Sale> sales = new ArrayList<>();
	
	// connection using Database
	private Dao dao = new DaoImplJDBC();
	
	// connection using File
	// private Dao dao = new DaoImplFile();
	
	//connection using Xml
	//private Dao dao = new DaoImplXml();
	
	//connection using Jaxb
	//private Dao dao = new DaoImplJaxb();
	

	public Shop() {
		this.cash = new Amount(100.00);
	}

	//
	public static Shop getInstance() {
		if (shop == null) {
			shop = new Shop();
		}
		return shop;
	}

	public static void main(String[] args) {
		Shop shop = getInstance();
		shop.loadInventory();
		shop.initSession();

		int opcion = 0;
		boolean exit = false;

		do {
			System.out.println(Constants.BOLD_TEXT + "SHOP" + Constants.RESET_TEXT);
			System.out.println("1)  Count cash");
			System.out.println("2)  Add product");
			System.out.println("3)  Add stock");
			System.out.println("4)  Mark product next expiration");
			System.out.println("5)  View inventory");
			System.out.println("6)  Sale");
			System.out.println("7)  View sales");
			System.out.println("8)  Delete product");
			System.out.println("9)  View total sales");
			System.out.println("10) Exit program");
			System.out.print("Select an option: ");

			opcion = sc.nextInt();
			sc.nextLine();

			switch (opcion) {
			case 1:
				shop.showCash();
				break;

			case 2:
				shop.addProduct();
				break;

			case 3:
				shop.addStock();
				break;

			case 4:
				shop.setExpired();
				break;

			case 5:
				shop.showInventory();
				break;

			case 6:
				sc.nextLine();
				shop.sale();
				break;
			case 7:
				shop.showSales();
				break;

			case 8:
				shop.deleteProduct();
				break;

			case 9:
				shop.showSalesTotal();
				break;

			case 10:
				System.out.println("You have exited the menu.");
				exit = true;
				break;
			default:
				System.err.println("That option does not exist. Choose another option.");
			}

		} while (!exit);

	}

	/**
	 * Load initial inventory to shop
	 */
	public void loadInventory() {
		dao.connect();
		ArrayList<Product> loadedInventory = dao.getInventory();

		if (loadedInventory != null && !loadedInventory.isEmpty()) {
			setInventory(loadedInventory);
		} else {
			System.out.println("Empty inventory.");
		}
	}

	/**
	 * 0.º Option: Export inventory to a file
	 * 
	 * @return
	 */
	public boolean writeInventory() {
		return dao.writeInventory(inventory);
	}

	/**
	 * 1st Option: Show current total cash
	 * 
	 * @return
	 */
	public Amount showCash() {
		return cash;
	}

	/**
	 * 2nd Option: Add a new product to inventory getting data from console
	 */
	public void addProduct() {

		System.out.print("Name: ");
		String name = sc.next();

		Product existingProduct = findProduct(name);
		if (existingProduct != null) {
			System.err.println("The product already exists in the inventory.");
			return;
		}

		System.out.print("Wholesaler price: ");
		double wholesalerPrice = sc.nextDouble();
		System.out.print("Stock: ");
		int stock = sc.nextInt();

		addProduct(new Product(name, wholesalerPrice, true, stock));
	}

	/**
	 * 3rd Option: Add stock for a specific product
	 */
	public void addStock() {
		System.out.println("Select a product name: ");
		String name = sc.next();
		Product product = findProduct(name);

		if (product != null) {
			// ask for the quantity
			System.out.print("Enter the quantity to add: ");
			int additionalStock = sc.nextInt();

			// update the product's stock by adding the additional quantity
			int updatedStock = product.getStock() + additionalStock;
			product.setStock(updatedStock);

			System.out.println("The stock of product " + name + " has been updated to " + updatedStock);
		} else {
			System.err.println("Product with name " + name + " not found.");
		}
	}

	/**
	 * 4th Option: Set a product as expired
	 */
	private void setExpired() {
		System.out.print("Select a product name: ");
		String name = sc.next();

		Product product = findProduct(name);

		if (product != null) {
			product.expire();
			System.out.println("The stock of product " + name + " has been updated to " + product.getPublicPrice());

		}
	}

	/**
	 * 5th Option: Show all inventory
	 */
	public void showInventory() {
		System.out.println();
		System.out.println("Inventory:");
		for (Product product : inventory) {
			if (product != null) {
				System.out.println(product.toString());
			}
		}
	}

	/**
	 * 6th Option: Make a sale of products to a client
	 */
	public void sale() {
		String date = getCurrentDateTimeFormatted();
		ArrayList<Product> products = new ArrayList<>();
		int saleId = saleIdCounter++;

		// ask if client is premium or not
		System.out.println("Is the client a premium member? (Yes/No)");
		String membershipOption = sc.next();

		Client client;
		switch (membershipOption.toLowerCase()) {
		case "yes":
			client = new ClientPremium("Premium Client");
			break;
		case "no":
			client = new Client("Regular Client");
			break;
		default:
			System.err.println("Invalid option.");
			client = new Client("Regular Client");
		}

		// ask for client name
		System.out.println("Enter the client's name:");
		@SuppressWarnings("unused")
		String clientName = sc.next();

		// sale product until input name is not 0
		Amount totalAmount = new Amount(0.0);
		String name = "";
		while (!name.equals("0") && products.size() < 10) {
			System.out.println("Enter the product name, write 0 to finish:");
			name = sc.next();

			if (name.equals("0")) {
				break;
			}
			Product product = findProduct(name);

			if (product != null && product.isAvailable()) {
				totalAmount = totalAmount.add(product.getPublicPrice());
				product.setStock(product.getStock() - 1);

				// if no more stock, set as not available to sale
				if (product.getStock() == 0) {
					product.setAvailable(false);
				}
				System.out.println("Product added successfully");
				products.add(product);
			} else {
				System.err.println("Product not found or out of stock");
			}
		}

		// apply tax rate
		totalAmount = totalAmount.multiply(Constants.TAX_RATE);

		// payment process
		boolean payment = client.pay(totalAmount);

		if (payment || totalAmount.getValue() > client.getBalance()) {
			if (payment) {
				cash = cash.add(totalAmount);
				System.out.println("Sale made successfully, total: " + totalAmount);
			} else {
				double differenceValue = (-1) * (totalAmount.getValue() - client.getBalance());
				Amount difference = new Amount(differenceValue);
				System.out.println("Sale made successfully, total: " + totalAmount);
				System.out.println("The client owes: " + difference);
			}

			Sale sale = new Sale(saleId, name, products, totalAmount, date);
			sales.add(sale);
		} else {
			System.err.println("Sale canceled.");
		}

	}

	/**
	 * 7th Option: Show all sales
	 */
	private void showSales() {
		for (Sale sale : sales) {
			if (sale != null) {
				System.out.println("Sale #" + sale.getSaleId());
				System.out.println("Client: " + sale.getClientName());
				System.out.println(sale.toString());
			}
		}
		saveSalesToFile();
	}

	/**
	 * 8th Option: Eliminate product
	 */
	public void deleteProduct() {
		System.out.print("Name of the product you want to delete: ");
		sc.nextLine();
		String name = sc.nextLine();

		Product productRemove = findProduct(name);

		if (productRemove == null) {
			System.err.println("The product does not exist in the inventory.");
			return;
		}

		System.out.println("Are you sure you want to delete the product " + name + "? (Yes / No)");
		String removeOption = sc.next();
		switch (removeOption.toLowerCase()) {
		case "yes":
			inventory.remove(productRemove);
			System.out.println("Product deleted successfully.");
			break;
		case "no":
			System.out.println("No changes have been made.");
			break;
		default:
			System.err.println("That option does not exist.");
		}
	}

	/**
	 * 9th Option: Show total sales
	 */
	private void showSalesTotal() {
		Amount totalAmount = new Amount(0.0);
		for (Sale sale : sales) {
			if (sale != null) {
				totalAmount = totalAmount.add(sale.getAmount());
				System.out.println(sale.toString());
			}
		}
		System.out.println("Total sales amount: " + totalAmount);
	}

	/**
	 * Get the cash amount.
	 * 
	 * @return the cash amount
	 */
	public Amount getCash() {
		return cash;
	}

	/**
	 * Set the cash amount.
	 * 
	 * @param cash the cash amount to set
	 */
	public void setCash(Amount cash) {
		this.cash = cash;
	}

	public void setInventory(ArrayList<Product> inventory) {
		this.inventory = inventory;
	}

	/**
	 * Get the inventory list.
	 * 
	 * @return the inventory list
	 */
	public List<Product> getInventory() {
		return inventory;
	}

	/**
	 * Get the sales list.
	 * 
	 * @return the sales list
	 */
	public ArrayList<Sale> getSales() {
		return this.sales;
	}

	/**
	 * Add a product to inventory.
	 * 
	 * @param product the product to add
	 */
	public void addProduct(Product product) {
		inventory.add(product);
	}

	/**
	 * Find product by name.
	 * 
	 * @param name the name of the product
	 * @return the found product, or null if not found
	 */
	public Product findProduct(String name) {
		for (Product product : inventory) {
			if (product != null && product.getName().equalsIgnoreCase(name)) {
				return product;
			}
		}
		return null;
	}

	/**
	 * Get the current date and time formatted as a string.
	 * 
	 * @return the formatted date and time string
	 */
	public String getCurrentDateTimeFormatted() {
		myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime myDateObj = LocalDateTime.now();
		return myDateObj.format(myFormatObj);
	}

	/**
	 * Get the current date formatted as a string.
	 * 
	 * @return the formatted date string
	 */
	private String getCurrentDateFormatted() {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return myDateObj.format(myFormatObj);
	}

	/**
	 * Save sales to a file.
	 */
	private void saveSalesToFile() {
		try {
			String date = getCurrentDateFormatted();
			File file = new File("./files/sales_" + date + ".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			if (!file.exists()) {
				System.out.println("Creating the file...");
				file.createNewFile();
			}

			for (Sale sale : sales) {
				if (sale != null && !sale.isSaved()) {
					writer.append(
							sale.getSaleId() + ";Client=" + sale.getClientName() + ";Date=" + sale.getDate() + ";\n");

					ArrayList<Product> products = sale.getProducts();
					for (Product product : products) {
						writer.append(sale.getSaleId() + ";Products=" + product.getName() + ","
								+ product.getPublicPrice() + ";\n");
					}

					writer.append(sale.getSaleId() + ";Amount=" + sale.getAmount() + ";\n");

					sale.setSaved(true);
				}
			}

			writer.close();
			System.out.println("Sale saved in the file successfully.");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error writing to the file.");
		}
	}

	/**
	 * Log in.
	 */
	private void initSession() {
		LoginView loginWindow = new LoginView();

		loginWindow.setVisible(true);

		while (!loginWindow.isLoggedIn()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Login successful. Welcome");
	}
}