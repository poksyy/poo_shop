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
import java.util.Comparator;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import main.Shop;
import model.Employee;
import model.Product;

import view.ShopView;

public class DaoImplXML implements Dao {
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
	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
	    //TODO DOM Writer
		return false;
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

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}


}
