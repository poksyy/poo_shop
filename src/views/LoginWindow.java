package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Employee;
import java.awt.Window.Type;

public class LoginWindow extends JFrame {
    private JTextField empNumField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private boolean isLoggedIn;

    public LoginWindow() {
    	setFont(new Font("Arial", Font.PLAIN, 12));
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\xpcar\\Downloads\\computer-icons-user-profile-material-design-profile-d6a8d1ffc84df96878c4b1f439c68f06.png"));
        setTitle("Login");
        setSize(300, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // text fields and login button
        empNumField = new JTextField(10);
        passwordField = new JPasswordField(10);
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 10));

        // panel with grid layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        
        // labels and input fields
        panel.add(new JLabel("Employee Number:"));
        panel.add(empNumField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        // action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	checkCredentials();
            }
        });

        // adding the panel to the frame
        getContentPane().add(panel);

        isLoggedIn = false;
    }

    // method to check the credentials
    private void checkCredentials() {
        // retrieving input values
        String empNum = empNumField.getText();
        String password = new String(passwordField.getPassword());
        
        // check credentials
        isLoggedIn = Employee.login(Integer.parseInt(empNum), password);
        if (!isLoggedIn) {
            JOptionPane.showMessageDialog(LoginWindow.this, "Invalid credentials", "Authentication Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // if login succeeds, close window
            dispose();
        }
    }
    
    // method to check if user is logged in
    public boolean isLoggedIn() {
        return isLoggedIn;
    }
    
}
