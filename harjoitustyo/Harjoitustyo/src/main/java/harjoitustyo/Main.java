/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo;

import harjoitustyo.domain.*;
import harjoitustyo.dao.*;
import harjoitustyo.presentation.Ui;
import java.time.LocalDateTime;
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

        // Lisää tastikäyttäjiä, asiakkaita, hommia ja työntekijöitä, jos DB on nuketettu.
        if (userManager.getUsers().size() == 0) {
            AppUser admin = new AppUser("Admin", "Password", "Admin");
            userManager.add(admin);
            Employee employee = employeeManager.add(new Employee("Teemu", "Työntekijä", "0501234567", "teemutyontekija@gmail.com", "111133-5555", "Tyontekijankatu 1 a"));
            Client client = clientManager.add(new Client("Aatu", "Asiakas", "0507654321", "aatu.asiakas@gmail.com", "121212-1234", "Asiakkaantie 7 B 22"));
            Assignment assignment = new Assignment(client, employee, LocalDateTime.of(2019, 12, 24, 21, 00), LocalDateTime.of(2019, 12, 24, 22, 00), "Vie lahjoja ja laula joululauluja", client.getAddress(), client.getNumber(),  "", "Assigned");
            assignment = assignmentManager.add(assignment);
        }

        //käynnistä interface
        Ui ui = new Ui(userManager, employeeManager, clientManager, assignmentManager, primaryStage);
        ui.start();
    }
}
