package task.manager;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import task.manager.controller.Setting;

import java.io.File;
import java.net.URL;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("src/main/java/task/manager/view/mainForm/mainFormView.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}