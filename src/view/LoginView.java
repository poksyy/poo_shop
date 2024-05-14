package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Employee;

public class LoginView extends JFrame implements ActionListener {
    private JTextField empNumField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private boolean isLoggedIn;
    
    public static void main(String[] args) {
        // create LoginView and make it visible
        LoginView loginView = new LoginView();
        loginView.setVisible(true);
    }

    
    public LoginView() {
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
        // Retrieving input values
        String empNum = empNumField.getText();
        String password = new String(passwordField.getPassword());
        
        // check credentials
        isLoggedIn = Employee.login(Integer.parseInt(empNum), password);
        if (!isLoggedIn) {
            // if login fails, show error message panel
            JOptionPane.showMessageDialog(LoginView.this, "Invalid credentials", "Authentication Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // if login succeeds, close this window and open ShopView
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
