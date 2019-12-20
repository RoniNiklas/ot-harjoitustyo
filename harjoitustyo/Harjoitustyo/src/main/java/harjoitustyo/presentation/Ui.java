package harjoitustyo.presentation;

//Roni
import harjoitustyo.dao.AppUserManagerDao;
import harjoitustyo.dao.AssignmentManagerDao;
import harjoitustyo.dao.ClientManagerDao;
import harjoitustyo.dao.EmployeeManagerDao;
import harjoitustyo.domain.AppUser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Ui {

    private AppUserManagerDao userManager;
    private EmployeeManagerDao employeeManager;
    private ClientManagerDao clientManager;
    private AssignmentManagerDao assignmentManager;
    private Stage primaryStage;
    private Text errorField;
    private BorderPane root;
    private Scene scene;
    private AppUser user;

    public Ui(AppUserManagerDao userManager, EmployeeManagerDao employeeManager, ClientManagerDao clientManager, AssignmentManagerDao assignmentManager, Stage primaryStage) {
        this.userManager = userManager;
        this.employeeManager = employeeManager;
        this.clientManager = clientManager;
        this.assignmentManager = assignmentManager;
        this.primaryStage = primaryStage;
    }

    public void start() {
        errorField = new Text("");

        // Graphics
        // Create the root
        root = new BorderPane();
        root.setPadding(new Insets(10, 5, 5, 5));  // top, right, bottom, left

        //Top Box
        createLoginView();

        // create the scene
        scene = new Scene(root, 1200, 1000);
        primaryStage.setTitle("Employee Management App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void removeAllViews() {
        root.getChildren().clear();
    }

    private void createLoginView() {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        Button loginBtn = new Button("Login");
        loginBtn.setDefaultButton(true);
        TextField nameField = new TextField();
        PasswordField pwField = new PasswordField();
        hbox.getChildren().addAll(nameField, pwField, loginBtn, errorField);
        root.setTop(hbox);
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (userManager.checkLogin(nameField.getText(), pwField.getText())) {
                    errorField.setText("Successful login");
                    removeLoginView();
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
        replaceTextAfterSleep(errorField, "", 5000);
    }

    private void removeLoginView() {
        root.getChildren().remove(root.getTop());
    }

    private void replaceTextAfterSleep(Text text, String input, int duration) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(duration);
                    text.setText(input);
                } catch (InterruptedException e) {
                    System.out.println("INTERRUPTED: " + e);
                    text.setText(input);
                }
            }
        };
        thread.start();
    }

    private void createLogoutView() {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10, 0, 10, 0));  // top, right, bottom, left
        Button logoutBtn = new Button("Logout");
        hbox.getChildren().addAll(logoutBtn, errorField);
        root.setTop(hbox);
        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                errorField.setText("Successful logout");
                user = null;
                removeAllViews();
                createLoginView();
            }
        });
        replaceTextAfterSleep(errorField, "", 5000);
    }

    private void createAdminView() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 5, 5, 5));  // top, right, bottom, left
        vbox.setSpacing(10);
        Button openEmployeeView = new Button("Employees");
        Button openClientView = new Button("Client");
        Button openAssignmentView = new Button("Assignments");
        vbox.getChildren().addAll(openEmployeeView, openClientView, openAssignmentView);
        root.setLeft(vbox);
        openEmployeeView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().remove(root.getCenter());
                EmployeeView employeeView = new EmployeeView(root, employeeManager);
                employeeView.createEmployeeView();
            }
        });
        openClientView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().remove(root.getCenter());
                ClientView clientView = new ClientView(root, clientManager);
                clientView.createClientView();
            }
        });
        openAssignmentView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().remove(root.getCenter());
                AssignmentView assignmentView = new AssignmentView(root, assignmentManager, clientManager, employeeManager);
                assignmentView.createAssignmentView();
            }
        });
    }
}
