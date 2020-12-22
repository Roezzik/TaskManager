package task.manager.controller.sheduler;

import javafx.stage.Stage;

import java.util.ArrayList;

// todo i can not understand two things
// 1. all is static?
// 2. the main goal of this class?
public class TaskNotificationList {

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

    public static int getSize(){
        return taskIdList.size();
    }
}
