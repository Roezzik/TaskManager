package com.netcracker.task.manager.view.notificationForm;


import java.io.IOException;

import com.netcracker.task.manager.view.editForm.EditTaskController;
import com.netcracker.task.manager.view.mainForm.MainFormController;
import com.netcracker.task.manager.view.mainForm.TaskRow;
import com.netcracker.task.manager.view.utils.TaskRowManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import com.netcracker.task.manager.controller.Controller;
import com.netcracker.task.manager.controller.sheduler.NotificationScheduler;
import com.netcracker.task.manager.view.editForm.EditTaskForm;
import com.netcracker.task.manager.view.utils.Refresher;


public class NotificationTaskController {
    
    private static int countScene;
    
    private int currentScene;
    
    private Controller controller;
    
    @FXML
    void initialize() throws IOException {
        controller = Controller.getInstance();
        currentScene = countScene++;
        //System.out.println(currentScene);
    }
    
    @FXML
    void clickButtonCancelled(ActionEvent event) {
        controller.cancelTask(NotificationScheduler.NotificationHistory.getTaskIdList(currentScene));
        closeScene();
    }
    
    @FXML
    void clickButtonDone(ActionEvent event) {
        controller.doneTask(NotificationScheduler.NotificationHistory.getTaskIdList(currentScene));
        closeScene();
    }
    
    @FXML
    void clickButtonPostponed(ActionEvent event) throws Exception {
        closeScene();
        Stage stage = new Stage();
        Refresher.getInstance()
                 .getMainFormController()
                 .getTaskRow(NotificationScheduler.NotificationHistory.getTaskIdList(currentScene));
        //System.out.println(currentScene);
        //System.out.println(NotificationScheduler.NotificationHistory.getTaskIdList(currentScene));
        (new EditTaskForm()).start(stage);
        stage.setOnCloseRequest(we -> Refresher.getInstance().getMainFormController().refreshTable());
    }
    
    private void closeScene() {
        NotificationScheduler.NotificationHistory.getTaskIdStageList(currentScene).close();
        Refresher.getInstance().getMainFormController().refreshTable();
    }
}
