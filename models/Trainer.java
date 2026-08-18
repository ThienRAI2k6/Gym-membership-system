package models;

public class Trainer extends Person {
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