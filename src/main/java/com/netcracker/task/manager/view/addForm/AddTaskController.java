package com.netcracker.task.manager.view.addForm;


import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import com.netcracker.task.manager.controller.Controller;
import com.netcracker.task.manager.controller.IdGenerator;
import com.netcracker.task.manager.controller.factory.TaskFactory;
import com.netcracker.task.manager.view.utils.AlertForm;
import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class AddTaskController {
    
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
    
    private final Controller controller;
    
    public AddTaskController() {
        this.controller = Controller.getInstance();
    }
    
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
        
        LocalDateTime todayTime = LocalDateTime.now();
        int           hour      = todayTime.getHour();
        int           minute    = todayTime.getMinute();
        
        initSpinnerFactory(notificationHour, notificationMinute);
        
        notificationHour.getValueFactory().setValue(hour);
        notificationMinute.getValueFactory().setValue(minute);
    }
    
    public static void initSpinnerFactory(Spinner<Integer> notificationHour, Spinner<Integer> notificationMinute) {
        
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
            AlertForm.warningAddAlert(ViewConstants.ALERT_INCORRECT_TIME);
            return;
        }
        Date        notificationDate = Date.from(taskDateTime.atZone(ZoneId.systemDefault()).toInstant());
        TaskFactory taskFactory      = new TaskFactory();
        Task task = taskFactory.create(IdGenerator.getInstance(controller.getLastTaskId()).getNextId(),
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


