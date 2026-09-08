package strategies;

public class ElderlyDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) { 
        return price * 0.8; // Giảm 20%
    } 
}