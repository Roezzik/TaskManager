package com.netcracker.task.manager.controller.sheduler;

import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;
import javafx.stage.Stage;

import java.util.*;

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

        long timeNow = new Date().getTime();

        for (Task task : listTask) {

            if (task.getDate().getTime() - timeNow < 0 ){
                continue;
            }

            if(task.getStatus() != Status.SCHEDULED && task.getStatus() != Status.POSTPONED) {
                continue;
            }

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
    public void postponeNotification(Task task) {
        removeNotification(task.getId());
        addNotification(task);
    }

    // then I'll change it to singleton
    public static class NotificationHistory {

        private static ArrayList<Integer> taskIdList = new ArrayList<>();

        private static ArrayList<Stage> taskIdStageList = new ArrayList<>();

        public static int getTaskIdList(int taskId) {
            return taskIdList.get(taskId);
        }

        public static void addTaskIdList(int taskId) {
            taskIdList.add(taskId);
        }

        public static Stage getTaskIdStageList(int taskId) {
            return taskIdStageList.get(taskId);
        }

        public static void addTaskIdStageList(Stage taskId) {
            taskIdStageList.add(taskId);
        }
    }

}

