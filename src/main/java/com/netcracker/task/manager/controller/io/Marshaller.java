package com.netcracker.task.manager.controller.io;


import com.netcracker.task.manager.controller.PropertyReadException;
import com.netcracker.task.manager.controller.io.Exception.CreateFileException;
import com.netcracker.task.manager.controller.io.Exception.TextMarshallerReadException;
import com.netcracker.task.manager.model.Journal;

import java.io.IOException;


public interface Marshaller {
    
    Journal read() throws IOException, TextMarshallerReadException, CreateFileException, PropertyReadException;
    void write(Journal journal) throws CreateFileException, PropertyReadException;
}
