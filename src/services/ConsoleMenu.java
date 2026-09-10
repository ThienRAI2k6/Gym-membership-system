package services;

import java.util.List;
import java.util.Scanner;

import exceptions.InvalidMembershipException;
import factories.MembershipFactory;
import models.Member;
import models.Membership;
import models.Person;
import models.Trainer;
import strategies.DiscountStrategy;
import strategies.ElderlyDiscount;
import strategies.NoDiscount;
import strategies.StudentDiscount;

public class ConsoleMenu {
    private final GymSystem gymSystem;
    private final Scanner scanner;

    public ConsoleMenu(GymSystem gymSystem) {
        this.gymSystem = gymSystem;
        this.scanner = new Scanner(System.in);
    }

    public ConsoleMenu() {
        this(new GymSystem());
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            String choice = readLine("👉 Nhập lựa chọn của bạn: ");
            switch (choice) {
                case "1":
                    handleMemberMenu();
                    break;
                case "2":
                    handleTrainerMenu();
                    break;
                case "3":
                    handleMembershipRegistration();
                    break;
                case "4":
                    handleCheckIn();
                    break;
                case "5":
                    handleDisplayAllPeople();
                    break;
                case "6":
                    handleRunDemo();
                    break;
                case "0":
                    System.out.println("\n=========================================");
                    System.out.println("   CẢM ƠN BẠN ĐÃ SỬ DỤNG HỆ THỐNG GYM!   ");
                    System.out.println("            HẸN GẶP LẠI!                 ");
                    System.out.println("=========================================\n");
                    exit = true;
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ. Vui lòng thử lại!");
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n=========================================");
        System.out.println("       HỆ THỐNG QUẢN LÝ PHÒNG GYM        ");
        System.out.println("=========================================");
        System.out.println("1. 👤 Quản lý Hội viên");
        System.out.println("2. 🏋️ Quản lý Huấn luyện viên");
        System.out.println("3. 💳 Đăng ký / Mua gói tập cho Hội viên");
        System.out.println("4. 📲 Quẹt thẻ điểm danh (Check-in)");
        System.out.println("5. 📋 Xem danh sách toàn bộ Nhân sự / Hội viên");
        System.out.println("6. 🚀 Chạy Demo thử nghiệm hệ thống");
        System.out.println("0. 🚪 Thoát chương trình");
        System.out.println("=========================================");
    }

    // --- 1. QUẢN LÝ HỘI VIÊN ---
    private void handleMemberMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- QUẢN LÝ HỘI VIÊN ---");
            System.out.println("1. Thêm hội viên mới");
            System.out.println("2. Xem danh sách hội viên");
            System.out.println("3. Tìm kiếm hội viên theo Mã HV");
            System.out.println("4. Cập nhật chỉ số cơ thể (Chiều cao / Cân nặng / BMI)");
            System.out.println("0. Quay lại menu chính");

            String choice = readLine("👉 Nhập lựa chọn: ");
            switch (choice) {
                case "1":
                    addMember();
                    break;
                case "2":
                    listMembers();
                    break;
                case "3":
                    findMember();
                    break;
                case "4":
                    updateMemberBodyMetrics();
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }

    private void addMember() {
        System.out.println("\n--- THÊM HỘI VIÊN MỚI ---");
        String id = readLine("Mã hội viên (VD: M01): ");
        if (gymSystem.findPersonById(id) != null) {
            System.out.println("❌ Lỗi: Mã " + id + " đã tồn tại trong hệ thống!");
            return;
        }

        String name = readLine("Họ và tên: ");
        String phone = readLine("Số điện thoại: ");
        String email = readLine("Email: ");

        double weight = readDouble("Cân nặng (kg) [Nhập 0 nếu bỏ qua]: ", 0, 300);
        double height = readDouble("Chiều cao (cm) [Nhập 0 nếu bỏ qua]: ", 0, 250);

        try {
            Member member;
            if (weight > 0 && height > 0) {
                member = new Member(id, name, phone, email, weight, height);
            } else {
                member = new Member(id, name, phone, email);
            }
            gymSystem.addPerson(member);
            System.out.println("✅ Thêm hội viên " + name + " (Mã: " + id + ") thành công!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Lỗi dữ liệu nhập: " + e.getMessage());
        }
    }

    private void listMembers() {
        List<Member> members = gymSystem.getPersonsByType(Member.class);
        System.out.println("\n================ DANH SÁCH HỘI VIÊN ================");
        if (members.isEmpty()) {
            System.out.println("Chưa có hội viên nào trong hệ thống.");
        } else {
            for (Member m : members) {
                m.displayInfo();
            }
        }
    }

    private void findMember() {
        String id = readLine("\nNhập mã hội viên cần tìm: ");
        Member member = gymSystem.findMemberById(id);
        if (member != null) {
            System.out.println("\n✅ Tìm thấy hội viên:");
            member.displayInfo();
        } else {
            System.out.println("❌ Không tìm thấy hội viên có mã: " + id);
        }
    }

    private void updateMemberBodyMetrics() {
        String id = readLine("\nNhập mã hội viên cần cập nhật chỉ số cơ thể: ");
        Member member = gymSystem.findMemberById(id);
        if (member == null) {
            System.out.println("❌ Không tìm thấy hội viên có mã: " + id);
            return;
        }

        double weight = readDouble("Nhập cân nặng mới (kg): ", 1, 300);
        double height = readDouble("Nhập chiều cao mới (cm): ", 51, 250);

        try {
            member.setWeight(weight);
            member.setHeight(height);
            System.out.println("✅ Cập nhật thành công!");
            System.out.printf("Chỉ số BMI mới: %.1f | Thể trạng: %s\n", member.calculateBMI(), member.getBMIStatus());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Lỗi: " + e.getMessage());
        }
    }

    // --- 2. QUẢN LÝ HUẤN LUYỆN VIÊN ---
    private void handleTrainerMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- QUẢN LÝ HUẤN LUYỆN VIÊN ---");
            System.out.println("1. Thêm huấn luyện viên mới");
            System.out.println("2. Xem danh sách huấn luyện viên");
            System.out.println("3. Tìm kiếm huấn luyện viên theo Mã HLV");
            System.out.println("0. Quay lại menu chính");

            String choice = readLine("👉 Nhập lựa chọn: ");
            switch (choice) {
                case "1":
                    addTrainer();
                    break;
                case "2":
                    listTrainers();
                    break;
                case "3":
                    findTrainer();
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ!");
            }
        }
    }

    private void addTrainer() {
        System.out.println("\n--- THÊM HUẤN LUYỆN VIÊN MỚI ---");
        String id = readLine("Mã HLV (VD: T01): ");
        if (gymSystem.findPersonById(id) != null) {
            System.out.println("❌ Lỗi: Mã " + id + " đã tồn tại trong hệ thống!");
            return;
        }

        String name = readLine("Họ và tên: ");
        String phone = readLine("Số điện thoại: ");
        String email = readLine("Email: ");
        String specialty = readLine("Chuyên môn (VD: Gym, Yoga, Zumba): ");
        int exp = readInt("Số năm kinh nghiệm: ", 0, 50);
        double salary = readDouble("Lương cơ bản (VNĐ): ", 0, 1_000_000_000);

        try {
            Trainer trainer = new Trainer(id, name, phone, email, specialty, exp, salary);
            gymSystem.addPerson(trainer);
            System.out.println("✅ Thêm HLV " + name + " (Mã: " + id + ") thành công!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Lỗi dữ liệu nhập: " + e.getMessage());
        }
    }

    private void listTrainers() {
        List<Trainer> trainers = gymSystem.getPersonsByType(Trainer.class);
        System.out.println("\n================ DANH SÁCH HUẤN LUYỆN VIÊN ================");
        if (trainers.isEmpty()) {
            System.out.println("Chưa có huấn luyện viên nào trong hệ thống.");
        } else {
            for (Trainer t : trainers) {
                t.displayInfo();
            }
        }
    }

    private void findTrainer() {
        String id = readLine("\nNhập mã HLV cần tìm: ");
        Trainer trainer = gymSystem.findTrainerById(id);
        if (trainer != null) {
            System.out.println("\n✅ Tìm thấy huấn luyện viên:");
            trainer.displayInfo();
        } else {
            System.out.println("❌ Không tìm thấy HLV có mã: " + id);
        }
    }

    // --- 3. ĐĂNG KÝ / MUA GÓI TẬP ---
    private void handleMembershipRegistration() {
        System.out.println("\n--- ĐĂNG KÝ / MUA GÓI TẬP ---");
        String memberId = readLine("Nhập mã hội viên mua thẻ: ");
        Member member = gymSystem.findMemberById(memberId);

        if (member == null) {
            System.out.println("❌ Không tìm thấy hội viên có mã: " + memberId);
            return;
        }

        System.out.println("\n1. Chọn loại gói tập:");
        System.out.println("   [1] BASIC");
        System.out.println("   [2] PREMIUM");
        String typeChoice = readLine("👉 Chọn (1/2): ");
        String type = typeChoice.equals("2") ? "PREMIUM" : "BASIC";

        System.out.println("\n2. Chọn thời hạn gói:");
        System.out.println("   [1] 1 Ngày (1_DAY)");
        System.out.println("   [2] 1 Tháng (1_MONTH)");
        System.out.println("   [3] 3 Tháng (3_MONTHS)");
        System.out.println("   [4] 6 Tháng (6_MONTHS)");
        System.out.println("   [5] 1 Năm (1_YEAR)");
        String durChoice = readLine("👉 Chọn (1-5): ");
        String duration;
        switch (durChoice) {
            case "1": duration = "1_DAY"; break;
            case "2": duration = "1_MONTH"; break;
            case "3": duration = "3_MONTHS"; break;
            case "4": duration = "6_MONTHS"; break;
            case "5": duration = "1_YEAR"; break;
            default:
                System.out.println("❌ Lựa chọn thời hạn không hợp lệ!");
                return;
        }

        System.out.println("\n3. Chọn chính sách giảm giá:");
        System.out.println("   [1] Không giảm giá");
        System.out.println("   [2] Sinh viên (Giảm 20%)");
        System.out.println("   [3] Người cao tuổi (Giảm 30%)");
        String discountChoice = readLine("👉 Chọn (1-3): ");
        DiscountStrategy discount;
        switch (discountChoice) {
            case "2":
                discount = new StudentDiscount();
                break;
            case "3":
                discount = new ElderlyDiscount();
                break;
            default:
                discount = new NoDiscount();
                break;
        }

        try {
            Membership membership = MembershipFactory.createMembership(type, duration, discount);
            member.setMembership(membership);

            System.out.println("\n================ THÔNG TIN HÓA ĐƠN GÓI TẬP ================");
            System.out.println("Hội viên sở hữu: " + member.getName() + " (Mã: " + member.getId() + ")");
            System.out.println("Loại gói: " + type + " | Thời hạn: " + duration);
            System.out.printf("Thành tiền: %,.0f k VNĐ\n", membership.calculateFinalPrice());
            System.out.println("Ngày kích hoạt: " + membership.getStartDate());
            System.out.println("Ngày hết hạn: " + membership.getExpiryDate());
            System.out.println("===========================================================");
            System.out.println("✅ Đăng ký gói tập thành công!");

        } catch (InvalidMembershipException e) {
            System.out.println(e.getMessage());
        }
    }

    // --- 4. QUẸT THẺ ĐIỂM DANH ---
    private void handleCheckIn() {
        System.out.println("\n--- QUẸT THẺ ĐIỂM DANH (CHECK-IN) ---");
        String id = readLine("Nhập mã hội viên quẹt thẻ: ");
        Member member = gymSystem.findMemberById(id);

        if (member == null) {
            System.out.println("❌ Không tìm thấy hội viên có mã: " + id);
            return;
        }

        System.out.println("🔄 Đang xử lý quẹt thẻ...");
        member.checkIn();
    }

    // --- 5. HIỂN THỊ TOÀN BỘ DANH SÁCH ---
    private void handleDisplayAllPeople() {
        List<Person> people = gymSystem.getPeopleList();
        System.out.println("\n================ TOÀN BỘ DANH SÁCH NHÂN SỰ & HỘI VIÊN ================");
        if (people.isEmpty()) {
            System.out.println("Hệ thống chưa có dữ liệu.");
        } else {
            for (Person p : people) {
                p.displayInfo();
            }
        }
    }

    // --- 6. CHẠY DEMO HỆ THỐNG ---
    private void handleRunDemo() {
        System.out.println("\n--- KÍCH HOẠT KỊCH BẢN DEMO HỆ THỐNG ---");
        try {
            gymSystem.runDemo();
        } catch (InvalidMembershipException e) {
            System.out.println("❌ Lỗi khi chạy Demo: " + e.getMessage());
        }
    }

    // --- UTILITY METHODS CHO NHẬP DỮ LIỆU TỪ TERMINAL ---
    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("❌ Vui lòng nhập số nguyên trong khoảng [%d, %d].\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("❌ Nhập không hợp lệ. Vui lòng nhập một số nguyên!");
            }
        }
    }

    private double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("❌ Vui lòng nhập số trong khoảng [%.1f, %.1f].\n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("❌ Nhập không hợp lệ. Vui lòng nhập số!");
            }
        }
    }

    // Phương thức main cho phép khởi chạy trực tiếp ConsoleMenu
    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();
        menu.start();
    }
}