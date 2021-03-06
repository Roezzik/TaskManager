package com.netcracker.task.manager.controller.sheduler;


import com.netcracker.task.manager.controller.DateConverter;
import com.netcracker.task.manager.controller.exception.NotificationException;
import com.netcracker.task.manager.model.Task;
import com.netcracker.task.manager.view.notificationForm.NotificationTaskForm;
import com.netcracker.task.manager.view.utils.ViewConstants;
import javafx.application.Platform;

import java.util.TimerTask;


/**
 * ScheduledTask - running each task in a separate thread
 */
public class ScheduledTask extends TimerTask {
    
    private final Task task;
    
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
            } catch (NotificationException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(ViewConstants.ERROR_SCHEDULED_TASK);
            }
        });
    }
}

