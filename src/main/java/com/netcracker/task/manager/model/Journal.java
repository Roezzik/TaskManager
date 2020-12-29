package com.netcracker.task.manager.model;


import java.io.Serializable;
import java.util.*;


public class Journal implements Serializable {
    
    private final HashMap<Integer, Task> tasksMap;
    
    public Journal() {
        tasksMap = new HashMap<>();
    }
    
    public List<Task> getListAllTasks() {
        List<Task> allTasks = new ArrayList<>(tasksMap.values());
        return Collections.unmodifiableList(allTasks);
    }
    
    public Task getTask(int taskId) {
        return tasksMap.get(taskId);
    }
    
    public void addTask(Task task) {
        tasksMap.put(task.getId(), task);
    }
    
    public void updateTask(Task task) {
        tasksMap.put(task.getId(), task);
    }
    
    public void deleteTask(int taskId) {
        tasksMap.remove(taskId);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Journal journal = (Journal) o;
        return tasksMap.equals(journal.tasksMap);
    }
    
    @Override
    public int hashCode() {
        return tasksMap.hashCode();
    }
    
    @Override
    public String toString() {
        return "Journal{" +
               "tasksMap=" + tasksMap +
               '}';
    }
}