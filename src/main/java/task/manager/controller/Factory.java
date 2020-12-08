package task.manager.controller;


import task.manager.model.Journal;


public class Factory {
    // todo implement methods create without D at the end for all cases
    public Journal created() {
        return new Journal();
    }
}
