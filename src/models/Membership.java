package models;

import java.time.LocalDate;
import states.ActiveState;
import states.ExpiredState;
import states.MembershipState;
import strategies.DiscountStrategy;

public class Membership {
    private String type;
    private double basePrice;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private MembershipState state;
    private DiscountStrategy discountStrategy;

    public Membership(String type ,double basePrice, int durationDays,int durationMonths, DiscountStrategy discountStrategy) {
        this.type = type;
        this.basePrice = basePrice;
        this.discountStrategy = discountStrategy;
        this.startDate = LocalDate.now(); // Lấy ngày hiện tại làm ngày kích hoạt
        this.expiryDate = this.startDate.plusMonths(durationMonths).plusDays(durationDays);
        this.state = new ActiveState();
    }

    public String getType() {
        return this.type;
    }
    public boolean isActive() {
        if (this.state != null && this.state.getStatusName().equals("ACTIVE")) {
            return true;
        }
        return false;
    }

    public void setState(MembershipState state) {
        this.state = state;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    // Kiểm tra xem thẻ đã quá hạn so với ngày kiểm tra hay chưa
    public boolean isExpired(LocalDate checkDate) {
        return checkDate.isAfter(expiryDate);
    }

    public double calculateFinalPrice() {
        return discountStrategy.applyDiscount(basePrice);
    }

    public void checkIn(Member member) {
        // Tự động chuyển sang ExpiredState nếu ngày hiện tại đã quá ngày hết hạn
        if (isExpired(LocalDate.now())) {
            this.state = new ExpiredState();
        }
        state.handleCheckIn(member, this);
    }
}