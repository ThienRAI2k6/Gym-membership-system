package models;

import states.MembershipState;
import states.ActiveState;
import strategies.DiscountStrategy;

public class Membership {
    private double basePrice;
    private MembershipState state;
    private DiscountStrategy discountStrategy;

    public Membership(double basePrice, DiscountStrategy discountStrategy) {
        this.basePrice = basePrice;
        this.discountStrategy = discountStrategy;
        this.state = new ActiveState(); // Mặc định khi mua là Active
    }

    public void setState(MembershipState state) {
        this.state = state;
    }

    public double calculateFinalPrice() {
        return discountStrategy.applyDiscount(basePrice);
    }

    public void checkIn(Member member) {
        state.handleCheckIn(member, this);
    }
}