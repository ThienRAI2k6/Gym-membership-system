package strategies;

public class ElderlyDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double price) { 
        return price * 0.7; // Giảm 30%
    }
}
