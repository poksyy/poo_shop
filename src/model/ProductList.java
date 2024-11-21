package model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

@XmlRootElement(name = "products")
public class ProductList {

    // List to hold all products
    private ArrayList<Product> products = new ArrayList<>();

    // Constructor to initialize JAXB
    public ProductList() {}

    // Constructor to initialize with a list of products
    public ProductList(ArrayList<Product> products) {
        this.products = products;
    }

    // Setter method for the products list
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    // Getter method to retrieve the list of products, annotated for XML unmarshalling/marshalling
    @XmlElement(name = "product")
    public ArrayList<Product> getProducts() {
        return products;
    }

    // Getter for total products as an XML attribute
    @XmlAttribute(name = "total")
    public int getTotalProducts() {
        return Product.getTotalProducts();
    }
}
