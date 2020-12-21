package task.manager.controller.factory;


import task.manager.controller.Controller;
import task.manager.controller.IdGenerator;
import task.manager.model.Status;
import task.manager.model.Task;

import java.io.IOException;
import java.util.Date;


public class TaskFactory {
    
    Controller controller;
    
    public Task create(int id, String name, String description, Date date, Status status) {
        return new Task(id, name, description, date, status);
    }
    
    public Task create(String name, String description, Date date, Status status) throws IOException {
        controller = Controller.getInstance();
        Task task = new Task();
        task.setId(IdGenerator.getInstance(controller.getLastTaskId()).getNextId());
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        task.setDate(date);
        return task;
    }
    
    public Task create(String name, String description, Date date) throws IOException {
        controller = Controller.getInstance();
        Task task = new Task();
        task.setId(IdGenerator.getInstance(controller.getLastTaskId()).getNextId());
        task.setName(name);
        task.setDescription(description);
        task.setStatus(Status.SCHEDULED);
        task.setDate(date);
        return task;
    }
    
    public Task create(int id, String name, String description, Date date) {
        Task task = new Task();
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setStatus(Status.SCHEDULED);
        task.setDate(date);
        return task;
    }
}
