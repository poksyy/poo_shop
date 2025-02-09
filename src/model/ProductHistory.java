package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "historical_inventory")
public class ProductHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
    
    @Column(name = "id_product")
    private int idProduct;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "wholesaler_price")
    private double wholesalerPrice;
    
    @Column(name = "public_price")
    private double publicPrice;
    
    @Column(name = "stock")
    private int stock;

    // Getters and setters

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWholesalerPrice() {
        return wholesalerPrice;
    }

    public void setWholesalerPrice(double wholesalerPrice) {
        this.wholesalerPrice = wholesalerPrice;
    }

    public double getPublicPrice() {
        return publicPrice;
    }

    public void setPublicPrice(double publicPrice) {
        this.publicPrice = publicPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ProductHistory [idProduct=" + idProduct + ", name=" + name + ", wholesalerPrice=" + wholesalerPrice
                + ", publicPrice=" + publicPrice + ", stock=" + stock + "]";
    }
}
