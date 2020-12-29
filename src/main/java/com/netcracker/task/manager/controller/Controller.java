package com.netcracker.task.manager.controller;


import com.netcracker.task.manager.controller.factory.JournalFactory;
import com.netcracker.task.manager.controller.sheduler.NotificationScheduler;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;

import java.util.*;


/**
 * Class for changing the task journal
 *
 * @see Journal
 */
public class Controller {
    
    private static Controller            instance;
    private        Journal               journal;
    private final  NotificationScheduler notificationScheduler;
    
    private Controller() {
        JournalFactory journalFactory = new JournalFactory();
        this.journal = journalFactory.create();
        this.notificationScheduler = NotificationScheduler.getInstance();
    }
    
    /**
     * Singleton implementation for Controller
     *
     * @return single Controller object
     */
    public static synchronized Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
    
    /**
     * Function for starts the timers for tasks notification
     */
    private void initAllTask() {
        notificationScheduler.startAllTasks(journal.getListAllTasks());
    }
    
    /**
     * Function getting all tasks in the journal
     *
     * @return unmodifiableList of tasks
     */
    public List<Task> getAllTasks() {
        return journal.getListAllTasks();
    }
    
    /**
     * Function for adding task in the journal
     *
     * @param task - new task
     */
    public void addTask(Task task) {
        journal.addTask(task);
        notificationScheduler.addNotification(task);
    }
    
    /**
     * Function for updating task in the journal
     *
     * @param task - new task
     */
    public void updateTask(Task task) {
        journal.updateTask(task);
        notificationScheduler.postponeNotification(task);
    }
    
    /**
     * Function for deleting task from the journal
     *
     * @param taskId - id of the task to be deleted
     */
    public void deleteTask(int taskId) {
        journal.deleteTask(taskId);
        notificationScheduler.removeNotification(taskId);
    }
    
    /**
     * Function for getting the last task id
     *
     * @return - last task id
     */
    public int getLastTaskId() {
        List<Task> tasksList = journal.getListAllTasks();
        return tasksList.stream().map(Task::getId).max(Integer::compareTo).orElse(0);
    }
    
    /**
     * Function for canceling task, the task status changes to Canceled
     *
     * @param taskId - id of the task being canceled
     */
    public void cancelTask(int taskId) {
        getTask(taskId).setStatus(Status.CANCELLED);
        notificationScheduler.removeNotification(taskId);
    }
    
    /**
     * Function for marking that the task is done, the task status changes to Done
     *
     * @param taskId - id of the task being done
     */
    public void doneTask(int taskId) {
        getTask(taskId).setStatus(Status.DONE);
        notificationScheduler.removeNotification(taskId);
    }
    
    /**
     * Function for getting the task by id
     *
     * @return - task by this id
     */
    public Task getTask(int taskId) {
        return journal.getTask(taskId);
    }
    
    /**
     * Function for getting the journal
     *
     * @return - journal with tasks
     */
    public Journal getJournal() {
        return journal;
    }
    
    /**
     * Function for overwriting the task journal from the selected backup file
     *
     * @param journal - journal with tasks
     */
    public void restoreBackupTasks(Journal journal) {
        JournalFactory journalFactory = new JournalFactory();
        this.journal = journalFactory.create();
        List<Task> backupTasks = journal.getListAllTasks();
        Date       currentDate = new Date();
        for (Task task : backupTasks) {
            if (task.getDate().before(currentDate)) {
                if (task.getStatus().equals(Status.SCHEDULED) || task.getStatus().equals(Status.POSTPONED)) {
                    task.setStatus(Status.EXPIRED);
                }
            }
            this.addTask(task);
        }
    }
}