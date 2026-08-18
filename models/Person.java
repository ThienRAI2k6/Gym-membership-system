package models;

public abstract class Person {
    protected String id;
    protected String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Bổ sung Getter để các Package khác (như States) có thể lấy tên
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public abstract void displayInfo(); // Polymorphism
}