package model;

import main.Payable;

public class Client extends Person implements Payable {
    private final double BALANCE = 50.00;
    private Amount balance;

    // Constructor
    public Client(String name) {
        super(name);
        this.balance = new Amount(BALANCE);
    }

    // Payable interface implementation
    @Override
    public boolean pay(Amount totalAmount) {
        double amountToPay = totalAmount.getValue();
        if (balance.getAmount() >= amountToPay) {
            balance.subtract(new Amount(amountToPay));
            return true; 
        } else {
            return false; 
        }
    }

    // Getters
    public double getBalance() {
        return balance.getAmount();
    }

    public String getName() {
        return name;
    }
}
