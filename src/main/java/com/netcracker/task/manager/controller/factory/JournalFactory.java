package com.netcracker.task.manager.controller.factory;


import com.netcracker.task.manager.model.Journal;


/**
 * Factory pattern class for creating Journal
 * @see Journal
 */
public class JournalFactory {
    
    public Journal create() {
        return new Journal();
    }
}