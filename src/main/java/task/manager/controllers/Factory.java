package task.manager.controllers;

import task.manager.models.Journal;

public class Factory {
    public Journal created(){
        return new Journal();
    }
}
