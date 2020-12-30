package com.netcracker.task.manager.controller.sheduler;


import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;

import java.util.*;


/**
 * NotificationScheduler  - a class that starts all tasks
 */
public class NotificationScheduler {
    
    public static  HashMap<Integer, Timer> timers;
    private static int                     TIME_DELAY = 50;
    
    private static NotificationScheduler instance;
    
    private NotificationScheduler() {
        timers = new HashMap<>();
        NotificationHistory notificationHistory = NotificationHistory.getInstance();
    }
    
    public static NotificationScheduler getInstance() {
        if (instance == null) {
            instance = new NotificationScheduler();
        }
        return instance;
    }
    
    /**
     * running all the tasks that were passed in the sheet
     *
     * @param listTask - list of all tasks
     */
    public void startAllTasks(List<Task> listTask) {
        
        long timeNow = new Date().getTime();
        
        for (Task task : listTask) {
            
            if (task.getDate().getTime() - timeNow < 0) {
                continue;
            }
            
            if (task.getStatus() != Status.SCHEDULED && task.getStatus() != Status.POSTPONED) {
                continue;
            }
            
            timers.put(task.getId(), new Timer());
            timers.get(task.getId()).schedule(new ScheduledTask(task),
                                              task.getDate().getTime() - new Date().getTime() + TIME_DELAY);
            TIME_DELAY = TIME_DELAY % 2 == 0 ? TIME_DELAY / 2 : TIME_DELAY * 2;
        }
    }
    
    /**
     * adding 1 task to the list of started tasks
     *
     * @param task - a task that should be added to the number of started ones
     */
    public void addNotification(Task task) {
        long timeNow = new Date().getTime();
        if (task.getDate().getTime() - timeNow < 0) {
            return;
        }
        
        if (task.getStatus() != Status.SCHEDULED && task.getStatus() != Status.POSTPONED) {
            return;
        }
        
        timers.put(task.getId(), new Timer());
        timers.get(task.getId()).schedule(new ScheduledTask(task),
                                          task.getDate().getTime() - new Date().getTime() + TIME_DELAY);
    }
    
    /**
     * removing a task from the running list
     *
     * @param taskId - a task to delete
     */
    public void removeNotification(int taskId) {
        if (timers.get(taskId) != null) {
            timers.get(taskId).cancel();
            timers.get(taskId).purge();
            timers.keySet().removeIf(key -> key == taskId);
        }
    }
    
    /**
     * change the time settings of tasks
     *
     * @param task - the task you wish to modify
     */
    public void postponeNotification(Task task) {
        removeNotification(task.getId());
        addNotification(task);
    }
    
}

