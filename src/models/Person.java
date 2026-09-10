package models;

import java.util.Objects;

public abstract class Person {
    protected String id;
    protected String name;
    protected String phoneNumber;
    protected String email;

    // Khởi tạo đầy đủ thông tin
    public Person(String id, String name, String phoneNumber, String email) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID không được để trống.");
        }
        this.id = id;
        this.setName(name);
        this.phoneNumber = phoneNumber;
        this.setEmail(email);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Tên không được để trống.");
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber; // Có thể bổ sung Regex kiểm tra SĐT ở đây
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            if(email != null && !email.isEmpty()) {
                throw new IllegalArgumentException("Email không hợp lệ.");
            }
        }
    }

    // --- KIỂM SOÁT DỮ LIỆU TRÙNG LẶP ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id.equals(person.id); // ID là duy nhất để phân biệt mỗi thực thể
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public abstract void displayInfo(); 
}