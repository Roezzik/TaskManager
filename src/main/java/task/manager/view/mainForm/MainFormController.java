package task.manager.view.mainForm;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import task.manager.controller.Controller;
import task.manager.model.Status;
import task.manager.model.Task;
import task.manager.view.utils.Refresher;
import task.manager.view.utils.TaskRowManager;
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
    private final Controller controller;
    
    public MainFormController() throws IOException {
        this.taskRows = new ArrayList<>();
        this.controller = Controller.getInstance();
    }
    
    public void refreshTable() {
        
        List<Task> tasksList = controller.getAllTasks();
        taskRows.clear();
        for (Task task : tasksList) {
            taskRows.add(new TaskRow(task));
        }
        tasksTable.setItems(FXCollections.observableList(taskRows));
        Refresher.getInstance().setMainFormController(this);
        
        editButton.setDisable(true);
        cancelButton.setDisable(true);
        deleteButton.setDisable(true);
        for (TaskRow taskRow : taskRows) {
            taskRow.getTaskCheckBox().setOnAction(event -> disableButtons());
        }
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
                editButton.setDisable(true);
                cancelButton.setDisable(false);
                deleteButton.setDisable(false);
            }
        } else {
            for (TaskRow taskRow : taskRows) {
                taskRow.getTaskCheckBox().setSelected(false);
                editButton.setDisable(true);
                cancelButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        }
    }
    
    @FXML
    public void disableButtons() {
        
        int counter = 0, doneCounter = 0, cancelCounter = 0;
        for (TaskRow taskRow : taskRows) {
            if (taskRow.getTaskCheckBox().isSelected()) {
                counter++;
                if (controller.getTask(taskRow.getId()).getStatus() == Status.DONE) {
                    doneCounter++;
                }
                if (controller.getTask(taskRow.getId()).getStatus() == Status.CANCELLED) {
                    cancelCounter++;
                }
            }
        }
        if (counter == 0) {
            editButton.setDisable(true);
            cancelButton.setDisable(true);
            deleteButton.setDisable(true);
        }
        
        if (counter == 1) {
            editButton.setDisable(false);
            cancelButton.setDisable(false);
            deleteButton.setDisable(false);
        }
        
        if (counter > 1) {
            editButton.setDisable(true);
            cancelButton.setDisable(false);
            deleteButton.setDisable(false);
        }
        
        if (doneCounter != 0) {
            editButton.setDisable(true);
            cancelButton.setDisable(true);
        }
        
        if (cancelCounter != 0) {
            cancelButton.setDisable(true);
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
        //TextMarshaller.getInstance().write(controller.getJournal(), PropertyParser.getPropertyValue("FILE_PATH"));
        //BinaryMarshaller.getInstance().write(controller.getJournal(), PropertyParser.getPropertyValue("FILE_PATH"));
        controller.write();
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
        for (int i = 0; i < tasksTable.getItems().size(); i++) {
            if (tasksTable.getItems().get(i).getTaskCheckBox().isSelected()) {
                TaskRowManager.getInstance().setTaskRow(tasksTable.getItems().get(i));
            }
        }
        
        EditTaskForm editTaskForm = new EditTaskForm();
        Stage        stage        = new Stage();
        editTaskForm.start(stage);
        stage.setOnCloseRequest(we -> refreshTable());
    }
    
    @FXML
    public void clickCancelButton(ActionEvent event) {
        for (int i = controller.getAllTasks().size() - 1; i >= 0; i--) {
            if (tasksTable.getItems().get(i).getTaskCheckBox().isSelected()) {
                controller.cancelTask(tasksTable.getItems().get(i).getId());
            }
        }
        refreshTable();
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


