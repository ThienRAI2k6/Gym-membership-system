package services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.InvalidMembershipException;
import factories.MembershipFactory;
import models.Member;
import models.Membership;
import models.Person;
import models.Trainer;
import states.ExpiredState;
import strategies.NoDiscount;
import strategies.StudentDiscount;

public class GymSystem {
    private List<Person> peopleList = new ArrayList<>();

    public void addPerson(Person person) {
        peopleList.add(person);
    }

    public List<Person> getPeopleList() {
        return peopleList;
    }

    public <T extends Person> List<T> getPersonsByType(Class<T> type) {
        List<T> result = new ArrayList<>();
        for (Person p : peopleList) {
            if (type.isInstance(p)) {
                result.add(type.cast(p));
            }
        }
        return result;
    }

    public Person findPersonById(String id) {
        for (Person p : peopleList) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public Member findMemberById(String id) {
        for (Person p : peopleList) {
            if (p instanceof Member && p.getId().equalsIgnoreCase(id)) {
                return (Member) p;
            }
        }
        return null;
    }

    public Trainer findTrainerById(String id) {
        for (Person p : peopleList) {
            if (p instanceof Trainer && p.getId().equalsIgnoreCase(id)) {
                return (Trainer) p;
            }
        }
        return null;
    }

    // Đã thêm throws InvalidMembershipException
    public void runDemo() throws InvalidMembershipException {
        System.out.println("=== DEMO TÍNH HẠN THẺ TẬP & PHÂN LOẠI GÓI ===");

        // 1. Tạo gói tập BASIC 1 tháng và PREMIUM 1 năm
        Member m1 = new Member("M01", "Nguyen Van A", "0963", "ducthien24@gmail.com");
        Membership basicMonthly = MembershipFactory.createMembership("BASIC", "1_MONTH", new StudentDiscount());
        m1.setMembership(basicMonthly);
        addPerson(m1); // BỔ SUNG: Thêm m1 vào hệ thống quản lý

        Member m2 = new Member("M02", "Tran Thi B", "09633", "duct2ien24@gmail.com");
        Membership premiumYearly = MembershipFactory.createMembership("PREMIUM", "1_YEAR", new NoDiscount());
        m2.setMembership(premiumYearly);
        addPerson(m2); // BỔ SUNG: Thêm m2 vào hệ thống quản lý

        // 2. Hiển thị thông tin kích hoạt và ngày hết hạn
        System.out.println("Hội viên: " + m1.getName());
        System.out.println("Gói đăng ký: BASIC - 1 Tháng");
        System.out.println("Giá thanh toán: " + basicMonthly.calculateFinalPrice() + "k");
        System.out.println("Ngày kích hoạt: " + basicMonthly.getStartDate());
        System.out.println("Ngày hết hạn: " + basicMonthly.getExpiryDate());

        System.out.println("-----------------------------");

        System.out.println("Hội viên: " + m2.getName());
        System.out.println("Gói đăng ký: PREMIUM - 1 Năm");
        System.out.println("Giá thanh toán: " + premiumYearly.calculateFinalPrice() + "k");
        System.out.println("Ngày kích hoạt: " + premiumYearly.getStartDate());
        System.out.println("Ngày hết hạn: " + premiumYearly.getExpiryDate());

        System.out.println("-----------------------------");

        // 3. Quẹt thẻ khi còn hạn
        System.out.println("[Thử nghiệm] Hội viên " + m1.getName() + " quẹt thẻ hôm nay:");
        m1.checkIn();

        // 4. Giả lập trường hợp quá hạn sau 2 tháng đối với m1
        LocalDate futureDate = LocalDate.now().plusMonths(2);
        System.out.println("\n[Mô phỏng] Thời gian trôi đến ngày: " + futureDate);
        if (basicMonthly.isExpired(futureDate)) {
            basicMonthly.setState(new ExpiredState());
        }
        m1.checkIn();

        // 5. Thử nghiệm bắt lỗi: Khách cố tình mua gói PREMIUM 1 ngày
        System.out.println("\n[Thử nghiệm] Khách mua gói PREMIUM 1 ngày:");
        try {
            // Bỏ biến invalidCard, chỉ gọi phương thức để test ngoại lệ
            MembershipFactory.createMembership("PREMIUM", "1_DAY", new NoDiscount());
        } catch (InvalidMembershipException e) {
            System.out.println(e.getMessage());
        }
    }
}