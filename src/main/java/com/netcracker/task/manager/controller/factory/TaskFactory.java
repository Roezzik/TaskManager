package com.netcracker.task.manager.controller.factory;


import com.netcracker.task.manager.controller.IdGenerator;
import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;

import java.util.Date;


/**
 * Factory pattern class for creating Journal
 *
 * @see Task
 */
public class TaskFactory {
    
    private final IdGenerator idGenerator;
    
    public TaskFactory() {
        idGenerator = IdGenerator.getInstance();
    }
    
    /**
     * Function for creating a Task
     *
     * @param id          - id of Task
     * @param name        - name of Task
     * @param description - description of Task
     * @param date        - date of notification of the Task
     * @param status      - status of Task
     * @return new Task object
     */
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
