package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Amount;

public class ShopView extends JFrame implements ActionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShopView frame = new ShopView();
					frame.setVisible(true);
					// allow keyboard focus on frame
					frame.setFocusable(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ShopView() {
		this.shop = new Shop();
		shopUI();
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
		if (e.getSource() == btnShowCash) {
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

	/**
	 * Opens the CashView window at a calculated position.
	 */
	private void openCashView() {
		Point cashViewPosition = calculateWindowPosition();

		Amount cash = shop.showCash();
		CashView cashView = new CashView(this, cash);
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
		Point cashViewPosition = calculateWindowPosition();

		ProductView productView = new ProductView(option, shop);
		productView.setLocation(cashViewPosition);
		productView.setVisible(true);
	}

	private void openExpiredView() {
		// TODO Auto-generated method stub

	}

	/**
	 * Opens the InventoryView window at a calculated position.
	 */
	private void openInventoryView() {
		Point cashViewPosition = calculateWindowPosition();

		InventoryView inventoryView = new InventoryView(shop);
		inventoryView.setLocation(cashViewPosition);
		inventoryView.setVisible(true);
	}

	private void openSaleView() {
		// TODO Auto-generated method stub

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

		// text fields and buttons
		btnShowCash = new JButton("1. Show cash");
		btnShowCash.setFont(new Font("Arial", Font.BOLD, 10));
		btnAddProduct = new JButton("2. Add product");
		btnAddProduct.setFont(new Font("Arial", Font.BOLD, 10));
		btnAddStock = new JButton("3. Add stock");
		btnAddStock.setFont(new Font("Arial", Font.BOLD, 10));
		btnSetExpired = new JButton("4. Set expired");
		btnSetExpired.setFont(new Font("Arial", Font.BOLD, 10));
		btnShowInventory = new JButton("5. Show inventory");
		btnShowInventory.setFont(new Font("Arial", Font.BOLD, 10));
		btnMakeSale = new JButton("6. Make sales");
		btnMakeSale.setFont(new Font("Arial", Font.BOLD, 10));
		btnShowSale = new JButton("7. Sales records");
		btnShowSale.setFont(new Font("Arial", Font.BOLD, 10));
		btnDeleteProduct = new JButton("8. Delete product");
		btnDeleteProduct.setFont(new Font("Arial", Font.BOLD, 10));
		btnShowTotalSale = new JButton("9. Total sales");
		btnShowTotalSale.setFont(new Font("Arial", Font.BOLD, 10));
		btnExit = new JButton("10. Exit");
		btnExit.setFont(new Font("Arial", Font.BOLD, 10));

		// panel with absolute layout
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(null);

		// labels and buttons
		JLabel label = new JLabel("Select or click on an option:");
		label.setBounds(10, 10, 200, 20);

		// first column
		btnShowCash.setBounds(10, 40, 220, 50);
		btnAddProduct.setBounds(10, 100, 220, 50);
		btnAddStock.setBounds(10, 160, 220, 50);
		btnSetExpired.setBounds(10, 220, 220, 50);
		btnShowInventory.setBounds(10, 280, 220, 50);

		// second column
		btnMakeSale.setBounds(255, 40, 220, 50);
		btnShowSale.setBounds(255, 100, 220, 50);
		btnDeleteProduct.setBounds(255, 160, 220, 50);
		btnShowTotalSale.setBounds(255, 220, 220, 50);
		btnExit.setBounds(255, 280, 220, 50);

		// add action listeners for buttons
		btnShowCash.addActionListener(this);
		btnAddProduct.addActionListener(this);
		btnAddStock.addActionListener(this);
		btnShowInventory.addActionListener(this);
		btnDeleteProduct.addActionListener(this);
		btnExit.addActionListener(this);

		// adding components to the panel
		panel.add(label);
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
		return (screenWidth - getWidth()) / 3;
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
		case KeyEvent.VK_0:
			exitOption();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
