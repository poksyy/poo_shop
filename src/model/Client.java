package model;

import main.Payable;

public class Client extends Person implements Payable {
    private final int MEMBER_ID = 456;
    private final double BALANCE = 50.00;
    private int memberId;
    private Amount balance;

    public Client(String name) {
        super(name);
        this.memberId = MEMBER_ID;
        this.balance = new Amount(BALANCE);
    }

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


    public double getBalance() {
        return balance.getAmount();
    }

    public String getName() {
        return name;
    }
}
