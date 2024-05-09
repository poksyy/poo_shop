package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Shop;
import model.Amount;
import model.Product;

public class ProductView extends JDialog implements ActionListener {
    private JTextField prodNameField;
    private JTextField stockNumField;
    private JTextField priceNumField;
    private JButton acceptButton;
    private JButton cancelButton;
    private int option;
	private Shop shop;


    public ProductView(int option, Shop shop) {
    	this.option = option;
    	this.shop = shop;
    	productMenu();
    }



	private void productMenu() {
        setTitle("Product menu");
        setSize(310, 172);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // text fields and login button
        prodNameField = new JTextField(10);
        stockNumField = new JTextField(10);
        priceNumField = new JTextField(10);
        stockNumField.setPreferredSize(new Dimension(100, 20));
        priceNumField.setPreferredSize(new Dimension(100, 20));

        acceptButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        // panel with border layout
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(null);

        // labels and input fields
        JLabel nameLabel = new JLabel("Product name:");
        nameLabel.setBounds(10, 10, 100, 20);
        prodNameField.setBounds(120, 10, 166, 20);

        JLabel stockLabel = new JLabel("Product stock:");
        stockLabel.setBounds(10, 40, 100, 20);
        stockNumField.setBounds(120, 41, 166, 20);
        
        JLabel priceLabel = new JLabel("Product price:");
        priceLabel.setBounds(10, 70, 100, 20);
        priceNumField.setBounds(120, 71, 166, 20);

        acceptButton.setBounds(120, 100, 83, 30);
        cancelButton.setBounds(213, 100, 73, 30);

        // adding components to the panel
        panel.add(nameLabel);
        panel.add(prodNameField);
        panel.add(stockLabel);
        panel.add(stockNumField);
        panel.add(priceLabel);
        panel.add(priceNumField);
        panel.add(acceptButton);
        panel.add(cancelButton);

        // adding ActionListener to accept or cancel
        acceptButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // adding the panel to the frame
        getContentPane().add(panel);
        
        // product view control
		if (option == 3) {
        	priceLabel.setVisible(false);
        	priceNumField.setVisible(false);
        } else if(option == 9) {
        	priceLabel.setVisible(false);
        	priceNumField.setVisible(false);
        	stockLabel.setVisible(false);
        	stockNumField.setVisible(false);
        }
	}

	private void addProduct() {
	    String name = prodNameField.getText();
	    int stock = Integer.parseInt(stockNumField.getText());
	    double price = Double.parseDouble(priceNumField.getText());
	    
	    // create a product object with the fields data
	    Product product = new Product(name, price, true, stock);
	    
	    // send the product object to the addProduct method in Shop as a parameter
	    shop.addProduct(product);
	}

	private void addStock() {
		
	}
	
	private void deleteProduct() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == cancelButton) {
	        dispose();
	    } else if (e.getSource() == acceptButton) {
	        switch (option) {
	            case 2:
	                addProduct();
	                break;
	            case 3:
	                addStock();
	                break;
	            case 9:
	            	deleteProduct();
	            	break;
	        }
	        dispose();
	    }
	}

}