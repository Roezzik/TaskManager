package task.manager.controller.sheduler;

import javafx.application.Platform;
import task.manager.controller.DateConverter;
import task.manager.model.Task;
import task.manager.view.notificationForm.NotificationTaskForm;

import java.util.TimerTask;

public class ScheduledTask extends TimerTask {

    private Task task;

    public ScheduledTask(Task task) {
        this.task = task;
    }

    @Override
    public void run() {

        Platform.runLater(() -> {

            try {
                NotificationTaskForm ntf = NotificationTaskForm.getInstance();
                ntf.start(task.getId());
                ntf.setLabelTaskName(task.getName());
                ntf.setLabelTaskDescription(task.getDescription());
                ntf.setLabelTime(DateConverter.getStringDate(task.getDate()));
            } catch (Exception e) {
                //e.printStackTrace();??? // todo ok thanks that you do not say anything
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

