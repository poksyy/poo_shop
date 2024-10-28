package dao.xml;

import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import model.Amount;
import model.Product;

public class SaxReader extends DefaultHandler {
    ArrayList<Product> products;
    Product product;
    String value;
    String parsedElement;

    // Method to get the list of products
    public ArrayList<Product> getProducts() {
        return products;
    }

    @Override
    public void startDocument() throws SAXException {
        this.products = new ArrayList<>();  // Initialize the list of products
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
        case "product":
            this.product = new Product(attributes.getValue("name") != null ? attributes.getValue("name") : "empty");
            break;
        case "wholesalerPrice":
            this.product.setCurrency(attributes.getValue("currency"));
            break;
        }
        this.parsedElement = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        value = new String(ch, start, length);
        switch (parsedElement) {
        case "wholesalerPrice":
            this.product.setWholesalerPrice(new Amount(Float.valueOf(value)));
            this.product.setPublicPrice(new Amount(Float.valueOf(value) * 2));
            break;
        case "stock":
            this.product.setStock(Integer.valueOf(value));
            break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("product")) {
            this.products.add(product);
        }
        this.parsedElement = "";
    }

    @Override
    public void endDocument() throws SAXException {
    }
}
