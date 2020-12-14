package task.manager.view.mainForm;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import task.manager.controller.Controller;
import task.manager.view.addForm.AddTaskForm;
import task.manager.view.editForm.EditTaskForm;

import java.io.IOException;
import java.util.ArrayList;


public class MainFormController {
    
    @FXML
    private MenuItem loadJournalItem;
    
    @FXML
    private MenuItem saveJournalItem;
    
    @FXML
    private TableView<TaskRow> tasksTable;
    
    @FXML
    private CheckBox checkBoxAllTasks;
    //TODO: create selectAll() method
    
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
    
    public MainFormController() {
        this.taskRows = new ArrayList<>();
    }
    
    public void refreshTable() throws IOException {
        
        taskRows.clear();
        for (int i = 0; i < Controller.getInstance().getAllTasks().size(); i++) {
            taskRows.add(new TaskRow(Controller.getInstance().getAllTasks().get(i)));
        }
        tasksTable.setItems(FXCollections.observableList(taskRows));
    }
    
    @FXML
    private void initialize() throws IOException {
        
        initTableColumns();
        refreshTable();
    }

//    public void edittaskname(TableColumn.CellEditEvent editEvent){
//        Task taskSelected = tasksTable.getSelectionModel().getSelectedItem();
//        taskSelected.setName(editEvent.getNewValue().toString());
//    }
//
//    public void edittaskdesc(TableColumn.CellEditEvent editEvent){
//        Task taskSelected = tasksTable.getSelectionModel().getSelectedItem();
//        taskSelected.setDescription(editEvent.getNewValue().toString());
//    }
    
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
        //refresh table after add;
    }
    
    @FXML
    public void clickEditButton(ActionEvent event) throws Exception {
        EditTaskForm editTaskForm = new EditTaskForm();
        Stage        stage        = new Stage();
        editTaskForm.start(stage);
        //refresh table after edit;
    }
}
