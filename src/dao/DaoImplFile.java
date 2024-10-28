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

import model.Employee;
import model.Product;

public class DaoImplFile implements Dao {
    private ArrayList<Product> inventory = new ArrayList<>();

    public DaoImplFile() {
    }

    @Override
    public void connect() throws SQLException {
        // No connection needed for file-based DAO
    }

    @Override
    public void disconnect() throws SQLException {
        // No disconnection needed for file-based DAO
    }

    // Inventory management
    @Override
    public ArrayList<Product> getInventory() {
        try {
            File file = new File("./files/inputInventory.txt");

            if (!file.exists()) {
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
                    System.err.println("Error detected in line: " + line);
                }

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            System.err.println("There was a problem with the file: " + e.getMessage());
        }

        return inventory;
    }

    /**
     * Exports the current inventory to a file
     */
    public boolean writeInventory(ArrayList<Product> inventory) {
        int totalProducts = 0;

        try {
            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDateTime = now.format(formatter);

            File file = new File("./files/inventory_" + formattedDateTime + ".txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (Product product : inventory) {
                writer.write(product.getId() + ";Product: " + product.getName() + "; Stock: " + product.getStock() + ";\n");
				totalProducts++;
            }
            
            writer.write("Total number of products:" + totalProducts);
            
            writer.close();
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    // Employee management
    @Override
    public Employee getEmployee(int id, String password) {
        // TODO Auto-generated method stub
        return null;
    }

    // Product management
    @Override
    public Product getProduct(int id) {
        // TODO Auto-generated method stub
        return null;
    }
}
