package task.manager.controller.test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import task.manager.model.Task;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {

    Task task;

    public ScheduledTask(Task task) {
        this.task = task;
    }

    @Override
    public void run() {

        Platform.runLater(() -> {
            try {
                URL url = new File("src/main/java/task/manager/view/notificationForm/notificationTaskView.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Task notification ");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

      /*  //output to the console
       System.out.println("-------------------------------");
            System.out.println(task.getId());
            System.out.println(task.getName());
            System.out.println(task.getDescription());
            System.out.println(task.getDate());
            System.out.println(task.getStatus());
        System.out.println("-------------------------------");*/
    }
}

