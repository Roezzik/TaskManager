package com.netcracker.task.manager.view.notificationForm;

import com.netcracker.task.manager.controller.sheduler.NotificationHistory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import com.netcracker.task.manager.controller.Controller;
import com.netcracker.task.manager.view.editForm.EditTaskForm;
import com.netcracker.task.manager.view.utils.Refresher;


public class NotificationTaskController {
    
    private static int countScene;
    
    private int currentScene;
    
    private Controller controller;

    private NotificationHistory notificationHistory;
    
    @FXML
    void initialize()  {
        notificationHistory = NotificationHistory.getInstance();
        controller = Controller.getInstance();
        currentScene = countScene++;
    }
    
    @FXML
    void clickButtonCancelled(ActionEvent event) {
        controller.cancelTask(notificationHistory.getTaskIdList(currentScene));
        closeScene();
    }
    
    @FXML
    void clickButtonDone(ActionEvent event) {
        controller.doneTask(notificationHistory.getTaskIdList(currentScene));
        closeScene();
    }
    
    @FXML
    void clickButtonPostponed(ActionEvent event) throws Exception {
        closeScene();
        Stage stage = new Stage();
        Thread.sleep(100);
        Refresher.getInstance()
                 .getMainFormController()
                 .getTaskRowByTaskId(notificationHistory.getTaskIdList(currentScene));
        (new EditTaskForm()).start(stage);
        stage.setOnCloseRequest(we -> Refresher.getInstance().getMainFormController().refreshTable());
    }
    
    private void closeScene() {
        notificationHistory.getTaskIdStageList(currentScene).close();
        Refresher.getInstance().getMainFormController().refreshTable();
    }
}
