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

    // здесь тоолько отображаем вьюху и задаем ей параметры имя, дата ...  , а функционал всех кнопок в нотификатионклассконтроллер
    @Override
    public void run() {

        // это работает !!!
        Platform.runLater(()->new Stage().show());








       /* try {
            new g().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        /*Platform.startup(()->   Platform.runLater(()-> {
            g();
                    //new anotherApp().start(new Stage());
        }) );
*/


                /* Platform.startup(() ->
                 g());
                 Platform.exit();*/
                     // This block will be executed on JavaFX Thread
                 //Platform.exit();




      /*  System.out.println("-------------------------------");
            System.out.println(task.getId());
            System.out.println(task.getName());
            System.out.println(task.getDescription());
            System.out.println(task.getDate());
            System.out.println(task.getStatus());
        System.out.println("-------------------------------");*/
    }

   /* @FXML
    public void g(){
        try {
            URL url = null;
            Parent root = null;
            url = new File("src/main/java/task/manager/view/notificationForm/notificationTaskView.fxml" ).toURI().toURL();
            root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Task notification ");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
class g extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("src/main/java/task/manager/view/mainForm/mainFormView.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}

