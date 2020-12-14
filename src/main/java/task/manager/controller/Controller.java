package task.manager.controller;

import task.manager.controller.io.TextMarshaller;
import task.manager.model.Journal;
import task.manager.model.Task;

import java.io.IOException;
import java.util.List;


public class Controller {
    
    private static Controller instance;
    private final  Journal    journal;
    
    private Controller() throws IOException {
        //journal = new Journal();
        journal = TextMarshaller.getInstance().read(Setting.getPropertyValue("FILE_PATH"));
    }
    
    public static synchronized Controller getInstance() throws IOException {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void addTask(Task task) {
        journal.addTask(task);
    }

    public Journal getJournal() {
        return journal;
    }
    
    public List<Task> getAllTasks() {
        return journal.getListAllTasks();
    }
}
