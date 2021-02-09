package server.controller.sheduler;


import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * NotificationHistory - class that stores forms and their controllers
 */
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
    
    /**
     * method for returning the task id by the transmitted number
     *
     * @param taskId task number
     * @return the task id by number in the sheet
     */
    public int getTaskIdList(int taskId) {
        return taskIdList.get(taskId);
    }
    
    /**
     * method for adding a task id to a sheet
     *
     * @param taskId task number
     */
    public void addTaskIdList(int taskId) {
        taskIdList.add(taskId);
    }
    
    /**
     * method for returning a scene by the transmitted number
     *
     * @param taskId task number
     * @return scene number by number on the sheet
     */
    public Stage getTaskIdStageList(int taskId) {
        return taskIdStageList.get(taskId);
    }
    
    /**
     * method for adding a scene to a scene sheet
     *
     * @param taskId scene for this task
     */
    public void addTaskIdStageList(Stage taskId) {
        taskIdStageList.add(taskId);
    }
}
