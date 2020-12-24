package task.manager.view.notificationForm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import task.manager.controller.sheduler.NotificationScheduler;
import task.manager.view.utils.ViewConstants;
import task.manager.view.utils.ViewPathConstants;

import java.io.File;
import java.net.URL;

public class NotificationTaskForm {

    private Pane root;
    private Stage stage;

    private static NotificationTaskForm instance;

    private NotificationTaskForm() {
    }

    public static NotificationTaskForm getInstance() {
        if (instance == null) {
            instance = new NotificationTaskForm();
        }
        return instance;
    }

    public void start(int taskId) {

        try {
            root = FXMLLoader.load(getClass().getResource(ViewPathConstants.PATH_TO_NOTIFICATION_FORM_VIEW));
            stage = new Stage();
            stage.setTitle(ViewConstants.TITLE_TO_NOTIFICATION_FORM_VIEW);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(e -> {
                e.consume(); // we do not allow you to close on the cross
            });
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        NotificationScheduler.NotificationHistory.addTaskIdStageList(stage);
        NotificationScheduler.NotificationHistory.addTaskIdList(taskId);
    }

    public void setLabelTaskName(String textLabelTaskName) {
        Label labelTaskName = (Label) this.root.getChildren().get(2).lookup("#labelTaskName");
        labelTaskName.setText(textLabelTaskName);
        root.getChildren().set(2, labelTaskName);
    }

    public void setLabelTaskDescription(String textLabelTaskDescription) {
        Label labelTaskDescription = (Label) root.getChildren().get(4).lookup("#labelTaskDescription");
        labelTaskDescription.setText(textLabelTaskDescription);
        root.getChildren().set(4, labelTaskDescription);
    }

    public void setLabelTime(String textLabelTime) {
        Label labelTime = (Label) root.getChildren().get(5).lookup("#labelTime");
        labelTime.setText(textLabelTime);
        root.getChildren().set(5, labelTime);
    }
}

