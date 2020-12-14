package task.manager.controller;

import task.manager.controller.io.TextMarshaller;
import task.manager.model.Journal;
import task.manager.model.Task;

import java.io.IOException;

public class Controller {
    private static Journal instance;


    private Controller() {
    }

    public static Journal getInstance() throws IOException {
        if (instance == null) {
            instance = TextMarshaller.getInstance().read(Setting.getPropertyValue("FILE_PATH"));;
        }
        return instance;
    }

    public static void addTask(Task task) throws IOException {
        getInstance().addTask(task);
    }

    public static void updateTask(Task task) throws IOException {
        getInstance().updateTask(task);
    }

    public static void deleteTask(int id) throws IOException {
        getInstance().deleteTask(id);
    }

    public static void writeJournal() throws IOException {
        TextMarshaller textMarshaller = TextMarshaller.getInstance();
        TextMarshaller.getInstance().write(getInstance(), Setting.getPropertyValue("FILE_PATH"));
    }

}
