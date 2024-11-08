package dao.xml;

import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import model.Amount;
import model.Product;

public class SaxReader extends DefaultHandler {
	
    // Attributes
    ArrayList<Product> products;
    Product product;
    String value;
    String parsedElement;

    /**
     * Retrieves the list of parsed products.
     * @return ArrayList of Product objects.
     */
    public ArrayList<Product> getProducts() {
        return products;
    }

    /**
     * Initializes the products list before parsing begins.
     */
    @Override
    public void startDocument() throws SAXException {
        this.products = new ArrayList<>();  // Initialize the list of products
    }

    
    /**
     * Processes the beginning of an XML element.
     * Initializes product objects and assigns attributes where applicable.
     * @param uri Namespace URI
     * @param localName Local name (no prefix)
     * @param qName Qualified name (with prefix, if any)
     * @param attributes Attributes attached to the element
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
	        case "product":
                // Initialize new product with a name or a default name if missing
	            this.product = new Product(attributes.getValue("name") != null ? attributes.getValue("name") : "empty");
	            break;
	        case "wholesalerPrice":
                // Sets the currency attribute for the wholesaler price if available
	            this.product.setCurrency(attributes.getValue("currency"));
	            break;
        }
        this.parsedElement = qName;
    }

    /**
     * Handles character data between XML tags.
     * Processes and assigns values based on the currently parsed element.
     * @param ch Array of characters
     * @param start Start position in array
     * @param length Number of characters to read
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        value = new String(ch, start, length);
        switch (parsedElement) {
	        case "wholesalerPrice":
	            // Set both wholesaler and public prices
	            this.product.setWholesalerPrice(new Amount(Float.valueOf(value)));
	            // Public price is double
	            this.product.setPublicPrice(new Amount(Float.valueOf(value) * 2));
	            break;
	        case "stock":
                // Set the stock quantity
	            this.product.setStock(Integer.valueOf(value));
	            break;
        }
    }

    /**
     * Processes the end of an XML element.
     * Adds the product to the products list when the end of a <product> element is reached.
     * @param uri Namespace URI
     * @param localName Local name (no prefix)
     * @param qName Qualified name (with prefix, if any)
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("product")) {
            this.products.add(product);
        }
        this.parsedElement = "";
    }

    /**
     * Completes any final steps after parsing ends (optional here).
     */
    @Override
    public void endDocument() throws SAXException {
    }
}
