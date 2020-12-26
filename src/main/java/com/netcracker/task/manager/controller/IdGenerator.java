package com.netcracker.task.manager.controller;


public class IdGenerator {
    
    private static IdGenerator instance;
    private        int         counter;
    
    private IdGenerator() {
    
    }
    
    private IdGenerator(int counter) {
        this.counter = counter;
    }
    
    public static IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }
        return instance;
    }
    
    public static IdGenerator getInstance(int counter) {
        if (instance == null) {
            instance = new IdGenerator(counter);
        }
        return instance;
    }
    
    public int getNextId() {
        return ++counter;
    }
}