package task.manager.controller;

import task.manager.controller.io.TextMarshaller;
import task.manager.model.Journal;
import task.manager.model.Task;

import java.io.IOException;

public class Controller {
    private Journal journal;


    public Controller() throws IOException {
        journal = TextMarshaller.getInstance().read(Setting.getPropertyValue("FILE_PATH"));
    }

    public void addTask(Task task){
        this.journal.addTask(task);
    }

    public Journal get(){
        return journal;
    }
}
