package client.view.notificationForm.postponeForm;


import client.view.addForm.AddTaskController;
import client.view.utils.TaskRowManager;
import shared.factory.TaskFactory;
import shared.model.Status;
import shared.model.Task;
import shared.utils.AlertForm;
import shared.constant.ViewConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import server.controller.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;


public class PostponeTaskController {
    
    @FXML
    public Button button15min;
    
    @FXML
    public Button button30min;
    
    @FXML
    public Button button1hour;
    
    @FXML
    public Button button3hours;
    
    @FXML
    public Button button6hours;
    
    @FXML
    public Button button1day;
    
    @FXML
    public Button buttonPostpone;
    
    @FXML
    private DatePicker notificationDate;
    
    @FXML
    private Spinner<Integer> notificationHour;
    
    @FXML
    private Spinner<Integer> notificationMinute;
    
    private final Controller     controller;
    private final TaskRowManager taskRowManager;
    private final Calendar       calendar;
    
    public PostponeTaskController() {
        this.controller = Controller.getInstance();
        this.taskRowManager = TaskRowManager.getInstance();
        this.calendar = Calendar.getInstance();
    }
    
    @FXML
    private void initialize() {
        postponeTask();
        initDatePicker();
        initSpinner();
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
    
    private void postponeTask() {
        
        Task task = controller.getTask(taskRowManager.getTaskRow().getId());
        
        button15min.setOnAction(event -> {
            addTime(task, 15);
            closeStage((Stage) button15min.getScene().getWindow());
        });
        
        button30min.setOnAction(event -> {
            addTime(task, 30);
            closeStage((Stage) button30min.getScene().getWindow());
        });
        
        button1hour.setOnAction(event -> {
            addTime(task, 60);
            closeStage((Stage) button1hour.getScene().getWindow());
        });
        
        button3hours.setOnAction(event -> {
            addTime(task, 180);
            closeStage((Stage) button3hours.getScene().getWindow());
        });
        
        button6hours.setOnAction(event -> {
            addTime(task, 360);
            closeStage((Stage) button6hours.getScene().getWindow());
        });
        
        button1day.setOnAction(event -> {
            addTime(task, 1440);
            closeStage((Stage) button1day.getScene().getWindow());
        });
        
        buttonPostpone.setOnAction(event -> clickPostponeButton());
    }
    
    private void addTime(Task task, int minutesAmount) {
        calendar.setTime(task.getDate());
        calendar.add(Calendar.MINUTE, minutesAmount);
        Date          currentTaskDate = calendar.getTime();
        LocalDateTime taskDateTime    = LocalDateTime.ofInstant(currentTaskDate.toInstant(), ZoneId.systemDefault());
        if (taskDateTime.isBefore(LocalDateTime.now())) {
            AlertForm.warningPostponeAlert(ViewConstants.ALERT_INCORRECT_POSTPONE);
            LocalDateTime nowDateTime = LocalDateTime.now();
            calendar.set(Calendar.YEAR, nowDateTime.getYear());
            calendar.set(Calendar.MONTH, nowDateTime.getMonth().getValue() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, nowDateTime.getDayOfMonth());
            calendar.set(Calendar.HOUR_OF_DAY, nowDateTime.getHour());
            calendar.set(Calendar.MINUTE, nowDateTime.getMinute());
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.MINUTE, minutesAmount);
        }
        Date        newNotificationDate = calendar.getTime();
        TaskFactory taskFactory         = new TaskFactory();
        task = taskFactory.create(taskRowManager.getTaskRow().getId(),
                                  taskRowManager.getTaskRow().getTaskName(),
                                  taskRowManager.getTaskRow().getTaskDescription(),
                                  newNotificationDate,
                                  Status.POSTPONED);
        controller.updateTask(task);
    }
    
    public void clickPostponeButton() {
        
        LocalDate taskDate = notificationDate.getValue();
        LocalDateTime taskDateTime = LocalDateTime.of(taskDate.getYear(),
                                                      taskDate.getMonth(),
                                                      taskDate.getDayOfMonth(),
                                                      notificationHour.getValue(),
                                                      notificationMinute.getValue());
        if (taskDateTime.isBefore(LocalDateTime.now())) {
            AlertForm.warningEditAlert(ViewConstants.ALERT_INCORRECT_TIME);
            return;
        }
        Date        newNotificationDate = Date.from(taskDateTime.atZone(ZoneId.systemDefault()).toInstant());
        TaskFactory taskFactory         = new TaskFactory();
        Task task = taskFactory.create(taskRowManager.getTaskRow().getId(),
                                       taskRowManager.getTaskRow().getTaskName(),
                                       taskRowManager.getTaskRow().getTaskDescription(),
                                       newNotificationDate,
                                       Status.POSTPONED);
        controller.updateTask(task);
        Stage stage = (Stage) buttonPostpone.getScene().getWindow();
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
    
    private void closeStage(Stage stage) {
        stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
}
