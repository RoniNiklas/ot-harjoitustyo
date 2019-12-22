package harjoitustyo.presentation;

//Roni
import harjoitustyo.dao.AssignmentManagerDao;
import harjoitustyo.dao.ClientManagerDao;
import harjoitustyo.dao.EmployeeManagerDao;
import harjoitustyo.domain.Assignment;
import harjoitustyo.domain.Client;
import harjoitustyo.domain.Employee;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.scene.control.ComboBox;

class NewAssignmentView {

    private Client client;
    private Employee employee;
    private BorderPane root;
    private Stage stage = new Stage();
    private AssignmentManagerDao assignmentManager;
    private ClientManagerDao clientManager;
    private EmployeeManagerDao employeeManager;
    private Text errorField = new Text("");
    private String clientFilter = "";
    private String employeeFilter = "";
    private final AssignmentView parent;

    public NewAssignmentView(AssignmentManagerDao assignmentManager, ClientManagerDao clientManager, EmployeeManagerDao employeeManager, AssignmentView parent) {
        root = new BorderPane();
        root.setPadding(new Insets(10, 10, 5, 5));
        Scene scene = new Scene(root, 1200, 800);
        stage.setScene(scene);
        stage.setTitle("Create a new assignment");
        this.assignmentManager = assignmentManager;
        this.clientManager = clientManager;
        this.employeeManager = employeeManager;
        this.parent = parent;
    }

    public void createNewAssignmentView() {
        stage.show();
        openSelectClientView();
    }

    public void removeAllViews() {
        root.getChildren().clear();
    }

