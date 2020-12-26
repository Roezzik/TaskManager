package com.netcracker.task.manager.controller.factory;


import com.netcracker.task.manager.controller.IdGenerator;
import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;

import java.util.Date;


public class TaskFactory {
    
    IdGenerator idGenerator = IdGenerator.getInstance();
    
    public Task create(int id, String name, String description, Date date, Status status) {
        return new Task(id, name, description, date, status);
    }
    
    public Task create(String name, String description, Date date, Status status) {
        Task task = new Task();
        task.setId(idGenerator.getNextId());
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        task.setDate(date);
        return task;
    }
    
    public Task create(String name, String description, Date date) {
        Task task = new Task();
        task.setId(idGenerator.getNextId());
        task.setName(name);
        task.setDescription(description);
        task.setDate(date);
        return task;
    }
    
    public Task create(String name, Date date, Status status) {
        Task task = new Task();
        task.setId(idGenerator.getNextId());
        task.setName(name);
        task.setStatus(Status.SCHEDULED);
        task.setDate(date);
        return task;
    }
}
