package com.netcracker.task.manager.controller.sheduler;


import javafx.stage.Stage;

import java.util.ArrayList;


public class NotificationHistory {
    
    private static NotificationHistory instance;
    
    private NotificationHistory() {
    }
    
    public static NotificationHistory getInstance() {
        if (instance == null) {
            instance = new NotificationHistory();
        }
        return instance;
    }
    
    private final ArrayList<Integer> taskIdList = new ArrayList<>();
    
    private final ArrayList<Stage> taskIdStageList = new ArrayList<>();
    
    public int getTaskIdList(int taskId) {
        return taskIdList.get(taskId);
    }
    
    public void addTaskIdList(int taskId) {
        taskIdList.add(taskId);
    }
    
    public Stage getTaskIdStageList(int taskId) {
        return taskIdStageList.get(taskId);
    }
    
    public void addTaskIdStageList(Stage taskId) {
        taskIdStageList.add(taskId);
    }
}
