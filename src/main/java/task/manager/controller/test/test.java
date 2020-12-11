package task.manager.controller.test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import task.manager.controller.Setting;
import task.manager.controller.io.TextMarshaller;
import task.manager.model.Journal;
import task.manager.model.Task;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Timer;



public class test /*extends Application */{

   //@Override
    public void start(Stage stage) throws Exception {

     /*  Thread thread = new Thread(() -> );
       thread.start();*/
        Stage stage1  = new Stage();
        stage1.show();
        //Platform.runLater( );
    }

    public static void main(String[] args) {


       // Stage stage1;
       // stage1 = new Stage();
        //stage1.show();

        Platform.startup(()->new Stage().show());
        Platform.runLater(()->new Stage().show());
        Platform.runLater(()->new Stage().show());
        //Platform.startup(()->new Stage().show());


        /*try {
            ggg();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


    public static void ggg() throws IOException {

        Map<Integer, Task> tasksMap  = TextMarshaller.getInstance().read(Setting.getPropertyValue("FILE_PATH")).getTasksMap();
        Journal journal = new Journal();
        tasksMap.values().forEach(task -> journal.addTask(task));


        WorkingWithNotifications.startAllTasks(journal);
        System.out.println(WorkingWithNotifications.timers);


        //WorkingWithNotifications.cancelNotification(1, journal);
        System.out.println(WorkingWithNotifications.timers);

        //System.out.println(new Date().getTime());

       // Timer time = new Timer();

        //time.schedule(st, 3000);

        // по возможности создать новый класс котоорый будет, создават, продлять, отменять задачи

       /* for(Task t: tasksMap.values()){
            new Timer().schedule( new ScheduledTask(t),  t.getDate().getTime() - new Date().getTime() );
        }*/



    }


}
