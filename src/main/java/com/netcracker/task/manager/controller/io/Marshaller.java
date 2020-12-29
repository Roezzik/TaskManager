package com.netcracker.task.manager.controller.io;


import com.netcracker.task.manager.controller.exception.*;
import com.netcracker.task.manager.model.Journal;


public interface Marshaller {
    
    Journal read(String pathToBackup) throws  ReadFileException;
    
    void write(Journal journal) throws  WriteFileException;
}
