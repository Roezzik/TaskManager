package task.manager.view.addForm;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import task.manager.controller.Controller;
import task.manager.controller.Setting;
import task.manager.controller.io.TextMarshaller;
import task.manager.model.Task;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;

import static task.manager.model.Status.SCHEDULED;


public class AddTaskController {

    @FXML
    public Button add;

    @FXML
    private TextField taskName;
    
    @FXML
    private TextField taskDescription;
    
    @FXML
    private DatePicker notificationDate;
    
    @FXML
    private Spinner<Integer> notificationHour;
    
    @FXML
    private Spinner<Integer> notificationMinute;

    Controller controller;

    @FXML
    private void initialize() throws IOException {
        controller = new Controller();
        initDatePicker();
        initSpinner();
    }

//    public Date getNotificationDateTime() throws ParseException {
//        //TODO: rewrite the method for getting the date
//
//    }
    
    @FXML
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
        
        notificationHour.setValueFactory(hourFactory);
        notificationMinute.setValueFactory(minuteFactory);
    }

    public void clickadd(ActionEvent actionEvent) throws ParseException, IOException {
        if (taskName.getText().equals("") || taskDescription.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add eroor");
            alert.setHeaderText(null);
            alert.setContentText("\n" + "Fill in all the fields");
            alert.showAndWait();
        } else {
            String name = taskName.getText();
            String description = taskDescription.getText();
            Date date = Date.valueOf(notificationDate.getValue());
            Timestamp timestamp = new Timestamp(date.getTime());
            timestamp.setHours(notificationHour.getValue());
            timestamp.setMinutes(notificationMinute.getValue());
            System.out.println(timestamp);
            Task task = new Task(1, name, description, timestamp, SCHEDULED);
            controller.addTask(task);
            Stage stage = (Stage) add.getScene().getWindow();
            stage.close();
//            TextMarshaller textMarshaller = TextMarshaller.getInstance();
//            textMarshaller.write(controller.get(), Setting.getPropertyValue("FILE_PATH"));
        }
    }


    //TODO
}
