package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import util.Constants;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Amount;

public class ShopView extends JFrame implements ActionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnExportInventory;
	private JButton btnShowCash;
	private JButton btnAddProduct;
	private JButton btnAddStock;
	private JButton btnSetExpired;
	private JButton btnShowInventory;
	private JButton btnMakeSale;
	private JButton btnShowSale;
	private JButton btnDeleteProduct;
	private JButton btnShowTotalSale;
	private JButton btnExit;
	private Shop shop;
	private CashView cashView;

	public ShopView() {
		this.shop = new Shop();
		shopUI();
		setFocusable(true);
		shop.loadInventory();
	}

	/**
	 * Handles action events triggered by button presses. Opens different views
	 * based on the button pressed.
	 * 
	 * @param e the ActionEvent triggered by the button press
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnExportInventory) {
			openExportInventory();
		} else if (e.getSource() == btnShowCash) {
			openCashView();
		} else if (e.getSource() == btnAddProduct) {
			openProductView(Constants.ADD_PRODUCT);
		} else if (e.getSource() == btnAddStock) {
			openProductView(Constants.ADD_PRODUCT_STOCK);
		} else if (e.getSource() == btnSetExpired) {
			openExpiredView();
		} else if (e.getSource() == btnShowInventory) {
			openInventoryView();
		} else if (e.getSource() == btnMakeSale) {
			openSaleView();
		} else if (e.getSource() == btnShowSale) {
			openSaleRecords();
		} else if (e.getSource() == btnDeleteProduct) {
			openProductView(Constants.DELETE_PRODUCT);
		} else if (e.getSource() == btnShowTotalSale) {
			openTotalSale();
		} else if (e.getSource() == btnExit) {
			exitOption();
		}
	}

	private void openExportInventory() {
	    shop.writeInventory();
	    
		Point exportInventoryViewPosition = calculateWindowPosition();

		ExportInventoryView inventoryExportView = new ExportInventoryView(shop);
		inventoryExportView.setLocation(exportInventoryViewPosition);
		inventoryExportView.setVisible(true);
	}


	/**
	 * Opens the CashView window at a calculated position.
	 */
	private void openCashView() {
		Point cashViewPosition = calculateWindowPosition();

		Amount cash = shop.showCash();
		cashView = new CashView(this, cash, shop);
		cashView.setLocation(cashViewPosition);
		cashView.setVisible(true);
	}

	/**
	 * Opens the ProductView window with a specified option at a calculated
	 * position.
	 * 
	 * @param option the option specifying the action to be performed in the
	 *               ProductView
	 */
	private void openProductView(int option) {
		Point ProductViewPosition = calculateWindowPosition();

		ProductView productView = new ProductView(option, shop);
		productView.setLocation(ProductViewPosition);
		productView.setVisible(true);
	}

	private void openExpiredView() {
		Point ExpiredProductViewPosition = calculateWindowPosition();

		ExpiredProductView productExpiredView = new ExpiredProductView(shop);
		productExpiredView.setLocation(ExpiredProductViewPosition);
		productExpiredView.setVisible(true);
	}

	/**
	 * Opens the InventoryView window at a calculated position.
	 */
	private void openInventoryView() {
		Point InventoryViewPosition = calculateWindowPosition();

		InventoryView inventoryView = new InventoryView(shop);
		inventoryView.setLocation(InventoryViewPosition);
		inventoryView.setVisible(true);
	}

	/**
	 * Opens the SaleView window at a calculated position.
	 */
	private void openSaleView() {
		Point SaleViewPosition = calculateWindowPosition();

		SaleView SaleView = new SaleView(shop, this);
		SaleView.setLocation(SaleViewPosition);
		SaleView.setVisible(true);
	}

	private void openSaleRecords() {
		// TODO Auto-generated method stub

	}

	private void openTotalSale() {
		// TODO Auto-generated method stub

	}

	/**
	 * Exits the application by terminating the Java Virtual Machine.
	 */
	private void exitOption() {
		System.exit(0);
	}

	/**
	 * Sets up the main shop menu UI, including buttons for various actions and
	 * their event listeners.
	 */
	private void shopUI() {
		setTitle("Shop menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 380);
		setLocation(calculateXPosition(), calculateYPosition());
		shop = new Shop();

		JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));

		// text fields and buttons
		panel.add(new JLabel("Select or click on an option:"));
		
		JButton[] buttons = { btnExportInventory = new JButton("0. Export inventory"), btnShowCash = new JButton("1. Show cash"), btnAddProduct = new JButton("2. Add product"),
				btnAddStock = new JButton("3. Add stock"), btnSetExpired = new JButton("4. Set expired"),
				btnShowInventory = new JButton("5. Show inventory"), btnMakeSale = new JButton("6. Make sales"),
				btnShowSale = new JButton("7. Sales records"), btnDeleteProduct = new JButton("8. Delete product"),
				btnShowTotalSale = new JButton("9. Total sales"), btnExit = new JButton("10. Exit") };

		// setting button fonts
		Font buttonFont = new Font("Arial", Font.BOLD, 12);
		setButtonFonts(new JButton[] { btnExportInventory, btnShowCash, btnAddProduct, btnAddStock, btnSetExpired, btnShowInventory,
				btnMakeSale, btnShowSale, btnDeleteProduct, btnShowTotalSale, btnExit }, buttonFont);

		for (JButton button : buttons) {
			button.setHorizontalAlignment(SwingConstants.LEFT);
			button.setFont(buttonFont);

			// adjust the inner margin to center the text almost in the middle
			Insets margin = button.getMargin();
			margin.left = 40;
			button.setMargin(margin);

			panel.add(button);
			button.addActionListener(this);
		}

		// setting button backgrounds
		Color buttonBackgroundColor = new Color(233, 236, 239);
		setButtonBackgrounds(new JButton[] { btnExportInventory, btnShowCash, btnAddProduct, btnAddStock, btnSetExpired, btnShowInventory,
				btnMakeSale, btnShowSale, btnDeleteProduct, btnShowTotalSale, btnExit }, buttonBackgroundColor);

		// setting frame background
		panel.setBackground(new Color(248, 249, 250));

		// adding components to the panel
		panel.add(btnExportInventory);
		panel.add(btnShowCash);
		panel.add(btnAddProduct);
		panel.add(btnAddStock);
		panel.add(btnSetExpired);
		panel.add(btnShowInventory);
		panel.add(btnMakeSale);
		panel.add(btnShowSale);
		panel.add(btnDeleteProduct);
		panel.add(btnShowTotalSale);
		panel.add(btnExit);

		// adding the panel to the frame
		getContentPane().add(panel);

		this.addKeyListener(this);
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
	 * Gets the screen size of the display.
	 * 
	 * @return Dimension representing the width and height of the screen.
	 */
	private Dimension getScreenSize() {
		// class which provides a way to represent the size of a component or a graphic
		// object in two dimensions, i.e. width and height
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	/**
	 * Calculates the Y position to center the window vertically on the screen.
	 * 
	 * @return int representing the Y coordinate.
	 */
	private int calculateYPosition() {
		int screenHeight = (int) getScreenSize().getHeight();
		return (screenHeight - getHeight()) / 2;
	}

	/**
	 * Calculates the X position to center the window horizontally on the screen.
	 * 
	 * @return int representing the X coordinate.
	 */
	private int calculateXPosition() {
		int screenWidth = (int) getScreenSize().getWidth();
		return (screenWidth - getWidth()) / 4;
	}

	/**
	 * Calculates the position where a new window should appear relative to the
	 * current window.
	 * 
	 * @return Point representing the new window's X and Y coordinates.
	 */
	private Point calculateWindowPosition() {
		// class that represents a point in a two-dimensional space with x and y
		// coordinates
		Point currentLocation = getLocation();
		int newX = currentLocation.x + getWidth() + Constants.PIXELS_APPEARANCE_NEW_WINDOW;
		int newY = currentLocation.y;
		return new Point(newX, newY);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * handles key press events to open views
	 * 
	 * @param e the KeyEvent triggered by the key press
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int productOption = e.getKeyCode();

		switch (productOption) {
		case KeyEvent.VK_0:
			openExportInventory();
			break;
		case KeyEvent.VK_1:
			openCashView();
			break;
		case KeyEvent.VK_2:
			openProductView(Constants.ADD_PRODUCT);
			break;
		case KeyEvent.VK_3:
			openProductView(Constants.ADD_PRODUCT_STOCK);
			break;
		case KeyEvent.VK_4:
			openExpiredView();
			break;
		case KeyEvent.VK_5:
			openInventoryView();
			break;
		case KeyEvent.VK_6:
			openSaleView();
			break;
		case KeyEvent.VK_7:
			openSaleRecords();
			break;
		case KeyEvent.VK_8:
			openProductView(Constants.DELETE_PRODUCT);
			break;
		case KeyEvent.VK_9:
			openTotalSale();
			break;
		}
		this.requestFocus();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
