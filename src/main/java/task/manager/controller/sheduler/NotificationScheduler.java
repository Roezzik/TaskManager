package task.manager.controller.sheduler;

import task.manager.model.Status;
import task.manager.model.Task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

public class NotificationScheduler {

    public static HashMap<Integer, Timer> timers = new HashMap<>();
    private static int TIME_DELAY = 50;

    private static NotificationScheduler instance;

    private NotificationScheduler() {
    }

    public static NotificationScheduler getInstance() {
        if (instance == null) {
            instance = new NotificationScheduler();
        }
        return instance;
    }

    public void startAllTasks(List<Task> listTask) {

        // after come up with a normal way
       /* for (int i = listTask.size()-1; i>=0; i--){*/
     /*   for (int i = 0; i< listTask.size(); i++ ){
            if ( listTask.get(i).getDate().getTime() - new Date().getTime() < 0 || (listTask.get(i).getStatus() != Status.SCHEDULED && listTask.get(i).getStatus() != Status.POSTPONED) )
                continue;
            TaskNotificationList.addTaskIdList(listTask.get(i).getId());
        }*/

        for (Task task : listTask) {
            // 2 разных условия
            if (task.getDate().getTime() - new Date().getTime() < 0 || (task.getStatus() != Status.SCHEDULED && task.getStatus() != Status.POSTPONED) )
                continue;

            timers.put(task.getId(), new Timer());
            timers.get(task.getId()).schedule(new ScheduledTask(task), task.getDate().getTime() - new Date().getTime() + TIME_DELAY);
            TIME_DELAY = TIME_DELAY % 2 == 0 ? TIME_DELAY / 2 : TIME_DELAY * 2;
        }
    }

    public void addNotification(Task task) {
        timers.put(task.getId(), new Timer());
        timers.get(task.getId()).schedule(new ScheduledTask(task), task.getDate().getTime() - new Date().getTime() + TIME_DELAY);
    }

    //cancel, done, delete
    public void removeNotification(int taskId) {
        if (timers.get(taskId) != null) {
            timers.get(taskId).cancel();
            timers.get(taskId).purge();
            timers.keySet().removeIf(key -> key == taskId); //  remove task from the map
        }
    }

    // no tested (it is necessary to write in the editform)

    // todo all methods should be named in imperative mood
    public void postponedNotification(Task task) {
        removeNotification(task.getId());
        addNotification(task);
    }

}

