
package harjoitustyo.presentation;

import harjoitustyo.domain.Client;
import harjoitustyo.dao.ClientManagerDao;
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
public class ClientView {

    private BorderPane root;
    private ClientManagerDao clientManager;
    private ObservableList clients;
    private String filter = "";
    private Text errorField = new Text("");
    @Getter
    private VBox allComponentsBox = new VBox();
    private VBox tableBox = new VBox();
    private VBox filterBox = new VBox();
    private VBox newClientBox = new VBox();
    private VBox removeClientBox = new VBox();

    public ClientView(BorderPane root, ClientManagerDao clientManager) {
        this.root = root;
        this.clientManager = clientManager;
        allComponentsBox.setSpacing(10);
        clients = clientManager.getObservableClients(filter);
        createTableBox();
        createFilterBox();
        createNewClientBox();
        createRemoveClientBox();
        allComponentsBox.getChildren().clear();
        allComponentsBox.getChildren().addAll(tableBox, errorField, filterBox, newClientBox, removeClientBox);
    }

    public void createTableBox() {
        clients = clientManager.getObservableClients(filter);
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
        table.setItems(clients);
        tableBox.getChildren().add(table);
    }

    private void createFilterBox() {
        filterBox.getChildren().clear();
        HBox filterHBox = new HBox();
        TextField filterField = new TextField(filter);
        Button filterBtn = new Button("Filter Clients");
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
        filterBox.getChildren().addAll(new Text("Filter Clients"), filterHBox);
    }

    private void createNewClientBox() {
        newClientBox.getChildren().clear();
        HBox newClientHBox = new HBox();
        newClientHBox.setSpacing(10);
        TextField fnameField = Utils.createTextField("First Name", 75);
        TextField lnameField = Utils.createTextField("Last name", 75);
        TextField emailField = Utils.createTextField("Email", 100);
        TextField numberField = Utils.createTextField("Phone number", 75);
        TextField addressField = Utils.createTextField("Address", 100);
        TextField idNumberField = Utils.createTextField("National id number", 125);
        Button submitButton = new Button("Add Client");
        newClientHBox.getChildren().addAll(fnameField, lnameField, emailField, numberField, addressField, idNumberField, submitButton);
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!clientManager.contains(idNumberField.getText())) {
                    Client client = new Client();
                    client.setEmail(emailField.getText());
                    client.setFirstname(fnameField.getText());
                    client.setLastname(lnameField.getText());
                    client.setIdNumber(idNumberField.getText());
                    client.setNumber(numberField.getText());
                    client.setAddress(addressField.getText());
                    clientManager.add(client);
                    createTableBox();
                    createNewClientBox();
                } else {
                    errorField.setText("An client with that ID already exists!");
                }
            }
        });
        newClientBox.getChildren().addAll(new Text("Add a new client"), newClientHBox);
    }

    private void createRemoveClientBox() {
        removeClientBox.getChildren().clear();
        HBox removeClientHBox = new HBox();
        TextField removeClientTextField = Utils.createTextField("Client's national id number", 200);
        removeClientHBox.setSpacing(10);
        Button removeClientButton = new Button("Remove Client");
        removeClientHBox.getChildren().addAll(removeClientTextField, removeClientButton);
        removeClientButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String idNumber = removeClientTextField.getText();
                if (clientManager.contains(idNumber)) {
                    clientManager.remove(idNumber);
                } else {
                    errorField.setText("No client with that ID exists.");
                }
                createTableBox();
            }
        });
        removeClientBox.getChildren().addAll(new Text("Remove Client"), removeClientHBox);
    }

    private TableColumn createEditableColumn(String header, String field, int minWidth) {
        TableColumn returnCol = new TableColumn(header);
        returnCol.setMinWidth(minWidth);
        returnCol.setCellValueFactory(new PropertyValueFactory<Client, String>(field));
        returnCol.setCellFactory(TextFieldTableCell.forTableColumn());
        if (field.equals("idNumber")) {
            returnCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Client, String> t) {
                    if (!clientManager.contains(t.getNewValue())) {
                        Long id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getId();
                        clientManager.update(id, field, t.getNewValue());
                    } else {
                        errorField.setText("An client with that ID already exists!");
                        Utils.replaceTextAfterSleep(errorField, "", 5000);
                        createTableBox();
                    }
                }
            });
        } else {
            returnCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Client, String> t) {
                    Long id = t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getId();
                    clientManager.update(id, field, t.getNewValue());
                }
            });
        }
        return returnCol;
    }
}
