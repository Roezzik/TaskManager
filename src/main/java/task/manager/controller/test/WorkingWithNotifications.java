package task.manager.controller.test;

import task.manager.model.Journal;
import task.manager.model.Status;
import task.manager.model.Task;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public abstract class WorkingWithNotifications {

    public static HashMap<Integer, Timer> timers = new HashMap<>();

    private static int TIME_DELAY = 50;

    public static void startAllTasks(Journal journal) {
        for (Task task : journal.getTasksMap().values()) {
            if (task.getDate().getTime() - new Date().getTime() < 0 || task.getStatus() == Status.CANCELLED) continue;
            timers.put(task.getId(), new Timer());
            timers.get(task.getId()).schedule(new ScheduledTask(task), task.getDate().getTime() - new Date().getTime() + TIME_DELAY);
            TIME_DELAY = TIME_DELAY % 2 == 0 ? TIME_DELAY / 2 : TIME_DELAY * 2;
        }
    }

    // отмена таски (если эл-т существует, отменяем его, удаляем отменненный из очереди )
    public static void cancelNotification(int id, Journal journal) {
        if (timers.get(id) != null) {
            timers.get(id).cancel();
            timers.get(id).purge();
            //+перезапись файла
            journal.getTask(id).setStatus(Status.CANCELLED);
            timers.keySet().removeIf(key -> key == id); // удаляем из мапы
        }
    }

    //not tested
    public static void doneNotification(int id, Journal journal) {
        if (timers.get(id) != null) {
            timers.get(id).cancel();
            timers.get(id).purge();
            //+перезапись файла
            journal.getTask(id).setStatus(Status.DONE);
            timers.keySet().removeIf(key -> key == id); // удаляем из мапы
        }
    }

    //not tested
    public static void addNotification(Task task) {
        timers.put(task.getId(), new Timer());
        timers.get(task.getId()).schedule(new ScheduledTask(task), task.getDate().getTime() - new Date().getTime() + TIME_DELAY);
    }

    //not tested
    public static void postponedNotification(Task task) {
        // останавливаем предыдущую запись и удаляем ее из потока и мапы
        if (timers.get(task.getId()) != null) {
            timers.get(task.getId()).cancel();
            timers.get(task.getId()).purge();
            //+перезапись файла
            timers.keySet().removeIf(key -> key == task.getId()); // удаляем из мапы
            // создаем с новыми параметрами
            timers.put(task.getId(), new Timer());
            timers.get(task.getId()).schedule(new ScheduledTask(task), task.getDate().getTime() - new Date().getTime());
        }
    }

}
