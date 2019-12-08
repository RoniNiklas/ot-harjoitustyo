/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.presentation;

import harjoitustyo.domain.Employee;
import harjoitustyo.dao.EmployeeManagerDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Roni
 */
public class EmployeeView {

    private BorderPane root;
    private EmployeeManagerDao employeeManager;
    private ObservableList employees;
    private String filter;
    private Text errorField;

    public EmployeeView(BorderPane root, EmployeeManagerDao employeeManager) {
        this.root = root;
        this.employeeManager = employeeManager;
        this.filter = "";
        employees = employeeManager.getObservableEmployees(this.filter);
        this.errorField = new Text("");
    }

    private void replaceTextAfterSleep(Text text, String input, int duration) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(duration);
                    text.setText(input);
                } catch (InterruptedException e) {
                    System.out.println("INTERRUPTED");
                    text.setText(input);
                }
            }
        };
        thread.start();
    }

    public void createEmployeeView() {
        employees = employeeManager.getObservableEmployees(filter);
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        TableView table = createTableView();
        VBox filterBox = createFilterBox();
        VBox addEmployeeBox = createAddEmployeeBox();
        VBox removeEmployeeBox = createRemoveEmployeeBox();
        vbox.getChildren().addAll(table, errorField, filterBox, addEmployeeBox, removeEmployeeBox);
        root.setCenter(vbox);
    }

    private TableColumn createEditableColumn(String header, String field, int minWidth) {
        TableColumn returnCol = new TableColumn(header);
        returnCol.setMinWidth(minWidth);
        returnCol.setCellValueFactory(new PropertyValueFactory<Employee, String>(field));
        returnCol.setCellFactory(TextFieldTableCell.forTableColumn());
        if (field.equals("idNumber")) {
            returnCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                    if (!employeeManager.contains(t.getNewValue())) {
                        Long id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getId();
                        employeeManager.update(id, field, t.getNewValue());
                    } else {
                        errorField.setText("An employee with that ID already exists!");
                        replaceTextAfterSleep(errorField, "", 5000);
                        createEmployeeView();
                    }
                }
            });
        } else {
            returnCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Employee, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Employee, String> t) {
                    Long id = t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getId();
                    employeeManager.update(id, field, t.getNewValue());
                }
            });
        }
        return returnCol;
    }

    private TableView createTableView() {
        TableView table = new TableView();
        table.setEditable(true);
        TableColumn firstnameCol = createEditableColumn("First name", "firstname", 100);
        TableColumn lastnameCol = createEditableColumn("Last name", "lastname", 100);
        TableColumn emailCol = createEditableColumn("Email", "email", 200);
        TableColumn numberCol = createEditableColumn("Number", "number", 100);
        TableColumn addressCol = createEditableColumn("Address", "address", 200);
        TableColumn idNumberCol = createEditableColumn("ID number", "idNumber", 100);
        table.getColumns().addAll(firstnameCol, lastnameCol, emailCol, numberCol, addressCol, idNumberCol);
        table.setItems(employees);
        return table;
    }

    private VBox createFilterBox() {
        VBox topBox = new VBox();
        HBox filterBox = new HBox();
        Text filterText = new Text("Filter Employees");
        TextField filterField = new TextField(filter);
        Button filterBtn = new Button("Filter Employees");
        Button removeFilterBtn = new Button("Clear Filter");
        filterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                filter = filterField.getText();
                createEmployeeView();
            }
        });
        removeFilterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                filter = "";
                createEmployeeView();
            }
        });
        filterBox.getChildren().addAll(filterField, filterBtn, removeFilterBtn);
        filterBox.setSpacing(10);
        topBox.getChildren().addAll(filterText, filterBox);
        return topBox;
    }

    private VBox createAddEmployeeBox() {
        VBox topBox = new VBox();
        HBox addEmployeeBox = new HBox();
        Text addEmployeeText = new Text("Add Employee");
        addEmployeeBox.setSpacing(10);
        TextField fnameField = new TextField("First name");
        TextField lnameField = new TextField("Last name");
        TextField emailField = new TextField("Email");
        TextField numberField = new TextField("Phone number");
        TextField addressField = new TextField("Address");
        TextField idNumberField = new TextField("National id number");
        Button submitButton = new Button("Add Employee");
        addEmployeeBox.getChildren().addAll(fnameField, lnameField, emailField, numberField, addressField, idNumberField, submitButton);
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!employeeManager.contains(idNumberField.getText())) {
                    Employee employee = new Employee();
                    employee.setEmail(emailField.getText());
                    employee.setFirstname(fnameField.getText());
                    employee.setLastname(lnameField.getText());
                    employee.setIdNumber(idNumberField.getText());
                    employee.setNumber(numberField.getText());
                    employee.setAddress(addressField.getText());
                    employeeManager.add(employee);
                    createEmployeeView();
                } else {
                    errorField.setText("An employee with that ID already exists!");
                }
            }
        });
        topBox.getChildren().addAll(addEmployeeText, addEmployeeBox);
        return topBox;
    }

    private VBox createRemoveEmployeeBox() {
        VBox topBox = new VBox();
        HBox removeEmployeeBox = new HBox();
        TextField removeEmployeeTextField = new TextField("Employee's national id number");
        removeEmployeeBox.setSpacing(10);
        Button removeEmployeeButton = new Button("Remove Employee");
        removeEmployeeBox.getChildren().addAll(removeEmployeeTextField, removeEmployeeButton);
        removeEmployeeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String idNumber = removeEmployeeTextField.getText();
                if (employeeManager.contains(idNumber)) {
                    System.out.println("Contains!");
                    employeeManager.remove(idNumber);
                } else {
                    errorField.setText("No employee with that ID exists.");
                }
                createEmployeeView();
            }
        });
        topBox.getChildren().addAll(new Text("Remove Employee"), removeEmployeeBox);
        return topBox;
    }
}
