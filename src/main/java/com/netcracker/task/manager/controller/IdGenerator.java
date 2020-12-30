package com.netcracker.task.manager.controller;


/**
 * Class for generating unique task IDs
 */
public class IdGenerator {
    
    private static IdGenerator instance;
    private        int         counter;
    
    private IdGenerator() {
    
    }
    
    private IdGenerator(int counter) {
        this.counter = counter;
    }
    
    /**
     * Singleton implementation for IdGenerator
     *
     * @return single IdGenerator object without parameter
     */
    public static IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }
        return instance;
    }
    
    /**
     * Singleton implementation for IdGenerator
     *
     * @return single IdGenerator object with parameter
     */
    public static IdGenerator getInstance(int counter) {
        if (instance == null) {
            instance = new IdGenerator(counter);
        }
        return instance;
    }
    
    /**
     * Function for getting the task id
     * @return task id
     */
    public int getNextId() {
        return ++counter;
    }
}