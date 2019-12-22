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
import lombok.Getter;

//Roni
public class EmployeeView {

    private BorderPane root;
    private EmployeeManagerDao employeeManager;
    private ObservableList employees;
    private String filter = "";
    private Text errorField = new Text("");
    @Getter
    private VBox allComponentsBox = new VBox();
    private VBox tableBox = new VBox();
    private VBox filterBox = new VBox();
    private VBox newEmployeeBox = new VBox();
    private VBox removeEmployeeBox = new VBox();

    public EmployeeView(BorderPane root, EmployeeManagerDao employeeManager) {
        this.root = root;
        this.employeeManager = employeeManager;
        allComponentsBox.setSpacing(10);
        employees = employeeManager.getObservableEmployees(filter);
        createTableBox();
        createFilterBox();
        createNewEmployeeBox();
        createRemoveEmployeeBox();
        allComponentsBox.getChildren().clear();
        allComponentsBox.getChildren().addAll(tableBox, errorField, filterBox, newEmployeeBox, removeEmployeeBox);
    }

    public void createTableBox() {
        employees = employeeManager.getObservableEmployees(filter);
        tableBox.getChildren().clear();
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
        tableBox.getChildren().add(table);
    }

    private void createFilterBox() {
        filterBox.getChildren().clear();
        HBox filterHBox = new HBox();
        TextField filterField = new TextField(filter);
        Button filterBtn = new Button("Filter Employees");
        Button removeFilterBtn = new Button("Clear Filter");
        filterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                filter = filterField.getText();
                createTableBox();
            }
        });
        removeFilterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                filter = "";
                createTableBox();
                createFilterBox();
            }
        });
        filterHBox.getChildren().addAll(filterField, filterBtn, removeFilterBtn);
        filterHBox.setSpacing(10);
        filterBox.getChildren().addAll(new Text("Filter Employees"), filterHBox);
    }

    private void createNewEmployeeBox() {
        newEmployeeBox.getChildren().clear();
        HBox newEmployeeHBox = new HBox();
        newEmployeeHBox.setSpacing(10);
        TextField fnameField = Utils.createTextField("First Name", 75);
        TextField lnameField = Utils.createTextField("Last name", 75);
        TextField emailField = Utils.createTextField("Email", 100);
        TextField numberField = Utils.createTextField("Phone number", 75);
        TextField addressField = Utils.createTextField("Address", 100);
        TextField idNumberField = Utils.createTextField("National id number", 125);
        Button submitButton = new Button("Add Employee");
        newEmployeeHBox.getChildren().addAll(fnameField, lnameField, emailField, numberField, addressField, idNumberField, submitButton);
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
                    createTableBox();
                    createNewEmployeeBox();
                } else {
                    errorField.setText("An employee with that ID already exists!");
                }
            }
        });
        newEmployeeBox.getChildren().addAll(new Text("Add a new employee"), newEmployeeHBox);
    }

    private void createRemoveEmployeeBox() {
        removeEmployeeBox.getChildren().clear();
        HBox removeEmployeeHBox = new HBox();
        TextField removeEmployeeTextField = Utils.createTextField("Employee's national id number", 200);
        removeEmployeeHBox.setSpacing(10);
        Button removeEmployeeButton = new Button("Remove Employee");
        removeEmployeeHBox.getChildren().addAll(removeEmployeeTextField, removeEmployeeButton);
        removeEmployeeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String idNumber = removeEmployeeTextField.getText();
                if (employeeManager.contains(idNumber)) {
                    employeeManager.remove(idNumber);
                } else {
                    errorField.setText("No employee with that ID exists.");
                }
                createTableBox();
            }
        });
        removeEmployeeBox.getChildren().addAll(new Text("Remove Employee"), removeEmployeeHBox);
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
                        Utils.replaceTextAfterSleep(errorField, "", 5000);
                        createTableBox();
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
}
