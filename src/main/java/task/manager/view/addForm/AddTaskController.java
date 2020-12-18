package task.manager.view.addForm;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import task.manager.controller.Controller;
import task.manager.controller.IdGenerator;
import task.manager.model.Status;
import task.manager.model.Task;
import task.manager.view.utils.AlertForm;
import task.manager.view.utils.ViewConstants;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class AddTaskController {
    
    public AddTaskController() throws IOException {
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
    public Button addButton;
    
    @FXML
    public Button cancelButton;
    
    Controller controller = Controller.getInstance();
    
    @FXML
    private void initialize() {
        
        initDatePicker();
        initSpinner();
    }
    
    private void initDatePicker() {
        
        LocalDate today = LocalDate.now();
        notificationDate.setValue(today);
        notificationDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                
                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }
    
    private void initSpinner() {
        
        SpinnerValueFactory<Integer> hourFactory   = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        SpinnerValueFactory<Integer> minuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        hourFactory.setWrapAround(true);
        minuteFactory.setWrapAround(true);
        
        notificationHour.setValueFactory(hourFactory);
        notificationMinute.setValueFactory(minuteFactory);
    }
    
    public void clickAddButton(ActionEvent event) {
        
        if (taskName.getText().length() == 0) {
            AlertForm.infoAddAlert(ViewConstants.ALERT_MISSING_TASK_NAME);
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
            AlertForm.infoAddAlert(ViewConstants.ALERT_INCORRECT_TIME);
            return;
        }
        Date notificationDate = Date.from(taskDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Task task = new Task(IdGenerator.getInstance(controller.getLastTaskId()).getNextId(),
                             taskName.getText(),
                             taskDescription.getText(),
                             notificationDate,
                             Status.SCHEDULED);
        controller.addTask(task);
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
    
    public void clickCancelButton(ActionEvent event) {
        
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}


