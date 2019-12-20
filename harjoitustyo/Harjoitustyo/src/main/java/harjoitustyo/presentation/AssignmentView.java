package harjoitustyo.presentation;

//Roni
import harjoitustyo.dao.AssignmentManagerDao;
import harjoitustyo.dao.ClientManagerDao;
import harjoitustyo.dao.EmployeeManagerDao;
import harjoitustyo.domain.Assignment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AssignmentView {

    private BorderPane root;
    private AssignmentManagerDao assignmentManager;
    private ClientManagerDao clientManager;
    private EmployeeManagerDao employeeManager;
    private Text errorField;
    private String filter;
    private ObservableList assignments;
    private AssignmentView actual;

    AssignmentView(BorderPane root, AssignmentManagerDao assignmentManager, ClientManagerDao clientManager, EmployeeManagerDao employeeManager) {
        this.root = root;
        this.assignmentManager = assignmentManager;
        this.clientManager = clientManager;
        this.employeeManager = employeeManager;
        this.errorField = new Text("");
        this.filter = "";
        this.actual = this;
    }

    public void createAssignmentView() {
        assignments = assignmentManager.getObservableAssignments(filter);
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        TableView table = createTableView();
        VBox filterBox = createFilterBox();
        VBox newAssignmentBox = createNewAssignmentBox();
        //VBox removeEmployeeBox = createRemoveEmployeeBox();
        vbox.getChildren().addAll(table, errorField, filterBox, newAssignmentBox);
        root.setCenter(vbox);
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

    private TableView createTableView() {
        TableView table = new TableView();
        table.setEditable(true);
        TableColumn clientColumn = createColumn("Client", "clientName", 125);
        TableColumn employeeColumn = createColumn("Employee", "employeeName", 125);
        TableColumn addressColumn = createEditableColumn("Address", "address", 150);
        TableColumn descriptionColumn = createEditableColumn("Description", "description", 200);
        TableColumn contactColumn = createEditableColumn("Contact", "contact", 125);
        TableColumn startColumn = createColumn("Starts", "startTimeString", 150);
        TableColumn endColumn = createColumn("Ends", "endTimeString", 150);
        TableColumn statusColumn = createEditableColumn("Status", "status", 50);
        table.getColumns().addAll(clientColumn, employeeColumn, descriptionColumn, addressColumn, contactColumn, startColumn, endColumn, statusColumn);
        table.setItems(assignments);
        return table;
    }

    private VBox createFilterBox() {
        VBox topBox = new VBox();
        HBox filterBox = new HBox();
        Text filterText = new Text("Filter Assignments");
        TextField filterField = new TextField(filter);
        Button filterBtn = new Button("Filter Assignments");
        Button removeFilterBtn = new Button("Clear Filter");
        filterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                filter = filterField.getText();
                createAssignmentView();
            }
        });
        removeFilterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                filter = "";
                createAssignmentView();
            }
        });
        filterBox.getChildren().addAll(filterField, filterBtn, removeFilterBtn);
        filterBox.setSpacing(10);
        topBox.getChildren().addAll(filterText, filterBox);
        return topBox;
    }

    private VBox createNewAssignmentBox() {
        VBox topBox = new VBox();
        topBox.setSpacing(10);
        Text newAssignmentText = new Text("Create a new assignment");
        Button newAssignmentBtn = new Button("Create a new assignment");
        topBox.getChildren().addAll(newAssignmentText, newAssignmentBtn);
        newAssignmentBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NewAssignmentView newAssignmentView = new NewAssignmentView(assignmentManager, clientManager, employeeManager, actual);
                newAssignmentView.createNewAssignmentView();
            }
        });
        return topBox;
    }
}
