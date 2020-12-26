package com.netcracker.task.manager.model;


import java.io.Serializable;

public enum Status implements Serializable  {
    
    SCHEDULED("Scheduled"),
    DONE("Done"),
    POSTPONED("Postponed"),
    EXPIRED("Expired"),
    CANCELLED("Cancelled");
    
    private final String title;
    
    Status(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
}

