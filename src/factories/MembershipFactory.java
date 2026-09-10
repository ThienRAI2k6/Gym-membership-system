package factories;

import exceptions.InvalidMembershipException;
import models.Membership;
import strategies.DiscountStrategy;

public class MembershipFactory {
    
    // Đã thêm throws InvalidMembershipException
    public static Membership createMembership(String type, String duration, DiscountStrategy discount) 
            throws InvalidMembershipException {
        double basePrice = 0.0;
        int durationDays = 0;
        int durationMonths = 0;
        
        // 1. Xác định giá gốc theo gói thời gian của BASIC
        switch (duration.toUpperCase()) {
            case "1_DAY":
                if (type.equalsIgnoreCase("PREMIUM")) {
                    throw new InvalidMembershipException("❌ Lỗi: Gói PREMIUM không áp dụng cho thời hạn 1 ngày.");
                }
                basePrice = 100.0; // 100k
                durationDays = 1;
                break;
            case "1_MONTH":
                basePrice = 500.0; // 500k
                durationMonths = 1;
                break;
            case "3_MONTHS":
                basePrice = 1000.0; // 1tr
                durationMonths = 3;
                break;
            case "6_MONTHS":
                basePrice = 1500.0; // 1tr5
                durationMonths = 6;
                break;
            case "1_YEAR":
                basePrice = 2000.0; // 2tr
                durationMonths = 12;
                break;
            default:
                throw new InvalidMembershipException("❌ Thời hạn không hợp lệ: " + duration);
        }

        // 2. Tính giá theo loại thẻ (BASIC / PREMIUM)
        if (type.equalsIgnoreCase("PREMIUM")) {
            basePrice += 1000.0;
        } else if (!type.equalsIgnoreCase("BASIC")) {
            throw new InvalidMembershipException("❌ Loại gói tập không hợp lệ: " + type);
        }

        // 3. Khởi tạo đối tượng Membership
        return new Membership(type ,basePrice, durationDays, durationMonths, discount);
    }
}