package task.manager.model;

import java.util.Date;

public interface TasklFactoryInterface {
    Task createTask(int id, String name, String description, Date date, Status status);
}
