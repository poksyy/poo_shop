package view;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import main.Shop;
import model.Product;

public class InventoryView extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shop shop;

	public InventoryView(Shop shop) {
		this.shop = shop;
		inventoryUI();
	}

	/**
	 * Initializes and displays the inventory user interface.
	 */
	private void inventoryUI() {
		setTitle("Product inventory");
		setSize(500, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// get the list of products from the shop
		List<Product> products = shop.getInventory();

		// table
		String[] columnNames = { "Id", "Name", "Public price", "Wholesaler Price", "Stock" };
		Object[][] data = new Object[products.size()][5];
		// insert data by position
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			data[i][0] = product.getId();
			data[i][1] = product.getName();
			data[i][2] = product.getPublicPrice();
			data[i][3] = product.getWholesalerPrice();
			data[i][4] = product.getStock();
		}

		JTable table = new JTable(data, columnNames);
		table.setBackground(new Color(248, 249, 250));
		// fills the full height available in the scroll container
		table.setFillsViewportHeight(true);
		// allow scrolling if the table has more rows than the dialog box space
		JScrollPane scrollPane = new JScrollPane(table);

		// adding components to the panel
		getContentPane().add(scrollPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
