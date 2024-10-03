package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplJDBC implements Dao {

    private Connection connection;

    public DaoImplJDBC() {
    }

    // Connection management
    @Override
    public void connect() throws SQLException {
        // Database connection
        String url = "jdbc:mysql://localhost:3306/pooshop";
        String user = "root";
        String pass = "";
        this.connection = DriverManager.getConnection(url, user, pass);
    }

    @Override
    public void disconnect() throws SQLException {
        // Database disconnection
        if (connection != null) {
            connection.close();
        }
    }

    // Employee management
    @Override
    public Employee getEmployee(int id, String password) {
        Employee employee = null;

        // query to select employeeId and password from db
        String query = "select * from employee where id = ? AND password = ?";

        // set in ps with the select values
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // create an object passing the values to the Employee constructor
                    employee = new Employee(rs.getInt(1), rs.getString(2));
                }
            }
        } catch (SQLException e) {
            // in case of SQL exception, print the stack trace
            e.printStackTrace();
        }
        return employee;
    }

    // Product management
    @Override
    public Product getProduct(int id) {
        Product product = null;

        // Query to select product from the database
        String query = "SELECT * FROM products WHERE id = ?";

        // Set a prepared statement with the select values
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Product(
                        rs.getString("name"),
                        rs.getDouble("wholesalerPrice"),
                        rs.getBoolean("available"),
                        rs.getInt("stock")
                    );
                    product.setId(rs.getInt("id"));
                    product.setPublicPrice(new Amount(rs.getDouble("publicPrice")));
                }
            }
            // In case of SQL exception, print the stack trace
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public ArrayList<Product> getInventory() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean writeInventory(ArrayList<Product> inventory) {
        // TODO Auto-generated method stub
        return false;
    }
}
