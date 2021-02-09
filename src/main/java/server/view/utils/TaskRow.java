package server.view.utils;


import shared.model.Task;
import shared.constant.ViewConstants;
import shared.constant.ViewPathConstants;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import server.view.editForm.EditTaskForm;

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
    private final Refresher                      refresher;

    public TaskRow(Task task) {

        id = task.getId();
        taskCheckBox = new SimpleObjectProperty<>(new CheckBox());
        taskName = new SimpleStringProperty(task.getName());
        taskDescription = new SimpleStringProperty(task.getDescription());
        notificationDate = new SimpleStringProperty(formatNotificationDate(task));
        taskStatus = new SimpleStringProperty(task.getStatus().getTitle());
        taskEditButton = new SimpleObjectProperty<>(createEditButton());

        this.refresher = Refresher.getInstance();
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
        SimpleDateFormat simpleDate = new SimpleDateFormat(ViewConstants.DATE_FORMAT);
        return simpleDate.format(date);
    }

    private Button createEditButton() {

        final Button cellButton = new Button();

        Image image = new Image(getClass().getResource(ViewPathConstants.PATH_TO_EDIT_BUTTON_IMAGE).toString());
        cellButton.setGraphic(new ImageView(image));
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
        });
        return cellButton;
    }

    public void openEditForm() throws Exception {
        TaskRowManager.getInstance().setTaskRow(this);
        EditTaskForm editTaskForm = new EditTaskForm();
        Stage        stage        = new Stage();
        stage.setOnCloseRequest(we -> refresher.getMainFormController().refreshTable());
        editTaskForm.start(stage);
    }
}
