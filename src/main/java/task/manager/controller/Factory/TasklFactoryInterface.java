package task.manager.controller.Factory;

import task.manager.model.Status;
import task.manager.model.Task;

import java.util.Date;

public interface TasklFactoryInterface {
    Task create(int id, String name, String description, Date date, Status status);
}
