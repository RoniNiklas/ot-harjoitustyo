/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo;

import java.util.Scanner;

/**
 *
 * @author Roni
 */
public class TextInterface {

    private Scanner scanner;
    private AppUserManagerDao manager;

    public TextInterface(Scanner scanner, AppUserManagerDao manager) {
        this.scanner = scanner;
        this.manager = manager;
    }

    public void mainloop() {
        boolean shouldRun = true;
        while (shouldRun) {
            paddedOutput("Insert Command: ");
            System.out.println("Login");
            System.out.println("Exit");
            String command = scanner.nextLine();
            switch (command.toLowerCase().trim()) {
                case "login": {
                    login();
                    break;
                }
                case "exit": {
                    shouldRun = false;
                    break;
                }
                default: {
                    System.out.println("That command was not recognized.");
                }
            }
        }
    }
    private void paddedOutput(String string) {
        System.out.println("");
        System.out.println(string);
        System.out.println("");
    }
    private void login() {
        paddedOutput("Username: ");
        String username = scanner.nextLine();
        paddedOutput("Password: ");
        String password = scanner.nextLine();
       AppUser user = manager.getUser(username);
        if (user != null) {
            boolean success = manager.checkLogin(username, password);
            if (success) {
                String authorization = user.getAuthorization();
                switch (authorization.toLowerCase().trim()) {
                    case "admin": {
                        adminLoop();
                        break;
                    }
                    case "regular": {
                        regularLoop();
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } else {
                paddedOutput("Wrong username or password");
            }
        } else {
            paddedOutput("Wrong username or password");
        }
    }

    private void adminLoop() {
        boolean shouldRun = true;
        while (shouldRun) {
            paddedOutput("Insert Command");
            System.out.println("DoAdminStuff");
            System.out.println("Logout");
            String command = scanner.nextLine();
            switch (command.toLowerCase().trim()) {
                case "doadminstuff": {
                    paddedOutput("Done!");
                    break;
                }
                case "logout": {
                    shouldRun = false;
                    break;
                }
                default: {
                    paddedOutput("That command was not recognized.");
                    break;
                }
            }
        }
    }

    private void regularLoop() {
        boolean shouldRun = true;
        while (shouldRun) {
            paddedOutput("Insert Command");
            System.out.println("DoRegularStuff");
            System.out.println("Logout");
            String command = scanner.nextLine();
            switch (command.toLowerCase().trim()) {
                case "doregularstuff": {
                    System.out.println("Done!");
                }
                case "logout": {
                    shouldRun = false;
                }
                default: {
                    paddedOutput("That command was not recognized.");
                }
            }
        }
    }
}
