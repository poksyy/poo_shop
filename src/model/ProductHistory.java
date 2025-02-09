package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "historical_inventory")
public class ProductHistory {

    @Id
    @Column(name = "idProduct")
    private int idProduct;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "wholesalerPrice")
    private double wholesalerPrice;
    
    @Column(name = "publicPrice")
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
