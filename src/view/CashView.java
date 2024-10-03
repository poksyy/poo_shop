package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import main.Shop;
import model.Amount;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CashView extends JDialog {
    private static final long serialVersionUID = 1L;

    // Fields
    private Shop shop;
    private JLabel cashLabel;

    /**
     * Initializes and displays the CashView user interface.
     *
     * @param shopView The parent ShopView.
     * @param cash     The amount of cash to display.
     */
    public CashView(ShopView shopView, Amount cash, Shop shop) {
        this.shop = shop;

        // Set dialog properties
        setTitle("Cash");
        setSize(200, 80);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Initialize components
        initializeComponents();

        // Add window listener to handle closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                shopView.setVisible(true);
            }
        });
    }

    /**
     * Initializes the components of the CashView.
     */
    private void initializeComponents() {
        // Create and configure cash label
        cashLabel = new JLabel("Current cash: " + shop.getCash().toString(), JLabel.LEFT);

        // Set up panel settings
        JPanel panel = new JPanel();
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BorderLayout());

        // Add label to panel
        panel.add(cashLabel, BorderLayout.CENTER);

        // Add panel to dialog
        getContentPane().add(panel);
    }
}
