package models;

public class Trainer extends Person {
    private String specialty;        // Chuyên môn (VD: Yoga, Aerobic, Bodybuilding)
    private int experienceYears;    
    private double baseSalary;     

    // Constructor: Khởi tạo đầy đủ thông tin HLV
    public Trainer(String id, String name, String phoneNumber, String email, String specialty, int experienceYears, double baseSalary) {

        super(id, name, phoneNumber, email);
        
        this.setSpecialty(specialty);
        this.setExperienceYears(experienceYears);
        this.setBaseSalary(baseSalary);
    }

    public String getSpecialty() { 
        return specialty; 
    }
    
    public void setSpecialty(String specialty) {
        if (specialty != null && !specialty.trim().isEmpty()) {
            this.specialty = specialty.trim(); 
        } else {
            throw new IllegalArgumentException("Lỗi: Chuyên môn của HLV không được để trống.");
        }
    }

    public int getExperienceYears() { 
        return experienceYears; 
    }
    
    public void setExperienceYears(int experienceYears) {
        if (experienceYears >= 0 && experienceYears <= 50) {
            this.experienceYears = experienceYears;
        } else {
            throw new IllegalArgumentException("Lỗi: Số năm kinh nghiệm không hợp lệ.");
        }
    }

    public double getBaseSalary() { 
        return baseSalary; 
    }
    
    public void setBaseSalary(double baseSalary) {
        if (baseSalary >= 0) {
            this.baseSalary = baseSalary;
        } else {
            throw new IllegalArgumentException("Lỗi: Lương cơ bản không thể là số âm.");
        }
    }
    
    // Hàm tính tổng lương
    public double calculateTotalSalary() {
        double bonusPerYear = 500000.0;
        return this.baseSalary + (this.experienceYears * bonusPerYear);
    }

    // --- HÀNH VI ĐA HÌNH ---
    @Override
    public void displayInfo() {
        System.out.println("HỒ SƠ HUẤN LUYỆN VIÊN: " + this.getName() + " (Mã HLV: " + this.getId() + ")");
        System.out.println("Liên hệ: " + (this.getPhoneNumber() != null ? this.getPhoneNumber() : "Trống") + " | " + (this.getEmail() != null ? this.getEmail() : "Trống"));
        System.out.println("Chuyên môn: " + this.specialty + " | Kinh nghiệm: " + this.experienceYears + " năm");
        System.out.printf("Lương cơ bản: %,.0f VNĐ | Tổng lương dự kiến: %,.0f VNĐ\n", this.baseSalary, this.calculateTotalSalary());
    }
}
