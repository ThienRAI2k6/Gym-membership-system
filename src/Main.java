import services.ConsoleMenu;
import services.GymSystem;

public class Main {
    public static void main(String[] args) {
        GymSystem gymSystem = new GymSystem();
        ConsoleMenu menu = new ConsoleMenu(gymSystem);
        menu.start();
    }
}