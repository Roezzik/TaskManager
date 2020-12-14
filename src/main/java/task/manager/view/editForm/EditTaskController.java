package task.manager.view.editForm;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import task.manager.controller.Controller;
import task.manager.model.Task;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;


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
    
    @FXML
    private void initialize() {
    
    }
    
    public void clickCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
//    public void showtask(Task task) {
//        name.setText(task.getName());
//        desc.setText(task.getDescription());
//        date.setValue(task.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//    }

//    public void clickedit() throws IOException {
//        if (name.getText().equals("") || desc.getText().equals("")) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Add eroor");
//            alert.setHeaderText(null);
//            alert.setContentText("\n" + "Fill in all the fields");
//            alert.showAndWait();
//        } else {
//            String names       = name.getText();
//            String description = desc.getText();
//            Date   data        = Date.valueOf(date.getValue());
//            //task = new Task((int)(Math.random()*100), names, description, data, SCHEDULED);
//            //Controller.addTask(task);
//            Stage stage = (Stage) edit.getScene().getWindow();
//            stage.close();
//        }
//    }

}
