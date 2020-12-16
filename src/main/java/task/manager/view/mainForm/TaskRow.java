package task.manager.view.mainForm;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import task.manager.controller.PropertyParser;
import task.manager.model.Task;
import task.manager.view.utils.ViewConstants;
import task.manager.view.editForm.EditTaskForm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TaskRow {
    
    private final int                            id;
    private final SimpleObjectProperty<CheckBox> taskCheckBox;
    private final SimpleStringProperty           taskName;
    private final SimpleStringProperty           taskDescription;
    private final SimpleStringProperty           notificationDate;
    private final SimpleStringProperty           taskStatus;
    private final SimpleObjectProperty<Button>   taskEditButton;
    
    
    public TaskRow(Task task) {
        
        id = task.getId();
        taskCheckBox = new SimpleObjectProperty<>(new CheckBox());
        taskName = new SimpleStringProperty(task.getName());
        taskDescription = new SimpleStringProperty(task.getDescription());
        notificationDate = new SimpleStringProperty(formatNotificationDate(task));
        taskStatus = new SimpleStringProperty(task.getStatus().getTitle());
        taskEditButton = new SimpleObjectProperty<>(createEditButton(task));
    }
    
    public int getId() {
        return id;
    }
    
    public CheckBox getTaskCheckBox() {
        return taskCheckBox.get();
    }
    
    public SimpleObjectProperty<CheckBox> taskCheckBoxProperty() {
        return taskCheckBox;
    }
    
    public void setTaskCheckBox(CheckBox taskCheckBox) {
        this.taskCheckBox.set(taskCheckBox);
    }
    
    public String getTaskName() {
        return taskName.get();
    }
    
    public SimpleStringProperty taskNameProperty() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName.set(taskName);
    }
    
    public String getTaskDescription() {
        return taskDescription.get();
    }
    
    public SimpleStringProperty taskDescriptionProperty() {
        return taskDescription;
    }
    
    public void setTaskDescription(String taskDescription) {
        this.taskDescription.set(taskDescription);
    }
    
    public String getNotificationDate() {
        return notificationDate.get();
    }
    
    public SimpleStringProperty notificationDateProperty() {
        return notificationDate;
    }
    
    public void setNotificationDate(String notificationDate) {
        this.notificationDate.set(notificationDate);
    }
    
    public String getTaskStatus() {
        return taskStatus.get();
    }
    
    public SimpleStringProperty taskStatusProperty() {
        return taskStatus;
    }
    
    public void setTaskStatus(String taskStatus) {
        this.taskStatus.set(taskStatus);
    }
    
    public Button getTaskEditButton() {
        return taskEditButton.get();
    }
    
    public SimpleObjectProperty<Button> taskEditButtonProperty() {
        return taskEditButton;
    }
    
    public void setTaskEditButton(Button taskEditButton) {
        this.taskEditButton.set(taskEditButton);
    }
    
    private String formatNotificationDate(Task task) {
        
        Date             date       = task.getDate();
        SimpleDateFormat simpleDate = new SimpleDateFormat(PropertyParser.getPropertyValue("DATE_FORMAT"));
        return simpleDate.format(date);
    }
    
    private Button createEditButton(Task task) {
        final Button    cellButton  = new Button();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(ViewConstants.PATH_TO_EDIT_BUTTON_IMAGE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert inputStream != null;
        Image     image = new Image(inputStream);
        ImageView view  = new ImageView(image);
        cellButton.setGraphic(view);
        cellButton.setStyle(ViewConstants.STYLE_FOR_EDIT_BUTTON);
        cellButton.setMinSize(ViewConstants.MIN_SIZE_EDIT_BUTTON, ViewConstants.MIN_SIZE_EDIT_BUTTON);
        cellButton.setMaxSize(ViewConstants.MAX_SIZE_EDIT_BUTTON, ViewConstants.MAX_SIZE_EDIT_BUTTON);
        cellButton.setPrefSize(ViewConstants.PREF_SIZE_EDIT_BUTTON, ViewConstants.PREF_SIZE_EDIT_BUTTON);
        cellButton.setOnAction(t -> {
            try {
                openEditForm();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("selectedData: " + task);
        });
        return cellButton;
    }
    
    public void openEditForm() throws Exception {
        EditTaskForm editTaskForm = new EditTaskForm();
        Stage        stage        = new Stage();
        editTaskForm.start(stage);
    }
}