package harjoitustyo.presentation;

//Roni
import harjoitustyo.dao.AssignmentManagerDao;
import harjoitustyo.dao.ClientManagerDao;
import harjoitustyo.dao.EmployeeManagerDao;
import harjoitustyo.domain.Assignment;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
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

public class AssignmentView {

    private BorderPane root;
    private AssignmentManagerDao assignmentManager;
    private ClientManagerDao clientManager;
    private EmployeeManagerDao employeeManager;
    private Text errorField;
    private String filter;
    private ObservableList assignments;
    private AssignmentView actual;
    @Getter
    private VBox allComponentsBox = new VBox();
    private VBox tableBox = new VBox();
    private VBox filterBox = new VBox();
    private VBox newAssignmentBox = new VBox();
    private VBox deleteAssignmentBox = new VBox();

    public AssignmentView(BorderPane root, AssignmentManagerDao assignmentManager, ClientManagerDao clientManager, EmployeeManagerDao employeeManager) {
        this.root = root;
        this.assignmentManager = assignmentManager;
        this.clientManager = clientManager;
        this.employeeManager = employeeManager;
        this.errorField = new Text("");
        this.filter = "";
        this.actual = this;
        allComponentsBox.setSpacing(10);
        createTableBox();
        createFilterBox();
        createNewAssignmentBox();
        allComponentsBox.getChildren().clear();
        allComponentsBox.getChildren().addAll(tableBox, errorField, filterBox, newAssignmentBox, deleteAssignmentBox);
    }

    public void createTableBox() {
        assignments = assignmentManager.getObservableAssignments(filter);
        tableBox.getChildren().clear();
        TableView table = new TableView();
        table.setEditable(true);
        TableColumn deleteColumn = createDeleteColumn();
        TableColumn clientColumn = createColumn("Client", "clientName", 125);
        TableColumn employeeColumn = createColumn("Employee", "employeeName", 125);
        TableColumn addressColumn = createEditableColumn("Address", "address", 150);
        TableColumn descriptionColumn = createEditableColumn("Description", "description", 200);
        TableColumn contactColumn = createEditableColumn("Contact", "contact", 125);
        TableColumn startColumn = createColumn("Starts", "startTimeString", 150);
        TableColumn endColumn = createColumn("Ends", "endTimeString", 150);
        TableColumn statusColumn = createEditableColumn("Status", "status", 50);
        table.getColumns().addAll(clientColumn, employeeColumn, descriptionColumn, addressColumn, contactColumn, startColumn, endColumn, statusColumn, deleteColumn);
        table.setItems(assignments);
        tableBox.getChildren().add(table);
    }

    private void createFilterBox() {
        filterBox.getChildren().clear();
        HBox filterHBox = new HBox();
        Text filterText = new Text("Filter Assignments");
        TextField filterField = new TextField(filter);
        Button filterBtn = new Button("Filter Assignments");
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
        filterBox.getChildren().addAll(filterText, filterHBox);
    }

    private void createNewAssignmentBox() {
        newAssignmentBox.getChildren().clear();
        newAssignmentBox.setSpacing(10);
        Text newAssignmentText = new Text("Create a new assignment");
        Button newAssignmentBtn = new Button("Create a new assignment");
        newAssignmentBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NewAssignmentView newAssignmentView = new NewAssignmentView(assignmentManager, clientManager, employeeManager, actual);
                newAssignmentView.createNewAssignmentView();
            }
        });
        newAssignmentBox.getChildren().addAll(newAssignmentText, newAssignmentBtn);
    }

    private TableColumn createEditableColumn(String header, String field, int minWidth) {
        TableColumn returnCol = new TableColumn(header);
        returnCol.setMinWidth(minWidth);
        returnCol.setCellValueFactory(new PropertyValueFactory<Assignment, String>(field));
        returnCol.setCellFactory(TextFieldTableCell.forTableColumn());
        returnCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Assignment, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Assignment, String> t) {
                Long id = t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).getId();
                assignmentManager.update(id, field, t.getNewValue());
            }
        });
        return returnCol;
    }

    private TableColumn createColumn(String header, String field, int minWidth) {
        TableColumn returnCol = new TableColumn(header);
        returnCol.setMinWidth(minWidth);
        returnCol.setCellValueFactory(new PropertyValueFactory<Assignment, String>(field));
        returnCol.setCellFactory(TextFieldTableCell.forTableColumn());
        return returnCol;
    }

    private TableColumn createDeleteColumn() {
        TableColumn<Assignment, Assignment> returnCol = new TableColumn("Delete");
        returnCol.setMinWidth(150);
        returnCol.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        returnCol.setCellFactory(param -> new TableCell<Assignment, Assignment>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Assignment assignment, boolean empty) {
                super.updateItem(assignment, empty);

                if (assignment == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(event -> {
                    assignmentManager.remove(assignment);
                    createTableBox();
                });
            }
        });
        return returnCol;
    }
}

