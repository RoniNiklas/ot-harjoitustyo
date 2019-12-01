/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package harjoitustyo.presentation;

import harjoitustyo.dao.ClientManagerDao;
import harjoitustyo.domain.Client;
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
public class ClientView {

    private BorderPane root;
    private ClientManagerDao clientManager;
    private ObservableList clients;
    private String filter;
    private Text errorField;

    public ClientView(BorderPane root, ClientManagerDao clientManager) {
        this.root = root;
        this.clientManager = clientManager;
        this.filter = "";
        clients = clientManager.getObservableClients(this.filter);
        this.errorField = new Text("");
    }

    public void createClientView() {
        clients = clientManager.getObservableClients(filter);
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        TableView table = createTableView();
        VBox filterBox = createFilterBox();
        VBox addClientBox = createAddClientBox();
        VBox removeClientBox = createRemoveClientBox();
        vbox.getChildren().addAll(table, errorField, filterBox, addClientBox, removeClientBox);
        root.setCenter(vbox);
    }

    private TableView createTableView() {
        TableView table = new TableView();
        table.setEditable(true);
        TableColumn firstnameCol = new TableColumn("First Name");
        TableColumn lastnameCol = new TableColumn("Last Name");
        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(200);
        TableColumn numberCol = new TableColumn("Number");
        numberCol.setMinWidth(100);
        TableColumn addressCol = new TableColumn("Address");
        addressCol.setMinWidth(200);
        TableColumn idNumberCol = new TableColumn("ID Number");
        idNumberCol.setMinWidth(100);
        firstnameCol.setCellValueFactory(new PropertyValueFactory<Client, String>("firstname"));
        firstnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstnameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> t) {
                ((Client) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setFirstname(t.getNewValue());
            }
        });
        lastnameCol.setCellValueFactory(new PropertyValueFactory<Client, String>("lastname"));
        lastnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastnameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> t) {
                ((Client) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setLastname(t.getNewValue());
            }
        });
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Client, String>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> t) {
                ((Client) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setEmail(t.getNewValue());
            }
        });
        numberCol.setCellValueFactory(new PropertyValueFactory<Client, String>("number"));
        numberCol.setCellFactory(TextFieldTableCell.forTableColumn());
        numberCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> t) {
                ((Client) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setNumber(t.getNewValue());
            }
        });
        addressCol.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> t) {
                ((Client) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setAddress(t.getNewValue());
            }
        });
        idNumberCol.setCellValueFactory(new PropertyValueFactory<Client, String>("idNumber"));

        idNumberCol.setCellFactory(TextFieldTableCell.forTableColumn());
        idNumberCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Client, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Client, String> t) {
                if (!clientManager.contains(t.getNewValue())) {
                    ((Client) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setIdNumber(t.getNewValue());
                } else {
                    errorField.setText("An client with that id already exists!");
                    createClientView();
                }
            }
        });
        table.getColumns().addAll(firstnameCol, lastnameCol, emailCol, numberCol, addressCol, idNumberCol);
        table.setItems(clients);
        return table;
    }

    private VBox createFilterBox() {
        VBox topBox = new VBox();
        HBox filterBox = new HBox();
        Text filterText = new Text("Filter Clients");
        TextField filterField = new TextField(filter);
        Button filterBtn = new Button("Filter Clients");
        Button removeFilterBtn = new Button("Clear Filter");
        filterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                filter = filterField.getText();
                createClientView();
            }
        });
        removeFilterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                filter = "";
                createClientView();
            }
        });
        filterBox.getChildren().addAll(filterField, filterBtn, removeFilterBtn);
        filterBox.setSpacing(10);
        topBox.getChildren().addAll(filterText, filterBox);
        return topBox;
    }

    private VBox createAddClientBox() {
        VBox topBox = new VBox();
        HBox addClientBox = new HBox();
        Text addClientText = new Text("Add Client");
        addClientBox.setSpacing(10);
        TextField fnameField = new TextField("First name");
        TextField lnameField = new TextField("Last name");
        TextField emailField = new TextField("Email");
        TextField numberField = new TextField("Phone number");
        TextField idNumberField = new TextField("National id number");
        TextField addressField = new TextField("Address");
        Button submitButton = new Button("Add Client");
        addClientBox.getChildren().addAll(fnameField, lnameField, emailField, numberField, addressField, idNumberField, submitButton);
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
                    createClientView();
                } else {
                    errorField.setText("An client with that ID already exists!");
                }
            }
        });
        topBox.getChildren().addAll(addClientText, addClientBox);
        return topBox;
    }

    private VBox createRemoveClientBox() {
        VBox topBox = new VBox();
        HBox removeClientBox = new HBox();
        TextField removeClientTextField = new TextField("Client's national id number");
        removeClientBox.setSpacing(10);
        Button removeClientButton = new Button("Remove Client");
        removeClientBox.getChildren().addAll(removeClientTextField, removeClientButton);
        removeClientButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String idNumber = removeClientTextField.getText();
                if (clientManager.contains(idNumber)) {
                    clientManager.remove(idNumber);
                } else {
                    errorField.setText("No client with that ID exists.");
                }
                createClientView();
            }
        });
        topBox.getChildren().addAll(new Text("Remove Client"), removeClientBox);
        return topBox;
    }
}
