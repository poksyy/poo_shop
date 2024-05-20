package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Amount;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CashView extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
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
        setTitle("Cash");
        setSize(200, 80);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // text fields
        cashLabel = new JLabel("Current cash: " + shop.getCash().toString(), JLabel.LEFT);

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

    /**
     * Updates the cash label with the new amount.
     * 
     * @param cash The new amount of cash to display.
     */
    public void updateCash(Amount cash) {
        cashLabel.setText("Current cash: " + cash.toString());
    }
}
