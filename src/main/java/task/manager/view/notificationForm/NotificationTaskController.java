package task.manager.view.notificationForm;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import task.manager.controller.Controller;
import task.manager.controller.sheduler.TaskNotificationList;
import task.manager.view.editForm.EditTaskForm;


public class NotificationTaskController {

    // scene number among all opened scenes
    private static int SceneNumber;

    private int thisSceneNumber;

    private int thisSceneId;

    Controller controller;

    @FXML
    void initialize() throws IOException {
        controller = Controller.getInstance();
        thisSceneNumber =  SceneNumber++;
        thisSceneId =  TaskNotificationList.getTaskIdList(  thisSceneNumber);
    }

    @FXML
    void clickButtonCancelled(ActionEvent event) {
        //System.out.println("thisSceneId"+ thisSceneId);
        controller.cancelTask(thisSceneId);
        TaskNotificationList.getTaskIdStageList(thisSceneNumber).close();
      //  for (int i=0; i< TaskNotificationList.getSize(); i++){
       //     System.out.println("dd" + TaskNotificationList.getTaskIdStageList(i));
      //  }
      //  System.out.println("thisSceneNumber"+thisSceneNumber);
      //  System.out.println("d" +   TaskNotificationList.getTaskIdStageList(thisSceneNumber));
        //refreshTable()
    }

    @FXML
    void clickButtonDone(ActionEvent event) {
      //  System.out.println(thisSceneId);
        controller.doneTask(thisSceneId);
        TaskNotificationList.getTaskIdStageList(thisSceneNumber).close();
        //refreshTable()
    }

    @FXML
    void clickButtonRestponed(ActionEvent event) throws Exception {
        EditTaskForm editTaskForm = new EditTaskForm();
        Stage stage = new Stage();
        editTaskForm.start(stage);
        TaskNotificationList.getTaskIdStageList(thisSceneNumber).close();
    }
}
