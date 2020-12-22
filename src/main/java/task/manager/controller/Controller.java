package task.manager.controller;


import task.manager.controller.io.Marshaller;
import task.manager.controller.io.TextMarshaller;
import task.manager.controller.sheduler.NotificationScheduler;
import task.manager.model.Journal;
import task.manager.model.Status;
import task.manager.model.Task;
import task.manager.view.utils.ViewPathConstants;

import java.io.IOException;
import java.util.*;


public class Controller {
    
    private static Controller instance;
    private final Journal               journal; // todo final? how will we load from disk backup?
    private final NotificationScheduler notificationScheduler; // todo shit happens you forget init this object, but i did it, no problem if you could not ;)
    
    // todo example how it really should be presented if you create interface
    private final Marshaller marshaller;
    
    private Controller() throws IOException { // todo noway controller throws exception and system stuck
        this.marshaller = TextMarshaller.getInstance();
        this.journal = TextMarshaller.getInstance().read(ViewPathConstants.FILE_PATH); // todo exception catch for case if journal is not available
        this.notificationScheduler = NotificationScheduler.getInstance();
    
        IdGenerator.getInstance(getLastTaskId());
        
        // refactor
        notificationScheduler.startAllTasks(journal.getListAllTasks());
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
        notificationScheduler.addNotification(task);
    }
    
    public void updateTask(Task task) {
        journal.updateTask(task);
        notificationScheduler.postponedNotification(task);
    }
    
    public void deleteTask(int taskId) {
        journal.deleteTask(taskId);
        notificationScheduler.removeNotification(taskId);
    }
    
    public int getLastTaskId() {
        Map<Integer, Task> tasksMap = journal.getTasksMap();
        return tasksMap.keySet().stream().max(Integer::compareTo).orElse(0);
    }

    public void cancelTask(int taskId){
        getTask(taskId).setStatus(Status.CANCELLED);
        notificationScheduler.removeNotification(taskId);
    }

    public void doneTask(int taskId){
        getTask(taskId).setStatus(Status.DONE);
        notificationScheduler.removeNotification(taskId);
    }

    public Task getTask(int taskId){
        return journal.getTask(taskId);
    }

    public void write(){
        TextMarshaller.getInstance().write(journal, ViewPathConstants.FILE_PATH);
    }
}