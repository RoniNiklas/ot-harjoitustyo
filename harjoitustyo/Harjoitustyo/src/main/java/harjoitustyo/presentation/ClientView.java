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
                        errorField.setText("A client with that ID already exists!");
                        replaceTextAfterSleep(errorField, "", 5000);
                        createClientView();
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
