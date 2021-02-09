package server.view.utils;


/**
 * Class for selecting a row with a task
 */
public class TaskRowManager {
    
    private static TaskRowManager instance;
    private        TaskRow        taskRow;
    
    private TaskRowManager() {
    }
    
    public static TaskRowManager getInstance() {
        if (instance == null) {
            instance = new TaskRowManager();
        }
        return instance;
    }
    
    public TaskRow getTaskRow() {
        return taskRow;
    }
    
    public void setTaskRow(TaskRow taskRow) {
        this.taskRow = taskRow;
    }
}
