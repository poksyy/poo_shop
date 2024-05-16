package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Amount;
import model.Client;
import model.ClientPremium;
import model.Product;
import model.Sale;
import view.LoginView;


public class Shop {
    private static final String BOLD_TEXT = "\u001B[1m";
    private static final String RESET_TEXT = "\u001B[0m";
    final static double TAX_RATE = 1.04;
    private static Scanner sc = new Scanner(System.in);

    private Amount cash;
    private DateTimeFormatter myFormatObj;
    private int saleIdCounter = 1;

    public ArrayList<Product> inventory = new ArrayList<>();
    ArrayList<Sale> sales = new ArrayList<>();

    public Shop() {
        cash = new Amount(100.00);
    }
    
	public static void main(String[] args) {
		Shop shop = new Shop();
		shop.loadInventory();
		shop.initSession();
		
		int opcion = 0;
		boolean exit = false;

		do {
			System.out.println("\n");
			System.out.println(BOLD_TEXT + "===========================" + RESET_TEXT);
			System.out.println(BOLD_TEXT + "Main menu myStore.com" + RESET_TEXT);
			System.out.println(BOLD_TEXT + "===========================" + RESET_TEXT);
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
	 * load initial inventory to shop
	 */
	public void loadInventory() {
		try {
			File file = new File("./files/inputInventory.txt");

			if (!file.exists()) {
				System.out.println("The file does not exist.");
				System.out.println("Creating file...");
				file.createNewFile();
			}

			if (file.canRead()) {
				BufferedReader reader = new BufferedReader(new FileReader(file));

				String line = reader.readLine();

				while (line != null) {
					try {
						String[] parts = line.split(";");
						String product = parts[0].split(":")[1].trim();
						double price = Double.parseDouble(parts[1].split(":")[1].trim());
						int stock = Integer.parseInt(parts[2].split(":")[1].trim());

						addProduct(new Product(product, price, true, stock));
					} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
						System.err.println("Error detected in line:" + line);
					}

					line = reader.readLine();
				}

				reader.close();
			} else {
				System.err.println("Cannot access file for reading.");
			}
		} catch (IOException | SecurityException e) {
			System.err.println("There was a problem with the file:" + e.getMessage());
		}
	}

	/**
	 * 1st Option: Show current total cash
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
	    System.out.print("Select a product name: ");
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
	    totalAmount = totalAmount.multiply(TAX_RATE);

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
	 * add a product to inventory
	 * 
	 * @param product
	 */
	public void addProduct(Product product) {
		inventory.add(product);
	}

	/**
	 * find product by name
	 * 
	 * @param product name
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
	 * formatted date & time 
	 * @return
	 */
	private String getCurrentDateTimeFormatted() {
		myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		LocalDateTime myDateObj = LocalDateTime.now();
		return myDateObj.format(myFormatObj);
	}
	
	/**
	 * formatted date
	 * @return
	 */
	private String getCurrentDateFormatted() {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return myDateObj.format(myFormatObj);
	}
	
	/**
	 * save sales to file
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
	                writer.append(sale.getSaleId() + ";Client=" + sale.getClientName() + ";Date=" + sale.getDate() + ";\n");

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
	 * log in
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

	public List<Product> getInventory() {
		// TODO Auto-generated method stub
		return inventory;
	}
	

}