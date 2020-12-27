package com.netcracker.task.manager.controller.sheduler;


import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;

import java.util.*;


public class NotificationScheduler {
    
    public static  HashMap<Integer, Timer> timers     = new HashMap<>();
    private static int                     TIME_DELAY = 50;
    
    private static NotificationScheduler instance;
    
    private NotificationScheduler() {
        NotificationHistory notificationHistory = NotificationHistory.getInstance();
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
    
    public void addNotification(Task task) {
        timers.put(task.getId(), new Timer());
        timers.get(task.getId()).schedule(new ScheduledTask(task),
                                          task.getDate().getTime() - new Date().getTime() + TIME_DELAY);
    }
    
    public void removeNotification(int taskId) {
        if (timers.get(taskId) != null) {
            timers.get(taskId).cancel();
            timers.get(taskId).purge();
            timers.keySet().removeIf(key -> key == taskId);
        }
    }
    
    public void postponeNotification(Task task) {
        removeNotification(task.getId());
        addNotification(task);
    }
    
}

