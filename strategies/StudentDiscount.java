package strategies;

public class StudentDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) { 
        return price * 0.8; // Giảm 20%
    } 
}