package server.view.editForm;


import shared.factory.TaskFactory;
import shared.model.Status;
import shared.model.Task;
import shared.utils.AlertForm;
import shared.constant.ViewConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import server.controller.Controller;
import server.view.addForm.AddTaskController;
import server.view.utils.TaskRowManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class EditTaskController {
    
    @FXML
    private TextField taskName;
    
    @FXML
    private TextArea taskDescription;
    
    @FXML
    private DatePicker notificationDate;
    
    @FXML
    private Spinner<Integer> notificationHour;
    
    @FXML
    private Spinner<Integer> notificationMinute;
    
    @FXML
    public Button saveButton;
    
    @FXML
    public Button cancelButton;
    
    private final Controller     controller;
    private final TaskRowManager taskRowManager;
    
    public EditTaskController() {
        this.controller = Controller.getInstance();
        this.taskRowManager = TaskRowManager.getInstance();
    }
    
    @FXML
    private void initialize() {
        
        initData();
        initDatePicker();
        initSpinner();
    }
    
    private void initData() {
        
        taskName.setText(taskRowManager.getTaskRow().getTaskName());
        taskDescription.setText(taskRowManager.getTaskRow().getTaskDescription());
        taskDescription = limitArea();
    }
    
    private TextArea limitArea() {
        
        return AddTaskController.getTextArea(taskDescription);
    }
    
    private void initDatePicker() {
        
        LocalDate today = LocalDate.now();
        notificationDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        
        if (controller.getTask(taskRowManager.getTaskRow().getId())
                      .getStatus()
                      .getTitle()
                      .equals(Status.EXPIRED.getTitle())) {
            
            notificationDate.setValue(today);
        } else {
            Date      taskDate      = controller.getTask(taskRowManager.getTaskRow().getId()).getDate();
            LocalDate taskLocalDate = taskDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            notificationDate.setValue(taskLocalDate);
        }
        
    }
    
    private void initSpinner() {
        
        AddTaskController.initSpinnerFactory(notificationHour, notificationMinute);
        
        if (controller.getTask(taskRowManager.getTaskRow().getId())
                      .getStatus()
                      .getTitle()
                      .equals(Status.EXPIRED.getTitle())) {
            Date          currentDate     = new Date();
            LocalDateTime currentDateTime = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault());
            
            notificationHour.getValueFactory().setValue(currentDateTime.getHour());
            notificationMinute.getValueFactory().setValue(currentDateTime.getMinute() + 1);
        } else {
            Date          taskDate     = controller.getTask(taskRowManager.getTaskRow().getId()).getDate();
            LocalDateTime taskDateTime = LocalDateTime.ofInstant(taskDate.toInstant(), ZoneId.systemDefault());
            
            notificationHour.getValueFactory().setValue(taskDateTime.getHour());
            notificationMinute.getValueFactory().setValue(taskDateTime.getMinute());
        }
    }
    
    public void clickSaveButton(ActionEvent event) {
        
        if (taskName.getText().length() == 0) {
            AlertForm.infoEditAlert(ViewConstants.ALERT_MISSING_TASK_NAME);
            return;
        }
        
        LocalDate taskDate = notificationDate.getValue();
        int       hour     = notificationHour.getValue();
        int       minute   = notificationMinute.getValue();
        LocalDateTime taskDateTime = LocalDateTime.of(taskDate.getYear(),
                                                      taskDate.getMonth(),
                                                      taskDate.getDayOfMonth(),
                                                      hour,
                                                      minute);
        if (taskDateTime.isBefore(LocalDateTime.now())) {
            AlertForm.warningEditAlert(ViewConstants.ALERT_INCORRECT_TIME);
            return;
        }
        Date        notificationDate = Date.from(taskDateTime.atZone(ZoneId.systemDefault()).toInstant());
        TaskFactory taskFactory      = new TaskFactory();
        Task task = taskFactory.create(taskRowManager.getTaskRow().getId(),
                                       taskName.getText(),
                                       taskDescription.getText(),
                                       notificationDate,
                                       Status.SCHEDULED);
        controller.updateTask(task);
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
    
    public void clickCancelButton(ActionEvent event) {
        
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
