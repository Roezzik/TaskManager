package com.netcracker.task.manager.controller;

import com.netcracker.task.manager.controller.io.Exception.CreateFileException;
import com.netcracker.task.manager.controller.io.Exception.TextMarshallerReadException;
import com.netcracker.task.manager.controller.io.Marshaller;
import com.netcracker.task.manager.controller.io.TextMarshaller;
import com.netcracker.task.manager.controller.sheduler.NotificationScheduler;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;
import com.netcracker.task.manager.view.utils.AlertForm;
import com.netcracker.task.manager.view.utils.ViewPathConstants;

import java.io.IOException;
import java.util.*;

public class Controller {

    private static Controller            instance;
    private        Journal               journal;
    private final  NotificationScheduler notificationScheduler;

    // todo example how it really should be presented if you create interface
    private final Marshaller marshaller;

    private Controller() throws IOException { // todo noway controller throws exception and system stuck
        this.marshaller = TextMarshaller.getInstance();

        try {
            this.journal = TextMarshaller.getInstance().read(ViewPathConstants.FILE_PATH); // todo exception catch for case if journal is not available
        } catch (CreateFileException e) {
            AlertForm.errorAlert(e.getErrorMessage());
        } catch (TextMarshallerReadException e) {
            AlertForm.errorAlert(e.getErrorMessage());
        }

        this.notificationScheduler = NotificationScheduler.getInstance();
        IdGenerator.getInstance(getLastTaskId());
    }

    public void initAllTask() {
        notificationScheduler.startAllTasks(journal.getListAllTasks());
    }

    public static synchronized Controller getInstance() throws IOException {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public List<Task> getAllTasks() {
        return journal.getListAllTasks();
    }

    public void addTask(Task task) {
        journal.addTask(task);
        notificationScheduler.addNotification(task);
    }

    public void updateTask(Task task) {
        journal.updateTask(task);
        notificationScheduler.postponeNotification(task);
    }

    public void deleteTask(int taskId) {
        journal.deleteTask(taskId);
        notificationScheduler.removeNotification(taskId);
    }

    public int getLastTaskId() {
        List<Task> tasksList = journal.getListAllTasks();
        return tasksList.stream().map(Task::getId).max(Integer::compareTo).orElse(0);
    }

    public void cancelTask(int taskId) {
        getTask(taskId).setStatus(Status.CANCELLED);
        notificationScheduler.removeNotification(taskId);
    }

    public void doneTask(int taskId) {
        getTask(taskId).setStatus(Status.DONE);
        notificationScheduler.removeNotification(taskId);
    }

    public Task getTask(int taskId) {
        return journal.getTask(taskId);
    }

    public void write() {
        try {
            TextMarshaller.getInstance().write(journal, ViewPathConstants.FILE_PATH);
        } catch (CreateFileException e) {
            AlertForm.errorAlert(e.getErrorMessage());
        }
    }
}