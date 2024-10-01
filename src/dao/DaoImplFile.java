package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import main.Shop;
import model.Employee;
import model.Product;

public class DaoImplFile implements Dao{

	public DaoImplFile() {
	}

	@Override
	public void connect() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Product> getInventory() {
	    ArrayList<Product> inventory = new ArrayList<>();

	    try {
	        File file = new File("./files/inputInventory.txt");

	        if (!file.exists()) {
	            System.err.println("The file does not exist.");
				System.out.println("Creating file...");
				file.createNewFile();
	        }

	        BufferedReader reader = new BufferedReader(new FileReader(file));
	        String line = reader.readLine();

	        while (line != null) {
	            try {
	                String[] parts = line.split(";");
	                String name = parts[0].split(":")[1].trim();
	                double price = Double.parseDouble(parts[1].split(":")[1].trim());
	                int stock = Integer.parseInt(parts[2].split(":")[1].trim());

	                Product product = new Product(name, price, true, stock);
	                inventory.add(product);
	            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					System.err.println("Error detected in line:" + line);
	            }
	            
	            line = reader.readLine();
	        }

	        reader.close();
	    } catch (IOException e) {
			System.err.println("There was a problem with the file:" + e.getMessage());
	    }

	    return inventory;
	}
	public boolean writeInventory(ArrayList<Product> inventory) {
	    try {
	        LocalDateTime now = LocalDateTime.now();
	        
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
	        String formattedDateTime = now.format(formatter);
	        
	        File file = new File("./files/outputInventory_" + formattedDateTime + ".txt");
	        
	        if (!file.exists()) {
	            file.createNewFile();
	        }

	        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

	        for (Product product : inventory) {
	            writer.write("Product: " + product.getName() + "; Price: " + product.getPublicPrice() + "; Stock: " + product.getStock() + ";\n");
	        }

	        writer.close();
	        return true;

	    } catch (IOException e) {
	        System.err.println("Error writing the inventory file: " + e.getMessage());
	        return false; 
	    }
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

	@Override
	public void disconnect() throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
