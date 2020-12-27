package com.netcracker.task.manager.controller;


import com.netcracker.task.manager.controller.sheduler.NotificationScheduler;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;

import java.util.*;


public class Controller {
    
    private static Controller            instance;
    private        Journal               journal;
    private final  NotificationScheduler notificationScheduler;
    
    private Controller() {
        this.journal = new Journal();
        this.notificationScheduler = NotificationScheduler.getInstance();
    }
    
    public static synchronized Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    
    public void initAllTask() {
        notificationScheduler.startAllTasks(journal.getListAllTasks());
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
    
    public Journal getJournal() {
        return journal;
    }
    
    public void addTasks(Journal journal) {
        this.journal = journal;
        List<Task> tasksList = this.journal.getListAllTasks();
        checkingExpiredTasks(tasksList);
    }
    
    public void checkingExpiredTasks(List<Task> tasksList) {
        Date currentDate = new Date();
        for (Task task : tasksList) {
            if (task.getDate().before(currentDate)) {
                if (task.getStatus().equals(Status.SCHEDULED)) {
                    task.setStatus(Status.EXPIRED);
                }
                if (task.getStatus().equals(Status.POSTPONED)) {
                    task.setStatus(Status.EXPIRED);
                }
            }
        }
    }
}