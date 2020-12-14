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

import static task.manager.model.Status.SCHEDULED;

public class EditTaskController {

    public TextField name;
    
    public TextField desc;
    
    public DatePicker date;
    
    public Button edit;
    
    public Button cancel;

    @FXML
    private void initialize() throws IOException {
        initDatePicker();
    }

    @FXML
    private void initDatePicker() {
        LocalDate today = LocalDate.now();
        date.setValue(today);
        date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }
    public void showtask(Task task){
        name.setText(task.getName());
        desc.setText(task.getDescription());
        date.setValue(task.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

    public void clickedit() throws IOException {
        if (name.getText().equals("") || desc.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add eroor");
            alert.setHeaderText(null);
            alert.setContentText("\n" + "Fill in all the fields");
            alert.showAndWait();
        } else {
            String names = name.getText();
            String description = desc.getText();
            Date data = Date.valueOf(date.getValue());
            //task = new Task((int)(Math.random()*100), names, description, data, SCHEDULED);
            //Controller.addTask(task);
            Stage stage = (Stage) edit.getScene().getWindow();
            stage.close();
        }
    }

    public void clickcancel(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
