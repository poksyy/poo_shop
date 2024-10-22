package dao.xml;

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
import java.util.Comparator;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import dao.Dao;
import main.Shop;
import model.Employee;
import model.Product;

import view.ShopView;

public class DaoImplXML implements Dao {

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
        ArrayList<Product> inventory = new ArrayList<>();
        
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser;
        try {
            parser = factory.newSAXParser();
            File file = new File("XML/inputInventory.xml");

            dao.xml.SaxReader saxReader = new dao.xml.SaxReader();
            parser.parse(file, saxReader);
            
            inventory = saxReader.getProducts();

        } catch (ParserConfigurationException | SAXException e) {
            System.out.println("ERROR creating the parser: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("ERROR file not found or cannot be read: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }

        return inventory;
    }

    /**
     * Exports the current inventory to a file
     */
    public boolean writeInventory(ArrayList<Product> inventory) {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDateTime = now.format(formatter);
            
            String fileFolder = "files"; 
            File folder = new File(fileFolder);
            
            if (!folder.exists()) {
            	ShopView shopView = new ShopView();
                File newFolder = shopView.nameFolder();
                if (newFolder == null) {
                    return false;
                }
                folder = newFolder;
            }

            File file = new File(folder, "inventory_" + formattedDateTime + ".txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Product product : inventory) {
                    writer.write(product.getId() + "; Product: " + product.getName() + "; Price: " + product.getPublicPrice() + "; Stock: " + product.getStock() + ";\n");
                }
            }

            return true;

        } catch (IOException e) {
            System.err.println("Error writing inventory to file: " + e.getMessage());
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
