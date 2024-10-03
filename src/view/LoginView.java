package view;

import util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Employee;
import exception.LimitLoginException;

public class LoginView extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JTextField empNumField;
    private JPasswordField passwordField;
    private JButton btnLogin;
    private boolean isLoggedIn;
    private int loginAttempts;

    public LoginView() {
        initializeUI();
    }

    /**
     * Sets up the user interface for the login screen, including labels, text
     * fields, and buttons, and handles the button click event for login.
     */
    private void initializeUI() {
        UIManager.put("OptionPane.okButtonText", "OK");
        loginAttempts = 0;

        setTitle("Admin Login");
        setSize(350, 175);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(248, 249, 250));

        // Create text fields and login button
        empNumField = new JTextField();
        passwordField = new JPasswordField();
        btnLogin = new JButton("Sign in");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 12));

        // Setup UI components
        setupUIComponents(panel);

        // Set ActionListener for the login button
        btnLogin.addActionListener(this);

        // Add the panel to the frame
        getContentPane().add(panel);
    }

    /**
     * Sets up the UI components like labels, text fields, and buttons.
     * 
     * @param panel The panel to which components will be added.
     */
    private void setupUIComponents(JPanel panel) {
        JLabel lblShop = new JLabel("POO Shop");
        lblShop.setBounds(10, 10, 100, 20);
        
        JLabel lblEmployee = new JLabel("Employee Number:");
        lblEmployee.setBounds(10, 40, 120, 20);
        
        empNumField.setBounds(154, 41, 172, 20);
        
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(10, 70, 120, 20);
        
        passwordField.setBounds(154, 70, 172, 20);
        btnLogin.setBounds(154, 100, 172, 30);

        // Add components to panel
        panel.add(lblShop);
        panel.add(lblEmployee);
        panel.add(empNumField);
        panel.add(lblPassword);
        panel.add(passwordField);
        panel.add(btnLogin);

        // Set button styles
        setButtonFonts(new JButton[] { btnLogin }, new Font("Arial", Font.BOLD, 12));
        setButtonBackgrounds(new JButton[] { btnLogin }, new Color(233, 236, 239));
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
     * password from the input fields, checking if the employee number is an
     * integer, and handling login attempts accordingly.
     */
    private void checkCredentials() {
        String empNum = empNumField.getText();
        String password = new String(passwordField.getPassword());

        try {
            int empNumInt = Integer.parseInt(empNum);
            isLoggedIn = Employee.login(empNumInt, password);

            if (!isLoggedIn) {
                handleFailedLogin();
            } else {
                dispose();
                ShopView shopView = new ShopView();
                shopView.setVisible(true);
            }
        } catch (NumberFormatException e) {
            handleFailedLogin();
        }
    }

    /**
     * Handles failed login attempts by incrementing the attempt count and
     * displaying appropriate messages.
     */
    private void handleFailedLogin() {
        incrementLoginAttempts();
        int remainingAttempts = Constants.MAX_LOGIN_ATTEMPTS - loginAttempts;
        if (remainingAttempts > 0) {
            showLoginAttempts(remainingAttempts);
            clearFields();
        } else {
            handleExceededAttempts();
        }
    }

    /**
     * Increments the login attempts count and checks if it exceeds the maximum
     * allowed attempts.
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
     * a LimitLoginException and displaying an error message.
     */
    private void handleExceededAttempts() {
        try {
            throw new LimitLoginException("Maximum login attempts exceeded");
        } catch (LimitLoginException e) {
            JOptionPane.showMessageDialog(LoginView.this, e.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0); // Exit application
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
