package harjoitustyo;
import java.util.Scanner;

/**
 *
 * @author Roni
 */
public class Main {
    public static void main(String[] args) {
        User admin = new User ("Admin", "Password", "Admin");
        User regular = new User ("Employee", "Password", "Employee");
        UserManagerDao usermanager = new UserManagerMemory();
        System.out.println(admin);
        System.out.println(regular);
        usermanager.add(admin);
        usermanager.add(regular);
        Scanner scanner = new Scanner(System.in);
        InterfaceDao textInterface = new TextInterface(scanner, usermanager);
        textInterface.mainloop();
    }
}
