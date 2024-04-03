package model;

import main.Payable;

public class Client extends Person implements Payable {
	private final int MEMBER_ID = 456;
	private final double BALANCE = 50.00;
	private int memberId;
	private Amount balance;

	public boolean pay(double amount) {
	    if (balance.getAmount() >= amount) {
	        balance.subtract(new Amount(amount));
	        return true;
	    } else {
	        return false;
	    }
	}

}
