package com.netcracker.task.manager.view.mainForm;


import com.netcracker.task.manager.controller.BackupManager;
import com.netcracker.task.manager.controller.exception.*;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;
import com.netcracker.task.manager.view.utils.AlertForm;
import com.netcracker.task.manager.view.utils.Refresher;
import com.netcracker.task.manager.view.utils.ViewConstants;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.netcracker.task.manager.controller.Controller;
import com.netcracker.task.manager.view.utils.TaskRowManager;
import com.netcracker.task.manager.view.addForm.AddTaskForm;
import com.netcracker.task.manager.view.editForm.EditTaskForm;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
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
    private final Controller         controller;
    private final BackupManager      backupManager;
    
    public MainFormController() {
        this.taskRows = new ArrayList<>();
        this.controller = Controller.getInstance();
        this.backupManager = BackupManager.getInstance();
    }
    
    public void refreshTable() {
        
        List<Task> tasksList = controller.getAllTasks();
        taskRows.clear();
        for (Task task : tasksList) {
            taskRows.add(new TaskRow(task));
        }
        taskRows.sort(Comparator.comparingInt(TaskRow::getId));
        tasksTable.setItems(FXCollections.observableList(taskRows));
        Refresher.getInstance().setMainFormController(this);
        
        checkBoxAllTasks.setSelected(false);
        editButton.setDisable(true);
        cancelButton.setDisable(true);
        deleteButton.setDisable(true);
        for (TaskRow taskRow : taskRows) {
            taskRow.getTaskCheckBox().setOnAction(event -> disableButtons());
            if (taskRow.getTaskStatus().equals(Status.DONE.getTitle())) {
                taskRow.getTaskEditButton().setDisable(true);
            }
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
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(ViewConstants.TITLE_TO_LOAD_FILE_CHOOSER);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(ViewConstants.FILTERS_FOR_FILE_CHOOSER,
                                                                                 "*.bin", "*.txt"));
        File file = fileChooser.showOpenDialog(tasksTable.getScene().getWindow());
        if (file == null) {
            return;
        }
        try {
            Journal journal = backupManager.readOtherBackup(file.toString());
            controller.restoreBackupTasks(journal);
            refreshTable();
        } catch (ReadFileException e) {
            AlertForm.errorAlert(e.getMessage());
        }
    }
    
    @FXML
    public void saveJournal(ActionEvent actionEvent) {
        try {
            backupManager.writeBackup(controller.getJournal());
        } catch (WriteFileException e) {
            AlertForm.errorAlert(e.getMessage());
        }
    }
    
    @FXML
    public void clickAddButton(ActionEvent event) {
        try {
            AddTaskForm addTaskForm = new AddTaskForm();
            Stage       stage       = new Stage();
            addTaskForm.start(stage);
            stage.setOnCloseRequest(we -> refreshTable());
        } catch (Exception e) {
            AlertForm.errorAlert(ViewConstants.ERROR_ADD_TASK_FORM_EXCEPTION);
        }
    }
    
    @FXML
    public void clickEditButton(ActionEvent event) {
        
        try {
            for (int i = 0; i < tasksTable.getItems().size(); i++) {
                if (tasksTable.getItems().get(i).getTaskCheckBox().isSelected()) {
                    TaskRowManager.getInstance().setTaskRow(tasksTable.getItems().get(i));
                }
            }
            
            EditTaskForm editTaskForm = new EditTaskForm();
            Stage        stage        = new Stage();
            editTaskForm.start(stage);
            stage.setOnCloseRequest(we -> refreshTable());
        } catch (Exception e) {
            AlertForm.errorAlert(ViewConstants.ERROR_EDIT_TASK_FORM_EXCEPTION);
        }
        
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
    
    public void setTaskRowByTaskId(int taskId) {
        List<TaskRow> taskRows = tasksTable.getItems();
        TaskRow       taskRow  = taskRows.stream().filter(tr -> tr.getId() == taskId).findFirst().get();
        TaskRowManager.getInstance().setTaskRow(taskRow);
    }
}


