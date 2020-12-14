package task.manager;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import task.manager.controller.Controller;
import task.manager.controller.Setting;
import task.manager.model.Journal;
import task.manager.model.Status;
import task.manager.model.Task;
import task.manager.view.mainForm.MainFormController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Journal journal = Controller.getInstance();
        URL url = new File("src/main/java/task/manager/view/mainForm/mainFormView.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new javafx.event.EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    Controller.writeJournal();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
//        Date date = new Date(12);
//        Task task = new Task(12, "q11", "qq", date, Status.SCHEDULED);
//        Task task1 = new Task(12, "11q", "dsa", date, Status.SCHEDULED);
//        Controller.addTask(task);
//        Controller.updateTask(task1);
//        Controller.deleteTask(12);
//        System.out.println(Controller.getInstance());
        launch(args);
    }
}