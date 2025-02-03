package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Amount {
	private double value;
	private String currency = "€";

	// Constructors
	public Amount() {
	}

	public Amount(double newValue) {
		this.value = newValue;
	}

	public Amount(Amount totalAmount) {
		if (totalAmount != null) {
			this.value = totalAmount.getValue();
		} else {
			this.value = 0.0;
		}
	}

	// Getters and Setters
	// This annotation marks the getter method that provides the main value of the XML element.
	@XmlValue
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	// This annotation marks the 'currency' field as an attribute of the XML element.
	@XmlAttribute(name = "currency")
	public String getCurrency() {
		return currency;
	}

	// Additional methods
	public int getAmount() {
		return (int) value;
	}

	public Amount add(Amount amount) {
		if (amount != null) {
			double newValue = this.value + amount.getValue();
			return new Amount(newValue);
		} else {
			return new Amount(this.value);
		}
	}

	public Amount subtract(Amount amount) {
		return new Amount(this.value - amount.getValue());
	}

	public Amount multiply(double multiplier) {
		return new Amount(this.value * multiplier);
	}

	@Override
	public String toString() {
		return value + " " + currency;
	}
}
