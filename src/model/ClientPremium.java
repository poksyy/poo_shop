package model;

public class ClientPremium extends Client {
    private double points = 0;

    // Constructor
    public ClientPremium(String name) {
        super(name);
    }

    // Getters y Setters
    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    @Override
    public boolean pay(Amount totalAmount) {
        boolean paymentStatus = super.pay(totalAmount);
        
        int earnedPoints = (int) (totalAmount.getValue() / 10);
        this.points += earnedPoints;
        System.out.println("Points earned: " + earnedPoints);

        return paymentStatus;
    }
}
