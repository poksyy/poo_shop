package view;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import main.Shop;
import model.Product;

public class InventoryView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Shop shop;

	/**
	 * Constructor that initializes the inventory view.
	 *
	 * @param shop The shop that contains the product inventory.
	 */
	public InventoryView(Shop shop) {
		this.shop = shop;
        shop.loadInventory();
		initializeUI();
	}

	/**
	 * Initializes and displays the inventory user interface.
	 */
	private void initializeUI() {
		setTitle("Product Inventory");
		setSize(500, 380);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		List<Product> products = shop.getInventory();

		// Use the provided product list directly
		if (products == null || products.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No products available.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// Create the table
		String[] columnNames = { "Id", "Name", "Public Price", "Wholesaler Price", "Stock" };
		Object[][] data = new Object[products.size()][columnNames.length];

		// Insert data into the table
		populateTableData(products, data);

		// Configure the table
		JTable table = new JTable(data, columnNames);
		table.setBackground(new Color(248, 249, 250));
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll panel to the window
		getContentPane().add(scrollPane);
	}

	/**
	 * Populates the table with product data.
	 */
	private void populateTableData(List<Product> products, Object[][] data) {
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			data[i][0] = product.getId();
			data[i][1] = product.getName();
			data[i][2] = product.getPublicPrice();
			data[i][3] = product.getWholesalerPrice();
			data[i][4] = product.getStock();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Action handling logic can be implemented here
	}
}