    public void openSelectClientView() {
        removeAllViews();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        Button nextButton = new Button("Next");
        vbox.getChildren().addAll(errorField, new Text("1. First"),
                new Text("Select a client"), createSelectClientBox(),
                new Text("Or create a new client. (Remember to press Create Client)."), createAddClientBox(),
                new Text("2. Once you have selected the client, click next"), nextButton);
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (client != null) {
                    openSelectEmployeeView();
                } else {
                    errorField.setText("You have to choose a client for the assignment.");
                    Utils.replaceTextAfterSleep(errorField, "", 5000);
                }
            }
        });
        root.setCenter(vbox);
    }

    private HBox createSelectClientBox() {
        errorField.setText("");
        ObservableList clients = clientManager.getObservableClients(clientFilter);
        HBox selectClientBox = new HBox();
        ComboBox clientChooser = new ComboBox();
        clientChooser.getItems().add(null);
        clientChooser.getItems().addAll(clients);
        clientChooser.setValue(client);
        clientChooser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                client = (Client) clientChooser.getValue();
            }
        });
        selectClientBox.getChildren().addAll(clientChooser, createClientFilterBox());
        selectClientBox.setSpacing(100);
        return selectClientBox;
    }

    private HBox createClientFilterBox() {
        HBox filterBox = new HBox();
        Text filterText = new Text("Filter Clients");
        TextField filterField = new TextField(clientFilter);
        Button filterBtn = new Button("Filter Clients");
        Button removeFilterBtn = new Button("Clear Filter");
        filterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clientFilter = filterField.getText();
                openSelectClientView();
            }
        });
        removeFilterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clientFilter = "";
                openSelectClientView();
            }
        });
        filterBox.getChildren().addAll(filterField, filterBtn, removeFilterBtn);
        filterBox.setSpacing(10);
        return filterBox;
    }

    private HBox createAddClientBox() {
        HBox addClientBox = new HBox();
        addClientBox.setSpacing(10);
        TextField fnameField = Utils.createTextField("First Name", 75);
        TextField lnameField = Utils.createTextField("Last name", 75);
        TextField emailField = Utils.createTextField("Email", 100);
        TextField numberField = Utils.createTextField("Phone number", 75);
        TextField addressField = Utils.createTextField("Address", 100);
        TextField idNumberField = Utils.createTextField("National id number", 125);
        Button submitButton = new Button("Create Client");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!clientManager.contains(idNumberField.getText())) {
                    client = new Client();
                    client.setEmail(emailField.getText());
                    client.setFirstname(fnameField.getText());
                    client.setLastname(lnameField.getText());
                    client.setIdNumber(idNumberField.getText());
                    client.setNumber(numberField.getText());
                    client.setAddress(addressField.getText());
                    client = clientManager.add(client);
                    openSelectClientView();
                } else {
                    errorField.setText("A client with that ID already exists!");
                    Utils.replaceTextAfterSleep(errorField, "", 5000);
                }
            }
        });
        addClientBox.getChildren().addAll(fnameField, lnameField, emailField, numberField, addressField, idNumberField, submitButton);
        return addClientBox;
    }

    public void openSelectEmployeeView() {
        removeAllViews();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        HBox backAndNext = new HBox();
        Button nextButton = new Button("Next");
        Button backButton = new Button("Back");
        backAndNext.getChildren().addAll(backButton, nextButton);
        backAndNext.setSpacing(50);
        vbox.getChildren().addAll(errorField,
                new Text("3. Then"),
                new Text("Select an employee"), createSelectEmployeeBox(),
                new Text("Or create a new employee. (Remember to press Create Employee)."), createAddEmployeeBox(),
                new Text("4. Once selected, click next. Or go back to selecting a client."), backAndNext);
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (employee != null) {
                    openAssignmentDetailsView();
                } else {
                    errorField.setText("You have to select an employee for the assignment.");
                    Utils.replaceTextAfterSleep(errorField, "", 5000);
                }
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                openSelectClientView();
            }
        });
        root.setCenter(vbox);
    }

    private HBox createSelectEmployeeBox() {
        errorField.setText("");
        ObservableList employees = employeeManager.getObservableEmployees(employeeFilter);
        HBox selectEmployeeBox = new HBox();
        ComboBox employeeChooser = new ComboBox();
        employeeChooser.getItems().add(null);
        employeeChooser.getItems().addAll(employees);
        employeeChooser.setValue(employee);
        employeeChooser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                employee = (Employee) employeeChooser.getValue();
            }
        });
        selectEmployeeBox.getChildren().addAll(employeeChooser, createEmployeeFilterBox());
        selectEmployeeBox.setSpacing(100);
        return selectEmployeeBox;
    }

    private HBox createEmployeeFilterBox() {
        HBox filterBox = new HBox();
        TextField filterField = new TextField(employeeFilter);
        Button filterBtn = new Button("Filter Employees");
        Button removeFilterBtn = new Button("Clear Filter");
        filterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                employeeFilter = filterField.getText();
                openSelectEmployeeView();
            }
        });
        removeFilterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                employeeFilter = "";
                openSelectEmployeeView();
            }
        });
        filterBox.getChildren().addAll(filterField, filterBtn, removeFilterBtn);
        filterBox.setSpacing(10);
        return filterBox;
    }

    private VBox createAddEmployeeBox() {
        VBox topBox = new VBox();
        HBox addEmployeeBox = new HBox();
        Text addEmployeeText = new Text("Add Employee");
        addEmployeeBox.setSpacing(10);
        TextField fnameField = Utils.createTextField("First Name", 75);
        TextField lnameField = Utils.createTextField("Last name", 75);
        TextField emailField = Utils.createTextField("Email", 100);
        TextField numberField = Utils.createTextField("Phone number", 75);
        TextField addressField = Utils.createTextField("Address", 100);
        TextField idNumberField = Utils.createTextField("National id number", 125);
        Button submitButton = new Button("Add Employee");
        addEmployeeBox.getChildren().addAll(fnameField, lnameField, emailField, numberField, addressField, idNumberField, submitButton);
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!employeeManager.contains(idNumberField.getText())) {
                    employee = new Employee();
                    employee.setEmail(emailField.getText());
                    employee.setFirstname(fnameField.getText());
                    employee.setLastname(lnameField.getText());
                    employee.setIdNumber(idNumberField.getText());
                    employee.setNumber(numberField.getText());
                    employee.setAddress(addressField.getText());
                    employee = employeeManager.add(employee);
                    openSelectEmployeeView();
                } else {
                    errorField.setText("An employee with that ID already exists!");
                    Utils.replaceTextAfterSleep(errorField, "", 5000);
                }
            }
        });
        topBox.getChildren().addAll(addEmployeeText, addEmployeeBox);
        return topBox;
    }

    public void openAssignmentDetailsView() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        // Static textfields
        Text clientName = new Text("Client: " + client.getFullname());
        Text employeeName = new Text("Employee: " + employee.getFullname());
        HBox statics = new HBox();
        statics.setSpacing(50);
        statics.getChildren().addAll(clientName, employeeName);

        //Modifiable textfields
        //Description
        HBox descriptionBox = new HBox();
        TextArea descriptionField = new TextArea();
        descriptionField.setWrapText(true);
        descriptionField.setMinHeight(200);
        descriptionBox.getChildren().addAll(new Text("Description: "), descriptionField);

        //Address
        HBox addressBox = new HBox();
        TextField addressField = Utils.createTextField("Address for the assignment", 100);
        addressField.setText(client.getAddress());
        addressBox.getChildren().addAll(new Text("Address: "), addressField);

        //Contact info
        HBox contactBox = new HBox();
        TextField contactField = Utils.createTextField("Client's phone number", 100);
        contactField.setText(client.getNumber());
        contactBox.getChildren().addAll(new Text("Client's phone number: "), contactField);

        //Startdate
        VBox startDateAndTimeVBox = new VBox();
        HBox startDateAndTimeHBox = new HBox();
        DatePicker startDatePicker = new DatePicker();
        TextField startHour = Utils.createTextField("Hour", 10);
        TextField startMinutes = Utils.createTextField("Minutes", 10);
        startDateAndTimeHBox.getChildren().addAll(startDatePicker, startHour, startMinutes);
        startDateAndTimeVBox.getChildren().addAll(new Text("Start date and time"), startDateAndTimeHBox);
        
        //EndDate
        VBox endDateAndTimeVBox = new VBox();
        HBox endDateAndTimeHBox = new HBox();
        DatePicker endDatePicker = new DatePicker();
        TextField endHour = Utils.createTextField("Hour", 10);
        TextField endMinutes = Utils.createTextField("Minutes", 10);
        endDateAndTimeHBox.getChildren().addAll(endDatePicker, endHour, endMinutes);
        endDateAndTimeVBox.getChildren().addAll(new Text("End date and time"), endDateAndTimeHBox);
        
        // Backandnext
        HBox backAndNext = new HBox();
        Button nextButton = new Button("Submit");
        Button backButton = new Button("Back");
        backAndNext.getChildren().addAll(backButton, nextButton);
        backAndNext.setSpacing(50);

        //Put it all together
        vbox.getChildren().addAll(errorField,
                new Text("5. Then fill in the details of the assignment"),
                statics,
                addressBox,
                contactBox,
                descriptionBox,
                startDateAndTimeVBox,
                endDateAndTimeVBox,
                new Text("6. Once you're ready, click submit. Or go back to selecting an employee"), backAndNext);
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (assignmentManager.validateAssignment(
                            client, employee,
                            startDatePicker.getValue(),
                            startHour.getText(),
                            startMinutes.getText(),
                            endDatePicker.getValue(),
                            endHour.getText(),
                            endMinutes.getText(),
                            descriptionField.getText(),
                            addressField.getText(),
                            contactField.getText(),
                            "", "Assigned"
                    )) {
                        Assignment assignment = new Assignment(client, employee,
                                LocalDateTime.of(startDatePicker.getValue(), LocalTime.of(Integer.parseInt(startHour.getText()), Integer.parseInt(startMinutes.getText()))),
                                LocalDateTime.of(endDatePicker.getValue(), LocalTime.of(Integer.parseInt(endHour.getText()), Integer.parseInt(endMinutes.getText()))),
                                descriptionField.getText(),
                                addressField.getText(),
                                contactField.getText(),
                                "", "Assigned");
                        assignment = assignmentManager.add(assignment);
                        stage.close();
                        parent.createTableBox();
                    }
                } catch (Exception exception) {
                    errorField.setText(exception.getMessage());
                }
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                openSelectEmployeeView();
            }
        });
        root.setCenter(vbox);
    }
}
