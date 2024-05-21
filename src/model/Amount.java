package model;

public class Amount  {
    private double value;
    private final String currency = "€";
    
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }
    
 
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

    public Amount multiply(double multiplier) {
        return new Amount(this.value * multiplier);
    }
    
    @Override
    public String toString() {
        return value + currency;
    }

    public Amount subtract(Amount amount) {
        return new Amount(this.value - amount.getValue());
    }

}
