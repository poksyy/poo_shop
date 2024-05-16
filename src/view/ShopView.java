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
	private JButton showCashButton;
	private JButton addProductButton;
	private JButton addStockButton;
	private JButton showInventoryButton;
	private JButton deleteProductButton;
	private JButton exitButton;
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
		if (e.getSource() == showCashButton) {
			openCashView();
		} else if (e.getSource() == addProductButton) {
			openProductView(Constants.ADD_PRODUCT);
		} else if (e.getSource() == addStockButton) {
			openProductView(Constants.ADD_PRODUCT_STOCK);
		} else if (e.getSource() == showInventoryButton) {
			openInventoryView();
		} else if (e.getSource() == deleteProductButton) {
			openProductView(Constants.DELETE_PRODUCT);
		} else if (e.getSource() == exitButton) {
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

	/**
	 * Opens the InventoryView window at a calculated position.
	 */
	private void openInventoryView() {
		Point cashViewPosition = calculateWindowPosition();

		InventoryView inventoryView = new InventoryView(shop);
		inventoryView.setLocation(cashViewPosition);
		inventoryView.setVisible(true);
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

		// Text fields and buttons
		showCashButton = new JButton("1. Show cash");
		showCashButton.setFont(new Font("Arial", Font.BOLD, 10));
		addProductButton = new JButton("2. Add product");
		addProductButton.setFont(new Font("Arial", Font.BOLD, 10));
		addStockButton = new JButton("3. Add stock");
		addStockButton.setFont(new Font("Arial", Font.BOLD, 10));
		showInventoryButton = new JButton("5. Show inventory");
		showInventoryButton.setFont(new Font("Arial", Font.BOLD, 10));
		deleteProductButton = new JButton("9. Delete product");
		deleteProductButton.setFont(new Font("Arial", Font.BOLD, 10));
		exitButton = new JButton("10. Exit");
		exitButton.setFont(new Font("Arial", Font.BOLD, 10));

		// Panel with absolute layout
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(null);

		// Labels and buttons
		JLabel label = new JLabel("Select or click on an option:");
		label.setBounds(10, 10, 200, 20);

		// First column
		showCashButton.setBounds(10, 40, 220, 50);
		addProductButton.setBounds(10, 100, 220, 50);
		addStockButton.setBounds(10, 160, 220, 50);
		// Second column
		showInventoryButton.setBounds(255, 40, 220, 50);
		deleteProductButton.setBounds(255, 100, 220, 50);
		exitButton.setBounds(255, 160, 220, 50);

		// Add action listeners for buttons
		showCashButton.addActionListener(this);
		addProductButton.addActionListener(this);
		addStockButton.addActionListener(this);
		showInventoryButton.addActionListener(this);
		deleteProductButton.addActionListener(this);
		exitButton.addActionListener(this);

		// Adding components to the panel
		panel.add(label);
		panel.add(showCashButton);
		panel.add(addProductButton);
		panel.add(addStockButton);
		panel.add(showInventoryButton);
		panel.add(deleteProductButton);
		panel.add(exitButton);

		// Adding the panel to the frame
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
		// TODO Auto-generated method stub

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
		case KeyEvent.VK_5:
			openInventoryView();
			break;
		case KeyEvent.VK_9:
			openProductView(Constants.DELETE_PRODUCT);
			break;
		case KeyEvent.VK_0:
			exitOption();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
