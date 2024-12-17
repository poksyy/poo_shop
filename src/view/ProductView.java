package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Shop;
import model.Product;
import util.Constants;

public class ProductView extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField prodNameField;
    private JTextField stockNumField;
    private JTextField priceNumField;
    private JLabel nameLabel;
    private JLabel stockLabel;
    private JLabel priceLabel;
    private JButton btnAccept;
    private JButton btnCancel;
    private int option;
    private Shop shop;

    // Constructor initializes the ProductView with an option and shop reference
    public ProductView(int option, Shop shop) {
        this.option = option;
        this.shop = shop;
        initializeUI();
    }

    /**
     * Sets up the product menu UI, including buttons for various actions and their
     * event listeners.
     */
    private void initializeUI() {
        
    	UIManager.put("OptionPane.yesButtonText", "Yes");
        UIManager.put("OptionPane.noButtonText", "No");
        UIManager.put("OptionPane.okButtonText", "OK");
        
        setTitle("Product Menu");
        setSize(310, 172);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Text fields for product information
        prodNameField = new JTextField(10);
        stockNumField = new JTextField(10);
        priceNumField = new JTextField(10);
        stockNumField.setPreferredSize(new Dimension(100, 20));
        priceNumField.setPreferredSize(new Dimension(100, 20));

        // Buttons for actions
        btnAccept = new JButton("OK");
        btnCancel = new JButton("Cancel");

        // Panel settings
        JPanel panel = new JPanel();
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(null);

        // Labels and input fields
        setupLabelsAndFields(panel);

        // Setting up buttons and their actions
        setupButtons(panel);

        // Adding components to the panel
        getContentPane().add(panel);

        // Configure the view based on the selected option
        configureViewForOption();
    }

    /**
     * Sets up the labels and input fields for the product information in the panel.
     *
     * @param panel the panel to which labels and fields will be added
     */
    private void setupLabelsAndFields(JPanel panel) {
        nameLabel = new JLabel("Product name:");
        nameLabel.setBounds(10, 10, 100, 20);
        prodNameField.setBounds(120, 10, 166, 20);

        stockLabel = new JLabel("Product stock:");
        stockLabel.setBounds(10, 40, 100, 20);
        stockNumField.setBounds(120, 41, 166, 20);

        priceLabel = new JLabel("Product price:");
        priceLabel.setBounds(10, 70, 100, 20);
        priceNumField.setBounds(120, 71, 166, 20);

        panel.add(nameLabel);
        panel.add(prodNameField);
        panel.add(stockLabel);
        panel.add(stockNumField);
        panel.add(priceLabel);
        panel.add(priceNumField);
    }

    /**
     * Sets up the buttons and their actions in the panel.
     *
     * @param panel the panel to which buttons will be added
     */
    private void setupButtons(JPanel panel) {
        btnAccept.setBounds(120, 100, 83, 30);
        btnCancel.setBounds(213, 100, 73, 30);

        // Setting button fonts and backgrounds
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        setButtonFonts(new JButton[]{btnAccept, btnCancel}, buttonFont);
        Color buttonBackgroundColor = new Color(233, 236, 239);
        setButtonBackgrounds(new JButton[]{btnAccept, btnCancel}, buttonBackgroundColor);

        // Adding buttons to the panel
        panel.add(btnAccept);
        panel.add(btnCancel);

        // Adding ActionListeners to the buttons
        btnAccept.addActionListener(this);
        btnCancel.addActionListener(this);
    }

    /**
     * Configures the view based on the selected option (add, update, or delete product).
     */
    private void configureViewForOption() {
        if (option == Constants.ADD_PRODUCT_STOCK) {
            priceNumField.setVisible(false);
            priceLabel.setVisible(false);
        } else if (option == Constants.DELETE_PRODUCT) {
            stockNumField.setVisible(false);
            stockLabel.setVisible(false);
            priceNumField.setVisible(false);
            priceLabel.setVisible(false);
        }
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

    /**
     * Handles button click events for the dialog.
     *
     * @param e the ActionEvent triggered by button clicks
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancel) {
            dispose();
        } else if (e.getSource() == btnAccept) {
            handleAcceptAction();
        }
    }

    /**
     * Handles the action when the Accept button is clicked based on the option selected.
     */
    private void handleAcceptAction() {
        switch (option) {
            case Constants.ADD_PRODUCT:
                addProduct();
                break;
            case Constants.ADD_PRODUCT_STOCK:
                addStock();
                break;
            case Constants.DELETE_PRODUCT:
                deleteProduct();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Invalid option selected.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a new product to the shop inventory.
     */
    private void addProduct() {
        try {
            String name = prodNameField.getText();
            int stock = Integer.parseInt(stockNumField.getText());
            double price = Double.parseDouble(priceNumField.getText());

            if (shop.findProduct(name) != null) {
                JOptionPane.showMessageDialog(this, "The product already exists in the inventory.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Product product = new Product(name, price, true, stock);
            shop.addProduct(product);
            JOptionPane.showMessageDialog(this, "Product added successfully.", "Information",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid input format for stock or price.");
        }
    }

    /**
     * Adds stock to an existing product in the shop inventory.
     */
    private void addStock() {
        try {
            String name = prodNameField.getText();
            int additionalStock = Integer.parseInt(stockNumField.getText());

            Product product = shop.findProduct(name);
            if (product != null) {
                product.setStock(product.getStock() + additionalStock);
                
                shop.updateProduct(product);
                
                JOptionPane.showMessageDialog(this, "Stock added successfully.", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                showErrorDialog("The product does not exist in the inventory.");
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid input format for stock.");
        }
    }

    /**
     * Deletes a product from the shop inventory.
     */
    private void deleteProduct() {
        String name = prodNameField.getText();
        Product product = shop.findProduct(name);

        if (product != null) {
            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete the product " + name + "?", "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {

            	shop.deleteProduct(product);

                shop.inventory.remove(product);
                JOptionPane.showMessageDialog(this, "Product removed successfully.", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        } else {
            showErrorDialog("The product does not exist in the inventory.");
        }
    }

    /**
     * Displays an error dialog with the specified message.
     *
     * @param message the error message to display
     */
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
