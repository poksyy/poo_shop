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
     * @param shop The shop containing the product inventory.
     */
    public InventoryView(Shop shop) {
        this.shop = shop;
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

        // Get the list of products from the shop
        List<Product> products = shop.getInventory();

        // Create the table
        String[] columnNames = { "Id", "Name", "Public Price", "Wholesaler Price", "Stock" };
        Object[][] data = new Object[products.size()][columnNames.length];

        // Insert data into the table
        populateTableData(products, data);

        // Configure the table
        JTable table = new JTable(data, columnNames);
        table.setBackground(new Color(248, 249, 250));
        table.setFillsViewportHeight(true); // Fills the full height available
        JScrollPane scrollPane = new JScrollPane(table); // Allows scrolling

        // Add the scroll pane to the window
        getContentPane().add(scrollPane);
    }

    /**
     * Populates the table data with product information.
     *
     * @param products The list of products.
     * @param data     The data array where values will be stored.
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
    }
}
