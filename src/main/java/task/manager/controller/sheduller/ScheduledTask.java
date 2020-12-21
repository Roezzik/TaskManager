package task.manager.controller.sheduller;

import javafx.application.Platform;
import task.manager.controller.DateConverter;
import task.manager.model.Task;
import task.manager.view.notificationForm.NotificationTaskForm;

import java.util.TimerTask;


public class ScheduledTask extends TimerTask {

    Task task;
    static int v =20;

    public ScheduledTask(Task task) {
        this.task = task;
    }

    @Override
    public void run() {


        v *=5;
        Platform.runLater(() -> {

            try {
                NotificationTaskForm ntf = NotificationTaskForm.getInstance();
                ntf.start();
                ntf.setLabelTaskName(task.getName());
                ntf.setLabelTaskDescription(task.getDescription());
                ntf.setLabelTime(DateConverter.getStringDate(task.getDate()));
            } catch (Exception e) {
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

