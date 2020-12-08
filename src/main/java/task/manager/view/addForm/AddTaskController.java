package task.manager.view.addForm;


import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class AddTaskController {
    
    @FXML
    private TextField taskName;
    
    @FXML
    private TextField taskDescription;
    
    @FXML
    private DatePicker notificationDate;
    
    @FXML
    private TextField notificationHour;
    
    @FXML
    private TextField notificationMinute;
    
    
    //the method is an attempt to get the date and time for an alert from the form
    //can be deleted
    @FXML
    public Date getNotificationDateTime() throws ParseException {
        String           date      = notificationDate.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String           time      = (notificationHour.getText() + ":" + notificationMinute.getText());
        String           dateTime  = (date + " " + time);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return formatter.parse(dateTime);
    }
    
    //TODO
}
