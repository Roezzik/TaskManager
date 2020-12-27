package com.netcracker.task.manager.view.notificationForm;


import com.netcracker.task.manager.controller.sheduler.NotificationHistory;
import com.netcracker.task.manager.view.notificationForm.postponeForm.PostponeTaskForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.netcracker.task.manager.controller.Controller;
import com.netcracker.task.manager.view.utils.Refresher;


public class NotificationTaskController {
    
    @FXML
    private Label labelTaskName;
    
    @FXML
    private Label labelTaskDescription;
    
    @FXML
    private Label labelTime;
    
    @FXML
    private Button buttonDone;
    
    @FXML
    private Button buttonPostpone;
    
    @FXML
    private Button buttonCancel;
    
    private static int countScene;
    
    private int currentScene;
    
    private Controller controller;
    
    private NotificationHistory notificationHistory;
    
    @FXML
    void initialize() {
        notificationHistory = NotificationHistory.getInstance();
        controller = Controller.getInstance();
        currentScene = countScene++;
    }
    
    @FXML
    void clickCancelButton(ActionEvent event) {
        controller.cancelTask(notificationHistory.getTaskIdList(currentScene));
        closeScene();
    }
    
    @FXML
    void clickDoneButton(ActionEvent event) {
        controller.doneTask(notificationHistory.getTaskIdList(currentScene));
        closeScene();
    }
    
    @FXML
    void clickPostponeButton(ActionEvent event) throws Exception {
        closeScene();
        
        Refresher.getInstance()
                 .getMainFormController()
                 .setTaskRowByTaskId(notificationHistory.getTaskIdList(currentScene));
        PostponeTaskForm postponeTaskForm = new PostponeTaskForm();
        Stage            stage            = new Stage();
        postponeTaskForm.start(stage);
        stage.setOnCloseRequest(we -> Refresher.getInstance().getMainFormController().refreshTable());
    }
    
    private void closeScene() {
        notificationHistory.getTaskIdStageList(currentScene).close();
        Refresher.getInstance().getMainFormController().refreshTable();
    }
}
