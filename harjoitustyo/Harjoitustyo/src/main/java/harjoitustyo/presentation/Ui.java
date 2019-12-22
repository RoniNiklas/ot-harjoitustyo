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
    private Text errorField = new Text("");
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
        // Graphics
        // Create the root
        root = new BorderPane();
        root.setPadding(new Insets(10, 5, 5, 5));  // top, right, bottom, left

        //Top Box
        createLoginView();

        // create the scene
        scene = new Scene(root, 1400, 1000);
        primaryStage.setTitle("Employee Management App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createLoginView() {
        HBox loginBox = new HBox();
        loginBox.setSpacing(10);
        Button loginBtn = new Button("Login");
        loginBtn.setDefaultButton(true);
        TextField nameField = new TextField();
        PasswordField pwField = new PasswordField();
        loginBox.getChildren().addAll(nameField, pwField, loginBtn, errorField);
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
        root.setTop(loginBox);
        Utils.replaceTextAfterSleep(errorField, "", 5000);
    }

    private void createLogoutView() {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10, 0, 10, 0));  // top, right, bottom, left
        Button logoutBtn = new Button("Logout");
        hbox.getChildren().addAll(logoutBtn, errorField);
        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                errorField.setText("Successful logout");
                user = null;
                removeAllViews();
                createLoginView();
            }
        });
        root.setTop(hbox);
        Utils.replaceTextAfterSleep(errorField, "", 5000);
    }

    private void createAdminView() {
        // Creates the sidebar
        VBox sidebar = new VBox();
        sidebar.setPadding(new Insets(10, 5, 5, 5));  // top, right, bottom, left
        sidebar.setSpacing(10);
        Button openEmployeeView = new Button("Employees");
        Button openClientView = new Button("Client");
        Button openAssignmentView = new Button("Assignments");
        sidebar.getChildren().addAll(openEmployeeView, openClientView, openAssignmentView);
        root.setLeft(sidebar);
        
        //Creates the views in the center
        
        EmployeeView employeeView = new EmployeeView(root, employeeManager);
        ClientView clientView = new ClientView(root, clientManager);
        AssignmentView assignmentView = new AssignmentView(root, assignmentManager, clientManager, employeeManager);

        //Sets the buttons in the sidebar to manage the views in the center
        
        openEmployeeView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().remove(root.getCenter());
                root.setCenter(employeeView.getAllComponentsBox());
            }
        });
        openClientView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().remove(root.getCenter());
                root.setCenter(clientView.getAllComponentsBox());
            }
        });
        openAssignmentView.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                root.getChildren().remove(root.getCenter());
                assignmentView.createTableBox();
                root.setCenter(assignmentView.getAllComponentsBox());
            }
        });
    }

    private void removeLoginView() {
        root.getChildren().remove(root.getTop());
    }

    private void removeAllViews() {
        root.getChildren().clear();
    }

}
