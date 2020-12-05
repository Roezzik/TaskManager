package task.manager.controllers;


public class IdGenerator {

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