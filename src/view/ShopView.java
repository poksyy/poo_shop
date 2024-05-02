package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Shop;
import model.Amount;

public class ShopView extends JFrame implements ActionListener, KeyListener {
    private JButton countCashButton;
    private JButton addProductButton;
    private JButton addStockButton;
    private JButton deleteProductButton;
    private Shop shop;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ShopView frame = new ShopView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ShopView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setLocationRelativeTo(null);
        shop = new Shop();

        // text fields and login button
        countCashButton = new JButton("Show cash");
        countCashButton.setFont(new Font("Arial", Font.BOLD, 10));
        addProductButton = new JButton("Add product");
        addProductButton.setFont(new Font("Arial", Font.BOLD, 10));
        addStockButton = new JButton("Add stock");
        addStockButton.setFont(new Font("Arial", Font.BOLD, 10));
        deleteProductButton = new JButton("Delete product");
        deleteProductButton.setFont(new Font("Arial", Font.BOLD, 10));

        // panel with grid layout
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(0, 1, 0, 0));

        // labels and buttons
        panel.add(new JLabel("Select or click on an option:"));
        panel.add(countCashButton);
        panel.add(addProductButton);
        panel.add(addStockButton);
        panel.add(deleteProductButton);

        // set ActionListener for menu buttons
        countCashButton.addActionListener(this);
        addProductButton.addActionListener(this);
        addStockButton.addActionListener(this);
        deleteProductButton.addActionListener(this);

        // adding the panel to the frame
        getContentPane().add(panel);

        // set the frame visible and center it on the screen
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == countCashButton) {
            // show cash in a new dialog
            dispose();
            Amount cash = shop.showCash();
            CashView cashView = new CashView(this, cash);
            cashView.setVisible(true);
        } else if (e.getSource() == addProductButton) {
        	shop.addProduct();
        } else if (e.getSource() == addStockButton) {
        	shop.addStock();
        } else if (e.getSource() == deleteProductButton) {
        	shop.deleteProduct();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}