package task.manager.controller;


import task.manager.model.Journal;


public class Factory {
    public Journal created() {
        return new Journal();
    }
}
