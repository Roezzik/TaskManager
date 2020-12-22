package task.manager.view.editForm;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import task.manager.controller.Controller;
import task.manager.controller.factory.TaskFactory;
import task.manager.model.Status;
import task.manager.model.Task;
import task.manager.view.addForm.AddTaskController;
import task.manager.view.utils.AlertForm;
import task.manager.view.utils.TaskRowManager;
import task.manager.view.utils.ViewConstants;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class EditTaskController {
    
    public EditTaskController() throws IOException {
    }
    
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

    // todo where is access modifier
    Controller     controller     = Controller.getInstance();
    TaskRowManager taskRowManager = TaskRowManager.getInstance();
    
    @FXML
    private void initialize() {
        
        initData();
        initDatePicker();
        initSpinner();
    }
    
    private void initData() {
        
        taskName.setText(taskRowManager.getTaskRow().getTaskName());
        taskDescription.setText(taskRowManager.getTaskRow().getTaskDescription());
        
    }
    
    private void initDatePicker() {
        
        LocalDate today = LocalDate.now();
        notificationDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
        
        Date      taskDate      = controller.getTask(taskRowManager.getTaskRow().getId()).getDate();
        LocalDate taskLocalDate = taskDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        notificationDate.setValue(taskLocalDate);
    }
    
    private void initSpinner() {
        
        AddTaskController.initSpinnerFactory(notificationHour, notificationMinute);
        
        Date          taskDate     = controller.getTask(taskRowManager.getTaskRow().getId()).getDate();
        LocalDateTime taskDateTime = LocalDateTime.ofInstant(taskDate.toInstant(), ZoneId.systemDefault());
        
        notificationHour.getValueFactory().setValue(taskDateTime.getHour());
        notificationMinute.getValueFactory().setValue(taskDateTime.getMinute());
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
