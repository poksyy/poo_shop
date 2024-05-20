package view;

import util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Employee;
import exception.LimitLoginException;

public class LoginView extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField empNumField;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private boolean isLoggedIn;
	private int loginAttempts;

	public LoginView() {
		loginUI();
	}

	/**
	 * Sets up the user interface for the login screen, including labels, text
	 * fields, and buttons, and handles the button click event for login.
	 */
	private void loginUI() {
		UIManager.put("OptionPane.okButtonText", "OK");
		loginAttempts = 0;
		setTitle("Admin login");
		setSize(350, 175);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel panel = new JPanel();

		// text fields and login button
		empNumField = new JTextField();
		passwordField = new JPasswordField();
		btnLogin = new JButton("Sign in");
		btnLogin.setFont(new Font("Arial", Font.BOLD, 12));

		// panel settings
		panel.setLayout(null);

		// setting button fonts
		Font buttonFont = new Font("Arial", Font.BOLD, 12);
		setButtonFonts(new JButton[] { btnLogin }, buttonFont);

		// setting button backgrounds
		Color buttonBackgroundColor = new Color(233, 236, 239);
		setButtonBackgrounds(new JButton[] { btnLogin }, buttonBackgroundColor);

		// setting frame background
		panel.setBackground(new Color(248, 249, 250));

		// labels and input fields
		JLabel lblShop = new JLabel("POO Shop");
		lblShop.setBounds(10, 10, 100, 20);
		JLabel lblEmployee = new JLabel("Employee Number:");
		lblEmployee.setBounds(10, 40, 120, 20);
		empNumField.setBounds(154, 41, 172, 20);
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 70, 120, 20);
		passwordField.setBounds(154, 70, 172, 20);
		btnLogin.setBounds(154, 100, 172, 30);

		panel.add(lblShop);
		panel.add(lblEmployee);
		panel.add(empNumField);
		panel.add(lblPassword);
		panel.add(passwordField);
		panel.add(btnLogin);

		// set ActionListener for loginButton
		btnLogin.addActionListener(this);

		// adding the panel to the frame
		getContentPane().add(panel);
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
	 * Validates the entered credentials by extracting the employee number and
	 * password from the input fields, then checks if the employee number is an
	 * integer. If the login fails, it handles the remaining login attempts and
	 * clears the input fields. If the login succeeds, it disposes of the login
	 * window and opens the ShopView.
	 */
	private void checkCredentials() {
		// input fields
		String empNum = empNumField.getText(); 
		String password = new String(passwordField.getPassword()); 

		// validate if employee number is an integer
		try {
			int empNumInt = Integer.parseInt(empNum);
			// check credentials
			isLoggedIn = Employee.login(empNumInt, password);
			if (!isLoggedIn) {
				incrementLoginAttempts();
				int remainingAttempts = Constants.MAX_LOGIN_ATTEMPTS - loginAttempts;
				if (remainingAttempts > 0) {
					showLoginAttempts(remainingAttempts);
					clearFields();
				} else {
					handleExceededAttempts();
				}
			} else {
				// if login succeeds
				dispose();
				ShopView shopView = new ShopView();
				shopView.setVisible(true);
			}
		} catch (NumberFormatException e) {
			incrementLoginAttempts();
			clearFields();
			showLoginAttempts(Constants.MAX_LOGIN_ATTEMPTS - loginAttempts);
		}
	}

	/**
	 * Increments the login attempts count and checks if it exceeds the maximum
	 * allowed attempts. If exceeded, it invokes the handleExceededAttempts method.
	 */
	private void incrementLoginAttempts() {
		loginAttempts++;
		if (loginAttempts >= Constants.MAX_LOGIN_ATTEMPTS) {
			handleExceededAttempts();
		}
	}

	/**
	 * Displays a message dialog indicating the number of remaining login attempts.
	 * 
	 * @param remainingAttempts the number of remaining login attempts
	 */
	private void showLoginAttempts(int remainingAttempts) {
		JOptionPane.showMessageDialog(LoginView.this,
				"Invalid credentials. " + remainingAttempts + " attempts remaining.", "Authentication Error",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Handles the scenario when the maximum login attempts are exceeded by throwing
	 * a LimitLoginException and displaying an error message. It then exits the
	 * application.
	 */
	private void handleExceededAttempts() {
		try {
			throw new LimitLoginException("Maximum login attempts exceeded");
		} catch (LimitLoginException e) {
			JOptionPane.showMessageDialog(LoginView.this, e.getMessage(), "Login Error ", JOptionPane.ERROR_MESSAGE);
			// exit application
			System.exit(0);
		}
	}

	/**
	 * Checks if an employee is currently logged in.
	 * 
	 * @return true if an employee is logged in, false otherwise
	 */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * Clears the text fields for employee number and password.
	 */
	private void clearFields() {
		empNumField.setText("");
		passwordField.setText("");
	}

	/**
	 * Handles the action events triggered by components in the login UI,
	 * specifically checking if the login button is pressed and invoking the
	 * checkCredentials method accordingly.
	 * 
	 * @param e the ActionEvent object representing the action performed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			checkCredentials();
		}
	}

}
