package com.netcracker.task.manager.view.notificationForm;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import com.netcracker.task.manager.controller.Controller;
import com.netcracker.task.manager.controller.sheduler.NotificationScheduler;
import com.netcracker.task.manager.view.editForm.EditTaskForm;
import com.netcracker.task.manager.view.utils.Refresher;


public class NotificationTaskController {

    private static int SceneNumber;

    private int thisSceneNumber;

    private Controller controller;

    @FXML
    void initialize() throws IOException {
        controller = Controller.getInstance();
        thisSceneNumber =  SceneNumber++;
    }

    @FXML
    void clickButtonCancelled(ActionEvent event) {
        controller.cancelTask(NotificationScheduler.NotificationHistory.getTaskIdList(thisSceneNumber));
        closeScene();
    }

    @FXML
    void clickButtonDone(ActionEvent event) {
        controller.doneTask(NotificationScheduler.NotificationHistory.getTaskIdList(thisSceneNumber));
        closeScene();
    }

    @FXML
    void clickButtonPostponed(ActionEvent event) throws Exception {
        closeScene();
        Stage stage = new Stage();
        (new EditTaskForm()).start(stage);
        stage.setOnCloseRequest(we -> Refresher.getInstance().getMainFormController().refreshTable());
    }

    private void closeScene(){
        NotificationScheduler.NotificationHistory.getTaskIdStageList(thisSceneNumber).close();
        Refresher.getInstance().getMainFormController().refreshTable();
    }
}
