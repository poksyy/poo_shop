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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import model.Employee;

public class ShopView extends JFrame implements ActionListener, KeyListener {
    private JButton countCashButton;
    private JButton addProductButton;
    private JButton addStockButton;
    private JButton deleteProductButton;
    private JTextField empNumField;
    private JPasswordField passwordField;
    private boolean isLoggedIn;

	private static final long serialVersionUID = 1L;
	private JPanel panel;

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
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // text fields and login button
        empNumField = new JTextField(10);
        passwordField = new JPasswordField(10);
        countCashButton = new JButton("Count cash");
        addProductButton = new JButton("Add product");
        addStockButton = new JButton("Add stock");
        deleteProductButton = new JButton("Delete product");
        countCashButton.setFont(new Font("Arial", Font.BOLD, 10));
        addProductButton.setFont(new Font("Arial", Font.BOLD, 10));
        addStockButton.setFont(new Font("Arial", Font.BOLD, 10));
        deleteProductButton.setFont(new Font("Arial", Font.BOLD, 10));
        
        // panel with grid layout
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(5,1));
        
        // labels and input fields
        panel.add(new JLabel("Select or click on an option:"));
        panel.add(countCashButton);
        panel.add(addProductButton);
        panel.add(addStockButton);
        panel.add(deleteProductButton);

        // set ActionListener for loginButton
        countCashButton.addActionListener(this);
        addProductButton.addActionListener(this);
        addStockButton.addActionListener(this);
        deleteProductButton.addActionListener(this);

        // adding the panel to the frame
        getContentPane().add(panel);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    // handle button click event
	    if (e.getSource() == countCashButton) {
	        checkCredentials();
	    } else if (e.getSource() == addProductButton) {
	        // handle add product button click
	    } else if (e.getSource() == addStockButton) {
	        // handle add stock button click
	    } else if (e.getSource() == deleteProductButton) {
	        // handle delete product button click
	    }
	}

    // method to check the credentials
    private void checkCredentials() {
        // retrieving input values
        String empNum = empNumField.getText();
        String password = new String(passwordField.getPassword());
        
        // check credentials
        isLoggedIn = Employee.login(Integer.parseInt(empNum), password);
        if (!isLoggedIn) {
            // if login fails, show error message panel
            JOptionPane.showMessageDialog(ShopView.this, "Invalid credentials", "Authentication Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // if login succeeds, close window
            dispose();
        }
    }
}
