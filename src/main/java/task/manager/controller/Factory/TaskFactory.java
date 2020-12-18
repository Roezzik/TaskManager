package task.manager.controller.Factory;

import task.manager.model.Status;
import task.manager.model.Task;

import java.util.Date;

public class TaskFactory implements TasklFactoryInterface {

    @Override
    public Task create(int id, String name, String description, Date date, Status status){
        return new Task(id, name, description, date, status);
    }
}
