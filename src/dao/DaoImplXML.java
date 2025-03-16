package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import dao.xml.DomWriter;
import model.Employee;
import model.Product;
import model.Sale;

public class DaoImplXML implements Dao {

	@Override
	public Employee getEmployee(int id, String password) {
		return null;
	}

	/**
	 * Retrieves the product inventory from an XML file. Uses SAX parser to read XML
	 * and populate a list of Product objects.
	 * 
	 * @return ArrayList of Product objects representing the inventory.
	 */
	@Override
	public ArrayList<Product> getInventory() {
		ArrayList<Product> inventory = new ArrayList<>();

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			File file = new File("XML/inputInventory.xml");

			// SAX reader for parsing XML
			dao.xml.SaxReader saxReader = new dao.xml.SaxReader();
			parser.parse(file, saxReader);
			// Get the parsed products
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
	 * Exports the provided inventory to an XML file. Method is currently
	 * unimplemented.
	 * 
	 * @param inventory ArrayList of Product objects to be written to XML.
	 * @return boolean indicating if the write was successful.
	 */
	@Override
	public boolean writeInventory(ArrayList<Product> inventory) {
		DomWriter domWriter = new DomWriter();
		domWriter.generateDocument(inventory);
		return true;
	}

	@Override
	public Product getProduct(int id) {
		return null;
	}

	@Override
	public void connect() {
	}

	@Override
	public void disconnect() {
	}

	@Override
	public void addProduct(Product product) {
	}

	@Override
	public void updateProduct(Product product) {
	}

	@Override
	public void deleteProduct(Product product) {
	}

	@Override
	public void addSale(Sale sale) {
	}

}
