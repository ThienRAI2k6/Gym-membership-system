import java.util.ArrayList;
import java.util.List;

// ==========================================
// 1. EXCEPTIONS (Xử lý ngoại lệ - 10đ)
// ==========================================
class InvalidMembershipException extends Exception {
    public InvalidMembershipException(String message) {
        super(message);
    }
}

// ==========================================
// 2. INHERITANCE & ABSTRACTION (Kế thừa & Trừu tượng - 20đ)
// ==========================================
abstract class Person {
    protected String id;
    protected String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public abstract void displayInfo(); // Polymorphism
}

class Trainer extends Person {
    private String specialty;

    public Trainer(String id, String name, String specialty) {
        super(id, name);
        this.specialty = specialty;
    }

    @Override
    public void displayInfo() {
        System.out.println("Trainer: " + name + " | Specialty: " + specialty);
    }
}

class Member extends Person {
    private Membership membership;

    public Member(String id, String name) {
        super(id, name);
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public Membership getMembership() {
        return membership;
    }

    @Override
    public void displayInfo() {
        System.out.println("Member: " + name + " | ID: " + id);
    }
    
    // Gọi hành vi của State Pattern
    public void checkIn() {
        if (membership != null) {
            membership.checkIn(this);
        } else {
            System.out.println("No membership found for " + name);
        }
    }
}

// ==========================================
// 3. STRATEGY PATTERN (Tính toán giảm giá - 10đ)
// ==========================================
interface DiscountStrategy {
    double applyDiscount(double price);
}

class NoDiscount implements DiscountStrategy {
    public double applyDiscount(double price) { return price; }
}

class StudentDiscount implements DiscountStrategy {
    public double applyDiscount(double price) { return price * 0.8; } // Giảm 20%
}

// ==========================================
// 4. STATE PATTERN (Trạng thái thẻ - 10đ)
// ==========================================
interface MembershipState {
    void handleCheckIn(Member member, Membership context);
}

class ActiveState implements MembershipState {
    public void handleCheckIn(Member member, Membership context) {
        System.out.println("✅ Access Granted! Welcome to the gym, " + member.name);
    }
}

class ExpiredState implements MembershipState {
    public void handleCheckIn(Member member, Membership context) {
        System.out.println("❌ Access Denied! " + member.name + ", your membership has expired.");
        // Throw exception if needed
    }
}

// ==========================================
// LỚP MEMBERSHIP KẾT HỢP VỚI STATE & STRATEGY
// ==========================================
class Membership {
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

// ==========================================
// 5. FACTORY PATTERN (Tạo thẻ hội viên - 10đ)
// ==========================================
class MembershipFactory {
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

// ==========================================
// 6. COLLECTIONS & ENCAPSULATION (Quản lý dữ liệu - 20đ)
// ==========================================
class GymSystem {
    private List<Member> members;
    private List<Trainer> trainers;

    public GymSystem() {
        this.members = new ArrayList<>();
        this.trainers = new ArrayList<>();
    }

    public void addMember(Member member) {
        members.add(member);
        System.out.println("Added new member: " + member.name);
    }

    // Demo hoạt động toàn hệ thống
    public void runDemo() {
        System.out.println("--- BẮT ĐẦU DEMO HỆ THỐNG ---");
        
        // 1. Tạo khách hàng
        Member m1 = new Member("M01", "Nguyen Van A");
        
        // 2. Dùng Factory & Strategy để mua thẻ Premium, áp dụng mã Sinh viên
        Membership premiumStudent = MembershipFactory.createMembership("PREMIUM", new StudentDiscount());
        m1.setMembership(premiumStudent);
        addMember(m1);
        
        System.out.println("Giá gốc: 1000.0 | Giá phải trả (đã giảm): " + premiumStudent.calculateFinalPrice());
        
        // 3. Demo State: Quẹt thẻ khi đang Active
        m1.checkIn();
        
        // 4. Demo State: Giả sử thẻ hết hạn
        System.out.println("...1 tháng sau...");
        m1.getMembership().setState(new ExpiredState());
        m1.checkIn(); // Báo lỗi
    }
}

// ==========================================
// CHẠY CHƯƠNG TRÌNH
// ==========================================
public class Main {
    public static void main(String[] args) {
        GymSystem gym = new GymSystem();
        gym.runDemo();
    }
}