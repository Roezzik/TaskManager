package com.netcracker.task.manager.view.notificationForm.postponeForm;


import com.netcracker.task.manager.view.utils.ViewConstants;
import com.netcracker.task.manager.view.utils.ViewPathConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class PostponeTaskForm {
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(ViewPathConstants.PATH_TO_POSTPONE_FORM_VIEW));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(ViewConstants.TITLE_TO_POSTPONE_FORM_VIEW);
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}

