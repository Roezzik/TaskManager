package task.manager.model;

import java.util.Date;

public class TaskFactory implements TasklFactoryInterface{

    @Override
    public Task createTask(int id, String name, String description, Date date, Status status){
        return new Task(id, name, description, date, status);
    }
}
