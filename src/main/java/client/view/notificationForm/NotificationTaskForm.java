package client.view.notificationForm;


import shared.constant.ViewConstants;
import shared.constant.ViewPathConstants;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.controller.exception.NotificationException;
import server.controller.sheduler.NotificationHistory;


public class NotificationTaskForm {
    
    private       Pane                root;
    private final NotificationHistory notificationHistory;
    
    private static NotificationTaskForm instance;
    
    private NotificationTaskForm() {
        notificationHistory = NotificationHistory.getInstance();
    }
    
    public static NotificationTaskForm getInstance() {
        if (instance == null) {
            instance = new NotificationTaskForm();
        }
        return instance;
    }
    
    public void start(int taskId) throws NotificationException {
        
        Stage stage;
        try {
            root = FXMLLoader.load(getClass().getResource(ViewPathConstants.PATH_TO_NOTIFICATION_FORM_VIEW));
            stage = new Stage();
            stage.setTitle(ViewConstants.TITLE_TO_NOTIFICATION_FORM_VIEW);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(Event::consume);
            stage.show();
        } catch (Exception e) {
            throw new NotificationException(ViewConstants.ERROR_PLATFORM_RUN);
        }
        
        notificationHistory.addTaskIdStageList(stage);
        notificationHistory.addTaskIdList(taskId);
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

