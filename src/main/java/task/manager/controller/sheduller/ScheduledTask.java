package task.manager.controller.sheduller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import task.manager.model.Task;


import java.io.File;
import java.io.IOException;
import java.net.URL;
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
                Pane root = FXMLLoader.load(url);

                /*System.out.println(root.getChildren());
                System.out.println(root.getChildren().get(2));
                System.out.println(root.getChildren().get(4));
                System.out.println(root.getChildren().get(5));
                */

                Label labelTaskName = (Label) root.getChildren().get(2).lookup("#labelTaskName");
                Label labelTaskDescription = (Label) root.getChildren().get(4).lookup("#labelTaskDescription");
                Label labelTime = (Label) root.getChildren().get(5).lookup("#labelTime");

                labelTaskName.setText(task.getName());
                labelTaskDescription.setText(task.getDescription());
                labelTime.setText(task.getDate().toString());

                root.getChildren().set(2, labelTaskName);
                root.getChildren().set(4, labelTaskDescription);
                root.getChildren().set(5, labelTime);

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

