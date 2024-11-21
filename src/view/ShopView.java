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
import java.io.File;

import util.Constants;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Amount;

public class ShopView extends JFrame implements ActionListener, KeyListener {
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
		this.shop = Shop.getInstance();
		shopUI();
		setFocusable(true);
	}

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
		if (!shop.writeInventory()) {
			JOptionPane.showMessageDialog(this, "Unable to export inventory to file", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(this, "Inventory exported successfully", "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public File nameFolder() {
		Point folderDialogPosition = calculateWindowPosition();

		JDialog dialog = new JDialog(this, "Folder Creation", true);
		dialog.setLocation(folderDialogPosition);
		dialog.setSize(400, 150);
		dialog.getContentPane().setLayout(new GridLayout(2, 1));

		JLabel label = new JLabel("The default folder 'files' does not exist. Please enter a folder name:");

		JTextField folderNameField = new JTextField();

		JButton confirmButton = new JButton("OK");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		dialog.getContentPane().add(label);
		dialog.getContentPane().add(folderNameField);
		dialog.getContentPane().add(confirmButton);

		dialog.setVisible(true);

		String folderNameInput = folderNameField.getText();
		File createdFolder = null;

		if (folderNameInput != null && !folderNameInput.trim().isEmpty()) {
			createdFolder = new File(folderNameInput);
			createdFolder.mkdirs();
		} else {
			System.err.println("No folder name provided. Export operation aborted.");
			return null;
		}

		return createdFolder;
	}

	private void openCashView() {
		Point cashViewPosition = calculateWindowPosition();
		Amount cash = shop.showCash();
		cashView = new CashView(this, cash, shop);
		cashView.setLocation(cashViewPosition);
		cashView.setVisible(true);
	}

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

	private void openInventoryView() {
		Point InventoryViewPosition = calculateWindowPosition();

		InventoryView inventoryView = new InventoryView(shop);
		inventoryView.setLocation(InventoryViewPosition);
		inventoryView.setVisible(true);
	}

	private void openSaleView() {
		Point SaleViewPosition = calculateWindowPosition();
		SaleView SaleView = new SaleView(shop, this);
		SaleView.setLocation(SaleViewPosition);
		SaleView.setVisible(true);
	}

	private void openSaleRecords() {
		// TODO: Implement method to show sale records
	}

	private void openTotalSale() {
		// TODO: Implement method to show total sales
	}

	private void exitOption() {
		System.exit(0);
	}

	private void shopUI() {
		setTitle("Shop menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 380);
		setLocation(calculateXPosition(), calculateYPosition());

		JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));

		// Text fields and buttons
		panel.add(new JLabel("Select or click on an option:"));

		JButton[] buttons = { btnExportInventory = new JButton("0. Export inventory"),
				btnShowCash = new JButton("1. Show cash"), btnAddProduct = new JButton("2. Add product"),
				btnAddStock = new JButton("3. Add stock"), btnSetExpired = new JButton("4. Set expired"),
				btnShowInventory = new JButton("5. Show inventory"), btnMakeSale = new JButton("6. Make sales"),
				btnShowSale = new JButton("7. Sales records"), btnDeleteProduct = new JButton("8. Delete product"),
				btnShowTotalSale = new JButton("9. Total sales"), btnExit = new JButton("10. Exit") };
		btnExportInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		// Setting button font
		Font buttonFont = new Font("Arial", Font.BOLD, 12);
		setButtonFonts(
				new JButton[] { btnExportInventory, btnShowCash, btnAddProduct, btnAddStock, btnSetExpired,
						btnShowInventory, btnMakeSale, btnShowSale, btnDeleteProduct, btnShowTotalSale, btnExit },
				buttonFont);

		for (JButton button : buttons) {
			button.setHorizontalAlignment(SwingConstants.LEFT);
			button.setFont(buttonFont);
			Insets margin = button.getMargin();
			margin.left = 40;
			button.setMargin(margin);
			panel.add(button);
			button.addActionListener(this);
		}

		// Setting a background button color
		Color buttonBackgroundColor = new Color(233, 236, 239);
		setButtonBackgrounds(
				new JButton[] { btnExportInventory, btnShowCash, btnAddProduct, btnAddStock, btnSetExpired,
						btnShowInventory, btnMakeSale, btnShowSale, btnDeleteProduct, btnShowTotalSale, btnExit },
				buttonBackgroundColor);

		// Setting a background color
		panel.setBackground(new Color(248, 249, 250));

		// Add components to panel
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

		// Add panel to the frame
		getContentPane().add(panel);
		this.addKeyListener(this);
	}

	private void setButtonFonts(JButton[] buttons, Font font) {
		for (JButton button : buttons) {
			button.setFont(font);
		}
	}

	private void setButtonBackgrounds(JButton[] buttons, Color color) {
		for (JButton button : buttons) {
			button.setBackground(color);
		}
	}

	private Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	private int calculateYPosition() {
		int screenHeight = (int) getScreenSize().getHeight();
		return (screenHeight - getHeight()) / 2;
	}

	private int calculateXPosition() {
		int screenWidth = (int) getScreenSize().getWidth();
		return (screenWidth - getWidth()) / 4;
	}

	private Point calculateWindowPosition() {
		Point currentLocation = getLocation();
		int newX = currentLocation.x + getWidth() + Constants.PIXELS_APPEARANCE_NEW_WINDOW;
		int newY = currentLocation.y;
		return new Point(newX, newY);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

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
