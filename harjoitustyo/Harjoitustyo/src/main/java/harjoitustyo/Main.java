/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo;

import harjoitustyo.domain.*;
import harjoitustyo.dao.*;
import harjoitustyo.presentation.Ui;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {

    private AppUserManagerDao userManager;
    private EmployeeManagerDao employeeManager;
    private ClientManagerDao clientManager;
    private AssignmentManagerDao assignmentManager;
    private ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(Main.class);
        userManager = springContext.getBean(AppUserManagerDB.class);
        employeeManager = springContext.getBean(EmployeeManagerDB.class);
        clientManager = springContext.getBean(ClientManagerDB.class);
        assignmentManager = springContext.getBean(AssignmentManagerDB.class);
    }

    @Override
    public void stop() throws Exception {
        springContext.stop();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // Lisää tastikäyttäjiä ja työntekijöitä.
        AppUser admin = new AppUser("Admin", "Password", "Admin");
        AppUser regular = new AppUser("Employee", "Password", "Employee");
        userManager.add(admin);
        userManager.add(regular);
        employeeManager.add(new Employee("make", "makenen", "050555666777", "make@makenen.com", "112233-0553", "kotitie 1A"));
        userManager.add(new AppUser("make", "Password", "Employee"));
        
        //käynnistä interface
        Ui ui = new Ui(userManager, employeeManager, clientManager, assignmentManager, primaryStage);
        ui.start();
    }
}
