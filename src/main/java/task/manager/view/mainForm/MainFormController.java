package task.manager.view.mainForm;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import task.manager.controller.Controller;
import task.manager.model.Task;
import task.manager.view.addForm.AddTaskForm;
import task.manager.view.editForm.EditTaskForm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainFormController {
    
    @FXML
    private MenuItem loadJournalItem;
    
    @FXML
    private MenuItem saveJournalItem;
    
    @FXML
    private TableView<TaskRow> tasksTable;
    
    @FXML
    private CheckBox checkBoxAllTasks;
    
    @FXML
    private TableColumn<TaskRow, CheckBox> checkColumn;
    
    @FXML
    private TableColumn<TaskRow, String> nameColumn;
    
    @FXML
    private TableColumn<TaskRow, String> descriptionColumn;
    
    @FXML
    private TableColumn<TaskRow, String> dateColumn;
    
    @FXML
    private TableColumn<TaskRow, String> statusColumn;
    
    @FXML
    private TableColumn<TaskRow, String> editColumn;
    
    @FXML
    private Button editButton;
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private Button deleteButton;
    
    private final ArrayList<TaskRow> taskRows;
    
    public MainFormController() throws IOException {
        this.taskRows = new ArrayList<>();
    }
    
    Controller controller = Controller.getInstance();
    
    public void refreshTable() {
        
        List<Task> tasksList = controller.getAllTasks();
        taskRows.clear();
        for (Task task : tasksList) {
            taskRows.add(new TaskRow(task));
        }
        tasksTable.setItems(FXCollections.observableList(taskRows));
    }
    
    @FXML
    private void initialize() {
        
        initTableColumns();
        refreshTable();
    }
    
    @FXML
    public void selectAllTasks() {
        if (checkBoxAllTasks.isSelected()) {
            for (TaskRow taskRow : taskRows) {
                taskRow.getTaskCheckBox().setSelected(true);
            }
        } else {
            for (TaskRow taskRow : taskRows) {
                taskRow.getTaskCheckBox().setSelected(false);
            }
        }
    }
    
    @FXML
    private void initTableColumns() {
        
        checkColumn.setCellValueFactory(new PropertyValueFactory<>("taskCheckBox"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("taskDescription"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("notificationDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
        editColumn.setCellValueFactory(new PropertyValueFactory<>("taskEditButton"));
    }
    
    @FXML
    public void loadJournal(ActionEvent actionEvent) {
        //TODO
    }
    
    @FXML
    public void saveJournal(ActionEvent actionEvent) {
        //TODO
    }
    
    @FXML
    public void clickAddButton(ActionEvent event) throws Exception {
        AddTaskForm addTaskForm = new AddTaskForm();
        Stage       stage       = new Stage();
        addTaskForm.start(stage);
        stage.setOnCloseRequest(we -> refreshTable());
    }
    
    @FXML
    public void clickEditButton(ActionEvent event) throws Exception {
        EditTaskForm editTaskForm = new EditTaskForm();
        Stage        stage        = new Stage();
        editTaskForm.start(stage);
        //refresh table after edit;
    }
    
    @FXML
    public void clickDeleteButton(ActionEvent event) {
        
        for (int i = controller.getAllTasks().size() - 1; i >= 0; i--) {
            if (tasksTable.getItems().get(i).getTaskCheckBox().isSelected()) {
                controller.deleteTask(tasksTable.getItems().get(i).getId());
            }
        }
        refreshTable();
    }
}


