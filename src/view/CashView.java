package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Amount;

public class CashView extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel cashLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// create objects
					Shop shop = new Shop();
					ShopView shopView = new ShopView();
					Amount cash = shop.showCash();
					CashView cashView = new CashView(shopView, cash);

					shopView.setVisible(true);

					// get the current cash amount
					cashView.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initializes and displays the CashView user interface.
	 * 
	 * @param shopView The parent ShopView.
	 * @param cash     The amount of cash to display.
	 */
	public CashView(ShopView shopView, Amount cash) {
		setTitle("Cash");
		setSize(200, 80);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// text fields
		cashLabel = new JLabel("Current cash: " + cash.toString(), JLabel.LEFT);

		// panel settings
		JPanel panel = new JPanel();
		panel.setBackground(new Color(248, 249, 250));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setLayout(new BorderLayout());

		// labels and input fields
		panel.add(cashLabel, BorderLayout.CENTER);

		// adding the panel to the frame
		getContentPane().add(panel);

		// add a window listener to set the ShopView visible when the CashView is closed
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				shopView.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}