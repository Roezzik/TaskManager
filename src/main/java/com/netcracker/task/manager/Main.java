package com.netcracker.task.manager;


import com.netcracker.task.manager.controller.Controller;
import com.netcracker.task.manager.view.utils.ViewPathConstants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.netcracker.task.manager.view.utils.ViewConstants;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = Controller.getInstance();
        controller.initAllTask();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ViewPathConstants.PATH_TO_MAIN_FORM_VIEW));
        Parent     root       = fxmlLoader.load();

        primaryStage.setTitle(ViewConstants.TITLE_TO_MAIN_FORM_VIEW);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            controller.write();
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}