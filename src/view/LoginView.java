package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(300, 150);
        setLocationRelativeTo(null);
        setFont(new Font("Arial", Font.PLAIN, 12));
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\xpcar\\git\\repository3\\dam2_m03_uf2_poo_shop\\src\\resources\\user-icon.png"));
        setTitle("Admin login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // text fields and login button
        empNumField = new JTextField(10);
        passwordField = new JPasswordField(10);
        passwordField.setEchoChar('*');
        loginButton = new JButton("Sign in");
        loginButton.setFont(new Font("Arial", Font.BOLD, 10));
        
        // panel with grid layout
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(4,2));
        
        // labels and input fields
        panel.add(new JLabel("POO Shop"));
        panel.add(new JLabel());
        panel.add(new JLabel("Employee Number:"));
        panel.add(empNumField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        // set ActionListener for loginButton
        loginButton.addActionListener(this);

        // adding the panel to the frame
        getContentPane().add(panel);
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
