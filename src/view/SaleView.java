package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import main.Shop;
import model.Amount;
import model.Client;
import model.ClientPremium;
import model.Product;
import model.Sale;
import util.Constants;

public class SaleView extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField clientNameField;
    private JTextField productNameField;
    private JTextField numProductField;
    private JRadioButton premiumYes;
    private JRadioButton premiumNo;
    private JButton btnAccept;
    private JButton btnCancel;
    private Shop shop;
    
    /**
     * Constructor for SaleView that initializes the sale dialog.
     * 
     * @param shop     the shop instance that holds product and sale information
     * @param shopView reference to the shop view to update after a sale
     */
    public SaleView(Shop shop, ShopView shopView) {
        this.shop = shop;
        saleUI(); // Initializes the sale user interface
    }

    /**
     * Sets up the user interface for the sale dialog.
     */
    private void saleUI() {
        setTitle("Sale Menu");
        setSize(410, 255);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(null);

        // Labels and radio buttons for premium membership
        JLabel lblPremium = new JLabel("Is the client a premium member?");
        lblPremium.setBounds(20, 20, 200, 25);
        premiumYes = new JRadioButton("Yes");
        premiumYes.setBackground(new Color(248, 249, 250));
        premiumYes.setBounds(230, 20, 60, 25);
        premiumNo = new JRadioButton("No");
        premiumNo.setBackground(new Color(248, 249, 250));
        premiumNo.setBounds(300, 20, 60, 25);
        ButtonGroup premiumGroup = new ButtonGroup();
        premiumGroup.add(premiumYes);
        premiumGroup.add(premiumNo);

        // Labels and text fields for client and product information
        JLabel lblClientName = new JLabel("Client Name:");
        lblClientName.setBounds(20, 60, 100, 25);
        clientNameField = new JTextField();
        clientNameField.setBounds(130, 60, 209, 25);

        JLabel lblProductName = new JLabel("Product Name:");
        lblProductName.setBounds(20, 100, 100, 25);
        productNameField = new JTextField();
        productNameField.setBounds(130, 100, 209, 25);
        
        JLabel lblNumProduct = new JLabel("Product Quantity:");
        lblNumProduct.setBounds(20, 140, 100, 25);
        numProductField = new JTextField();
        numProductField.setBounds(130, 140, 209, 25);

        // Accept and Cancel buttons
        btnAccept = new JButton("OK");
        btnAccept.setBounds(130, 180, 100, 30);
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(240, 180, 100, 30);

        // Setting button fonts
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        setButtonFonts(new JButton[] { btnAccept, btnCancel }, buttonFont);

        // Setting button backgrounds
        Color buttonBackgroundColor = new Color(233, 236, 239);
        setButtonBackgrounds(new JButton[] { btnAccept, btnCancel }, buttonBackgroundColor);

        // Setting panel background color
        panel.setBackground(new Color(248, 249, 250));

        // Adding components to the panel
        panel.add(lblPremium);
        panel.add(premiumYes);
        panel.add(premiumNo);
        panel.add(lblClientName);
        panel.add(clientNameField);
        panel.add(lblProductName);
        panel.add(productNameField);
        panel.add(lblNumProduct);
        panel.add(numProductField);
        panel.add(btnAccept);
        panel.add(btnCancel);

        // Adding ActionListeners for buttons
        btnAccept.addActionListener(this);
        btnCancel.addActionListener(this);

        // Adding the panel to the dialog
        getContentPane().add(panel);

        // Making the dialog visible
        setVisible(true);
    }

    /**
     * Sets the font for a group of buttons.
     *
     * @param buttons the array of JButtons to set the font for
     * @param font    the font to set
     */
    private void setButtonFonts(JButton[] buttons, Font font) {
        for (JButton button : buttons) {
            button.setFont(font);
        }
    }

    /**
     * Sets the background color for a group of buttons.
     *
     * @param buttons the array of JButtons to set the background color for
     * @param color   the color to set as the background
     */
    private void setButtonBackgrounds(JButton[] buttons, Color color) {
        for (JButton button : buttons) {
            button.setBackground(color);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle the action for buttons
        if (e.getSource() == btnAccept) {
            boolean isPremium = premiumYes.isSelected();
            String clientName = clientNameField.getText();
            String productName = productNameField.getText();
            String totalProduct = numProductField.getText();

            // Error message when leaving empty inputs
            if (clientName.isEmpty() || productName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both client and product names.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Convert product quantity string to integer
            int totalProductNum;
            try {
                totalProductNum = Integer.parseInt(totalProduct);
            } catch (NumberFormatException r) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for product quantity", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the product exists
            Product product = shop.findProduct(productName);
            if (product == null || !product.isAvailable()) {
                JOptionPane.showMessageDialog(this, "Product not found or out of stock.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a new Amount object with the public price decreased by the tax rate
            //Amount existingPublicPrice = product.getPublicPrice();
            //Amount clientTotal = (existingPublicPrice.multiply(Constants.TAX_RATE)).multiply(totalProductNum);
            Amount existingPublicPrice = product.getPublicPrice();
            Amount clientTotal = (existingPublicPrice.multiply(Constants.TAX_RATE)).multiply(totalProductNum);
            
            // Perform the sale
            ArrayList<Product> products = new ArrayList<>();
            products.add(product);
            
            // Temporarily create a non-premium client to check payment
            Client tempClient = new Client(clientName);
            boolean paymentSuccessful = tempClient.pay(clientTotal);
            if (paymentSuccessful) {
                // Create the client based on whether they are a premium member or not
                Client client = isPremium ? new ClientPremium(clientName) : new Client(clientName);
                
                // Re-perform the payment with the actual client object
                client.pay(clientTotal);
                
                // Update the stock of the existing product in the shop
                int newStock = product.getStock() - totalProductNum;
                product.setStock(newStock);

                addCashValue(clientTotal);

                // Create a new Sale object and add it to the shop's sales
                Sale sale = new Sale(shop.getSales().size() + 1, clientName, products, clientTotal,
                        shop.getCurrentDateTimeFormatted());
                shop.getSales().add(sale);

                JOptionPane.showMessageDialog(this, "Sale successful!\nTotal Amount: " + clientTotal, "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Payment failed.", "Error", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } else if (e.getSource() == btnCancel) {
            dispose();
        }
    }

    /**
     * Adds the cash value from a sale to the shop's total cash value.
     *
     * @param newPublicPrice the amount to be added to the cash value
     */
    public void addCashValue(Amount newPublicPrice) {
        // Store the current cashValue in a variable
        Amount cashValue = shop.getCash();
        Amount updatedCashValue = cashValue.add(newPublicPrice);
        // Create a new instance of Amount so we can work with Amount objects
        shop.setCash(updatedCashValue);
    }
}
