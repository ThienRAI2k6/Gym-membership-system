package factories;

import models.Membership;
import strategies.DiscountStrategy;

public class MembershipFactory {
    public static Membership createMembership(String type, DiscountStrategy discount) {
        switch (type.toUpperCase()) {
            case "BASIC":
                return new Membership(500.0, discount); // 500k
            case "PREMIUM":
                return new Membership(1000.0, discount); // 1000k
            default:
                throw new IllegalArgumentException("Unknown membership type");
        }
    }
}