/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.presentation;

import harjoitustyo.domain.AppUser;
import harjoitustyo.dao.AppUserManagerDao;
import harjoitustyo.dao.AppUserManagerMemory;
import harjoitustyo.domain.Employee;
import harjoitustyo.dao.EmployeeManagerDao;
import harjoitustyo.dao.EmployeeManagerMemory;
import harjoitustyo.presentation.EmployeeView;
import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Roni
 */
@Component
public class Ui extends Application {

    private AppUserManagerDao userManager;
    private EmployeeManagerDao employeeManager;
    private BorderPane root;
    private Scene scene;
    private AppUser user;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Start manager logic
        userManager = new AppUserManagerMemory();
        AppUser admin = new AppUser("Admin", "Password", "Admin");
        AppUser regular = new AppUser("Employee", "Password", "Employee");
        userManager.add(admin);
        userManager.add(regular);
        employeeManager = new EmployeeManagerMemory();
        // tää outo tuplaroolitus ei tule toimimaan. fixaa.
        employeeManager.add(new Employee("make", "makenen", "050555666777", "make@makenen.com", new ArrayList<>(), "112233-0553"));
        userManager.add(new AppUser("make", "Password", "Employee"));

        // Graphics
        // Create the root
        root = new BorderPane();
        root.setPadding(new Insets(10, 5, 5, 5));  // top, right, bottom, left

        //Top Box
        createLoginView();

        // create the scene
        scene = new Scene(root, 1000, 1000);
        primaryStage.setTitle("Employee Management App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createLoginView() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 5, 5, 5));  // top, right, bottom, left
        hbox.setSpacing(10);
        Text errorField = new Text("");
        Button loginBtn = new Button("Login");
        loginBtn.setDefaultButton(true);
        TextField nameField = new TextField();
        PasswordField pwField = new PasswordField();
        hbox.getChildren().add(nameField);
        hbox.getChildren().add(pwField);
        hbox.getChildren().add(loginBtn);
        hbox.getChildren().add(errorField);
        root.setTop(hbox);
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (userManager.checkLogin(nameField.getText(), pwField.getText())) {
                    errorField.setText("Successful login");
                    root.getChildren().remove(hbox);
                    createLogoutView();
                    user = userManager.getUser(nameField.getText());
                    if (user.getAuthorization().equals("Admin")) {
                        createAdminView();
                    }
                } else {
                    errorField.setText("Wrong username or password");
                }
            }
        });
    }

    private void createLogoutView() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 5, 20, 5));  // top, right, bottom, left
        hbox.setSpacing(10);
        Text errorField = new Text("");
        Button logoutBtn = new Button("Logout");
        hbox.getChildren().add(errorField);
        hbox.getChildren().add(logoutBtn);
        root.setTop(hbox);
        scene.setRoot(root);
        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                errorField.setText("Successful logout");
                user = null;
                root.getChildren().remove(hbox);
                root.getChildren().clear();
                createLoginView();
            }
        });
    }

    private void createAdminView() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 5, 5, 5));  // top, right, bottom, left
        vbox.setSpacing(10);
        Button openEmployeeView = new Button("Employees");
        Button openCustomerView = new Button("Customers");
        Button openAssignmentView = new Button("Assignments");
        vbox.getChildren().add(openEmployeeView);
        vbox.getChildren().add(openCustomerView);
        vbox.getChildren().add(openAssignmentView);
        root.setLeft(vbox);
        openEmployeeView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().remove(root.getCenter());
                EmployeeView employeeView = new EmployeeView(root, employeeManager);
                employeeView.createEmployeeView();                       
            }
        });
        openCustomerView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().remove(root.getCenter());
                createCustomerView();
            }
        });
        openAssignmentView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().remove(root.getCenter());
                createAssignmentView();
            }
        });
    }


    private void createCustomerView() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void createAssignmentView() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
