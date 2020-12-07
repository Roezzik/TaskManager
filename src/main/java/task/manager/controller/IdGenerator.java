package task.manager.controller;


public class IdGenerator {
    
    private static final IdGenerator idG = new IdGenerator();
    
    public static IdGenerator getIdGenerator() {
        return idG;
    }
    //TODO: create 2 private constructor's for Singleton?
    
    private int counter;
    
    public IdGenerator() {
        counter = 0;
    }
    
    public IdGenerator(int counter) {
        this.counter = counter;
    }
    
    public int getNextId() {
        return ++counter;
    }
}