package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Employee;
import exception.LimitLoginException;

public class LoginView extends JFrame implements ActionListener {
	private JTextField empNumField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private boolean isLoggedIn;
	private int loginAttempts;

	public static void main(String[] args) {
		// create LoginView and make it visible
		LoginView loginView = new LoginView();
		loginView.setVisible(true);
	}

	public LoginView() {
		productMenu();
	}

	private void productMenu() {
		loginAttempts = 0;
		setTitle("Admin login");
		setSize(350, 175);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// text fields and login button
		empNumField = new JTextField();
		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		loginButton = new JButton("Sign in");
		loginButton.setFont(new Font("Arial", Font.BOLD, 10));

		// panel with absolute layout
		JPanel panel = new JPanel();
		panel.setLayout(null);

		// labels and input fields
		JLabel label1 = new JLabel("POO Shop");
		label1.setBounds(10, 10, 100, 20);
		JLabel label2 = new JLabel("Employee Number:");
		label2.setBounds(10, 40, 120, 20);
		empNumField.setBounds(154, 41, 172, 20);
		JLabel label3 = new JLabel("Password:");
		label3.setBounds(10, 70, 120, 20);
		passwordField.setBounds(154, 70, 172, 20);
		loginButton.setBounds(154, 100, 172, 30);

		panel.add(label1);
		panel.add(label2);
		panel.add(empNumField);
		panel.add(label3);
		panel.add(passwordField);
		panel.add(loginButton);

		// set ActionListener for loginButton
		loginButton.addActionListener(this);

		// adding the panel to the frame
		getContentPane().add(panel);
	}

	// method to check the credentials
	private void checkCredentials() {
		// input fields
		String empNum = empNumField.getText(); // Get employee number from the text field
		String password = new String(passwordField.getPassword()); // Get password from the password field

		// check credentials
		isLoggedIn = Employee.login(Integer.parseInt(empNum), password);
		if (!isLoggedIn) {
			// if login fails increment the login attempts count
			loginAttempts++;
			// calculate the login attempts left
			int remainingAttempts = 3 - loginAttempts;
			// if there is any attempts
			if (remainingAttempts > 0) {
				JOptionPane.showMessageDialog(LoginView.this,
						"Invalid credentials. " + remainingAttempts + " attempts remaining.", "Authentication Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				// if there is no attempts
				try {
					throw new LimitLoginException("Maximum login attempts exceeded");
				} catch (LimitLoginException e) {
					JOptionPane.showMessageDialog(LoginView.this, e.getMessage(), "Login Error ",
							JOptionPane.ERROR_MESSAGE);
					// exit application
					System.exit(0);
				}
			}
		} else {
			// if login succeeds
			dispose();
			ShopView shopView = new ShopView();
			shopView.setVisible(true);
		}
	}

	// method to check if user is logged in
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			checkCredentials();
		}
	}

}
