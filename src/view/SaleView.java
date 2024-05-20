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

public class SaleView extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField clientNameField;
    private JTextField productNameField;
    private JRadioButton premiumYes;
    private JRadioButton premiumNo;
    private JButton btnAccept;
    private JButton btnCancel;
    private Shop shop;
    private ShopView shopView;

    public SaleView(Shop shop,  ShopView shopView) {
        this.shop = shop;
        this.shopView = shopView;
        saleUI();
    }

    private void saleUI() {
        setTitle("Sale Menu");
        setSize(370, 215);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(null);

        // text fields and buttons
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

        JLabel lblClientName = new JLabel("Client Name:");
        lblClientName.setBounds(20, 60, 100, 25);
        clientNameField = new JTextField();
        clientNameField.setBounds(130, 60, 209, 25);

        JLabel lblProductName = new JLabel("Product Name:");
        lblProductName.setBounds(20, 100, 100, 25);
        productNameField = new JTextField();
        productNameField.setBounds(130, 100, 209, 25);

        // Accept and Cancel buttons
        btnAccept = new JButton("OK");
        btnAccept.setBounds(130, 135, 100, 30);
        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(240, 135, 100, 30);

        // setting button fonts
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        setButtonFonts(new JButton[] { btnAccept, btnCancel }, buttonFont);

        // setting button backgrounds
        Color buttonBackgroundColor = new Color(233, 236, 239);
        setButtonBackgrounds(new JButton[] { btnAccept, btnCancel }, buttonBackgroundColor);

        // setting frame background
        panel.setBackground(new Color(248, 249, 250));

        // adding components to the panel
        panel.add(lblPremium);
        panel.add(premiumYes);
        panel.add(premiumNo);
        panel.add(lblClientName);
        panel.add(clientNameField);
        panel.add(lblProductName);
        panel.add(productNameField);
        panel.add(btnAccept);
        panel.add(btnCancel);

        // adding ActionListener to accept or cancel
        btnAccept.addActionListener(this);
        btnCancel.addActionListener(this);

        // adding the panel to the frame
        getContentPane().add(panel);

        // setting the frame visible
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
        if (e.getSource() == btnAccept) {
            boolean isPremium = premiumYes.isSelected();
            String clientName = clientNameField.getText();
            String productName = productNameField.getText();

            // error message when leaving empty inputs
            if (clientName.isEmpty() || productName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both client and product names.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // create the client based on whether true is premium or not premium
            Client client = isPremium ? new ClientPremium(clientName) : new Client(clientName);

            // check if the product exists
            Product product = shop.findProduct(productName);
            if (product == null || !product.isAvailable()) {
                JOptionPane.showMessageDialog(this, "Product not found or out of stock.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // create a new Amount object with the public price decreased by the tax rate
            Amount existingPublicPrice = product.getPublicPrice();
            Amount clientTotal = existingPublicPrice.multiply(Shop.TAX_RATE);

            // perform the sale
            ArrayList<Product> products = new ArrayList<>();
            products.add(product);
            boolean paymentSuccessful = client.pay(clientTotal);
            if (paymentSuccessful) {
                // update the stock of the existing product in the shop
                int newStock = product.getStock() - 1;
                product.setStock(newStock);
                
				addCashValue(clientTotal);
                
                Sale sale = new Sale(shop.getSales().size() + 1, clientName, products, clientTotal, shop.getCurrentDateTimeFormatted());
                shop.getSales().add(sale);

                JOptionPane.showMessageDialog(this, "Sale successful!\nTotal Amount: " + clientTotal, "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Payment failed.", "Error", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } else if (e.getSource() == btnCancel) {
            dispose();
        }
    }

	public void addCashValue(Amount newPublicPrice) {
		
		// storage the current cashValue on a variable.
		Amount cashValue = shop.getCash();
		Amount updatedCashValue = cashValue.add(newPublicPrice);
		// create a new instance of Amount so we can work with Amount objects.
		shop.setCash(updatedCashValue);
	}



}
