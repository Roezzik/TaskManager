package task.manager.controller.sheduler;

import javafx.application.Platform;
import task.manager.controller.DateConverter;
import task.manager.model.Task;
import task.manager.view.notificationForm.NotificationTaskForm;

import java.util.TimerTask;


public class ScheduledTask extends TimerTask {

    Task task; // todo all guys from this package can take me and change?
    //static int v =20; // todo what is it?

    public ScheduledTask(Task task) {
        this.task = task;
    }

    @Override
    public void run() {


       // v *=5; // todo who am i?
        Platform.runLater(() -> {

            try {
                NotificationTaskForm ntf = NotificationTaskForm.getInstance();
                System.out.println("x" + task.getId());
                ntf.start(task.getId());
              //  TaskNotificationList.addTaskIdList(task.getId());
                ntf.setLabelTaskName(task.getName());
                ntf.setLabelTaskDescription(task.getDescription());
                ntf.setLabelTime(DateConverter.getStringDate(task.getDate()));
            } catch (Exception e) {
                e.printStackTrace(); // todo ok thanks that you do not say anything
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

