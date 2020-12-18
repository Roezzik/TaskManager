package task.manager.controller;


import task.manager.controller.io.TextMarshaller;
import task.manager.model.Journal;
import task.manager.model.Status;
import task.manager.model.Task;
import task.manager.view.utils.ViewPathConstants;

import java.io.IOException;
import java.util.*;


public class Controller {
    
    private static Controller instance;
    private final  Journal    journal;
    
    private Controller() throws IOException {
        //journal = new Journal();
        journal = TextMarshaller.getInstance().read(ViewPathConstants.FILE_PATH);
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
    }
    
    public void updateTask(Task task) {
        journal.updateTask(task);
    }
    
    public void deleteTask(int taskId) {
        journal.deleteTask(taskId);
    }
    
    public int getLastTaskId() {
        Map<Integer, Task> tasksMap = journal.getTasksMap();
        return tasksMap.keySet().stream().max(Integer::compareTo).orElse(0);
    }

    public void cancelTask(int taskId){ getTask(taskId).setStatus(Status.CANCELLED); }

    public Task getTask(int taskId){
        return journal.getTask(taskId);
    }

    public void write(){
        TextMarshaller.getInstance().write(journal, ViewPathConstants.FILE_PATH);
    }
}