package task.manager.controller;


import task.manager.controller.io.TextMarshaller;
import task.manager.controller.sheduller.ScheduledTask;
import task.manager.controller.sheduller.WorkingWithNotifications;
import task.manager.model.Journal;
import task.manager.model.Status;
import task.manager.model.Task;
import task.manager.view.utils.ViewPathConstants;

import java.io.IOException;
import java.util.*;


public class Controller {
    
    private static Controller instance;
    private final  Journal    journal;
    private WorkingWithNotifications workingWithNotifications;
    
    private Controller() throws IOException {
        //journal = new Journal();
        journal = TextMarshaller.getInstance().read(ViewPathConstants.FILE_PATH);
        IdGenerator.getInstance(getLastTaskId());
        //start all tasks
        (workingWithNotifications = WorkingWithNotifications.getInstance()).startAllTasks(journal.getListAllTasks());

    }
    
    public static synchronized Controller getInstance() throws IOException {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    
    public List<Task> getAllTasks() {
        return journal.getListAllTasks();
    }
    
    public void addTask(Task task) {
        journal.addTask(task);
        workingWithNotifications.addNotification(task);
    }
    
    public void updateTask(Task task) {
        journal.updateTask(task);
        workingWithNotifications.postponedNotification(task);
    }
    
    public void deleteTask(int taskId) {
        journal.deleteTask(taskId);
        workingWithNotifications.removeNotification(taskId);
    }
    
    public int getLastTaskId() {
        Map<Integer, Task> tasksMap = journal.getTasksMap();
        return tasksMap.keySet().stream().max(Integer::compareTo).orElse(0);
    }

    public void cancelTask(int taskId){
        getTask(taskId).setStatus(Status.CANCELLED);
        workingWithNotifications.removeNotification(taskId);
    }

    public void doneTask(int taskId){
        getTask(taskId).setStatus(Status.DONE);
        workingWithNotifications.removeNotification(taskId);
    }

    public Task getTask(int taskId){
        return journal.getTask(taskId);
    }

    public void write(){
        TextMarshaller.getInstance().write(journal, ViewPathConstants.FILE_PATH);
    }
}