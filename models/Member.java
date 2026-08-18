package models;

public class Member extends Person {
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
    
    public void checkIn() {
        if (membership != null) {
            membership.checkIn(this);
        } else {
            System.out.println("No membership found for " + name);
        }
    }
}