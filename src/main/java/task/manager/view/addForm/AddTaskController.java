package task.manager.view.addForm;


import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;


public class AddTaskController {
    
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
    
    @FXML
    private void initialize() {
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
    
    //TODO
}
