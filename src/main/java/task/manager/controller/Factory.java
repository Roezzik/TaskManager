package task.manager.controller;


import task.manager.model.Journal;


public class Factory {
    public Journal create() {
        return new Journal();
    }
}
