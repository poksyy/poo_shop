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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField prodNameField;
	private JTextField stockNumField;
	private JTextField priceNumField;
	private JButton btnAccept;
	private JButton btnCancel;
	private int option;
	private Shop shop;

	public ProductView(int option, Shop shop) {
		this.option = option;
		this.shop = shop;
		productUI();
	}

	/**
	 * Sets up the product menu UI, including buttons for various actions and their
	 * event listeners.
	 */
	private void productUI() {
		UIManager.put("OptionPane.okButtonText", "OK");
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

		btnAccept = new JButton("OK");
		btnCancel = new JButton("Cancel");

		// panel settings
		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 249, 250));
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

		btnAccept.setBounds(120, 100, 83, 30);
		btnCancel.setBounds(213, 100, 73, 30);

		// setting button fonts
		Font buttonFont = new Font("Arial", Font.BOLD, 12);
		setButtonFonts(new JButton[] { btnAccept, btnCancel }, buttonFont);

		// setting button backgrounds
		Color buttonBackgroundColor = new Color(233, 236, 239);
		setButtonBackgrounds(new JButton[] { btnAccept, btnCancel }, buttonBackgroundColor);

		// setting frame background
		panel.setBackground(new Color(248, 249, 250));

		// adding components to the panel
		panel.add(nameLabel);
		panel.add(prodNameField);
		panel.add(stockLabel);
		panel.add(stockNumField);
		panel.add(priceLabel);
		panel.add(priceNumField);
		panel.add(btnAccept);
		panel.add(btnCancel);

		// adding ActionListener to accept or cancel
		btnAccept.addActionListener(this);
		btnCancel.addActionListener(this);

		// adding the panel to the frame
		getContentPane().add(panel);

		// product view control
		if (option == Constants.ADD_PRODUCT_STOCK) {
			priceLabel.setVisible(false);
			priceNumField.setVisible(false);
		} else if (option == Constants.DELETE_PRODUCT) {
			priceLabel.setVisible(false);
			priceNumField.setVisible(false);
			stockLabel.setVisible(false);
			stockNumField.setVisible(false);
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
	 * Handles action events generated by the buttons in the dialog window.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			dispose();
		} else if (e.getSource() == btnAccept) {
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
		}
	}

	/**
	 * Adds a new product to the shop's inventory.
	 */
	private void addProduct() {
		try {
			String name = prodNameField.getText();
			int stock = Integer.parseInt(stockNumField.getText());
			double price = Double.parseDouble(priceNumField.getText());

			// check if the product exists
			Product existingProduct = shop.findProduct(name);
			if (existingProduct != null) {
				JOptionPane.showMessageDialog(this, "The product already exists in the inventory.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// create a product object with the fields data
			Product product = new Product(name, price, true, stock);

			// add the product to shop's inventory
			shop.addProduct(product);

			// show an informational message
			JOptionPane.showMessageDialog(this, "Product added successfully.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} catch (NumberFormatException e) {
			// Show an error message for invalid input format
			JOptionPane.showMessageDialog(this, "Invalid input format for stock or price.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Adds stock to a product shop's inventory.
	 */
	private void addStock() {
		try {
			// input fields
			String name = prodNameField.getText();
			int additionalStock = Integer.parseInt(stockNumField.getText());

			// send product to the shop method with the parameter
			Product product = shop.findProduct(name);

			// if the product exist add stock
			if (product != null) {
				int updatedStock = product.getStock() + additionalStock;
				product.setStock(updatedStock);

				// show an informational message
				JOptionPane.showMessageDialog(this, "Stock added successfully.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "The product does not exist in the inventory.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Invalid input format for stock or price.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Deletes a product from the shop's inventory.
	 */
	private void deleteProduct() {
		try {
			// input fields
			String name = prodNameField.getText();

			// send product to the shop method with the parameter
			Product product = shop.findProduct(name);

			if (product != null) {
				// language changes for the JOptionPane
				UIManager.put("OptionPane.yesButtonText", "Yes");
				UIManager.put("OptionPane.noButtonText", "No");

				// pane confirmation to delete product
				int result = JOptionPane.showConfirmDialog(this,
						"Are you sure you want to delete the product " + name + "?", "Confirm deletion",
						JOptionPane.YES_NO_OPTION);

				// if the option is yes remove the product from inventory
				if (result == JOptionPane.YES_OPTION) {
					shop.inventory.remove(product);
					// show an informational message
					JOptionPane.showMessageDialog(this, "Product removed successfully.", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				} else {
					System.out.println("No changes have been made.");
				}
			} else {
				JOptionPane.showMessageDialog(this, "The product does not exist in the inventory.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: Invalid product name.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}