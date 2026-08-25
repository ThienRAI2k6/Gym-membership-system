package models;

import java.time.LocalDate;

public class Member extends Person {
    private Membership membership;
    
    private double weight; 
    private double height; 
    private LocalDate joinDate; // Ngày gia nhập để cấp kỷ niệm chương, tri ân

    // Constructor 1: Khởi tạo cơ bản 
    public Member(String id, String name, String phoneNumber, String email) {
        super(id, name, phoneNumber, email);
        this.joinDate = LocalDate.now(); // Tự động lấy ngày hiện tại của hệ thống khi tạo mới
    }

    //Constructor 2: Khởi tạo đầy đủ kèm chỉ số cơ thể 
    public Member(String id, String name, String phoneNumber, String email, double weight, double height) {
        super(id, name, phoneNumber, email);
        this.setWeight(weight);
        this.setHeight(height);
        this.joinDate = LocalDate.now();
    }

    // --- QUẢN LÝ CHỈ SỐ CƠ THỂ & VALIDATION ---
    public void setWeight(double weight) {
        if (weight > 0 && weight <= 300) { // Gym validation
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Lỗi: Cân nặng không hợp lệ.");
        }
    }

    public void setHeight(double height) {
        if (height > 50 && height <= 250) { // Gym validation
            this.height = height;
        } else {
            throw new IllegalArgumentException("Lỗi: Chiều cao không hợp lệ.");
        }
    }

    public double getWeight() { return weight; }
    public double getHeight() { return height; }
    public LocalDate getJoinDate() { return joinDate; }

    
    // Tính toán chỉ số khối cơ thể (BMI)
    public double calculateBMI() {
        if (height == 0) return 0;
        double heightInMeter = height / 100;
        return weight / (heightInMeter * heightInMeter);
    }

    // Phân loại thể trạng tự động dựa trên BMI
    public String getBMIStatus() {
        double bmi = calculateBMI();
        if (bmi == 0) return "Chưa có dữ liệu";
        if (bmi < 18.5) return "Thiếu cân (Cần tăng cơ)";
        if (bmi < 24.9) return "Bình thường (Duy trì vóc dáng)";
        if (bmi < 29.9) return "Thừa cân (Cần Cardio giảm mỡ)";
        return "Béo phì (Cần PT tư vấn đặc biệt)";
    }

    // Kiểm tra nhanh xem hội viên có đủ điều kiện tập không
    public boolean hasActiveMembership() {
        return this.membership != null && this.membership.isActive();
    }

    // --- QUẢN LÝ THẺ HỘI VIÊN ---
    public void setMembership(Membership membership) {
        if (membership == null) {
            System.out.println("Cảnh báo: Đang gán thẻ rỗng cho hội viên " + this.getName());
        }
        this.membership = membership;
    }

    public Membership getMembership() {
        return membership;
    }

    @Override
    public void displayInfo() {
        System.out.println("HỒ SƠ HỘI VIÊN: " + this.getName() + " (Mã HV: " + this.getId() + ")");
        System.out.println("Liên hệ: " + (this.getPhoneNumber() != null ? this.getPhoneNumber() : "Trống") + " | " + (this.getEmail() != null ? this.getEmail() : "Trống"));
        System.out.println("Ngày tham gia: " + this.joinDate);
        
        // Chỉ hiển thị BMI nếu đã nhập đủ chiều cao, cân nặng
        if (weight > 0 && height > 0) {
            System.out.printf("Thể trạng: %.1f kg | %.1f cm -> BMI: %.1f [%s]\n", weight, height, calculateBMI(), getBMIStatus());
        }

        System.out.print("Trạng thái thẻ: ");
        if (membership != null) {
            // Giả định bạn đã thêm hàm getType() và isActive() ở lớp Membership như thiết kế trước
            String status = membership.isActive() ? "Đang hoạt động" : "Đã hết hạn";
            System.out.println(membership.getType() + " - " + status);
        } else {
            System.out.println("CHƯA ĐĂNG KÝ MUA THẺ");
        }
        System.out.println("========================================\n");
    }
    
    public void checkIn() {
        if (membership != null) {
            membership.checkIn(this);
        } else {
            System.out.println("Hệ thống từ chối: " + this.getName() + " chưa sở hữu thẻ Gym nào! Vui lòng mua thẻ tại Lễ tân.");
        }
    }
}
