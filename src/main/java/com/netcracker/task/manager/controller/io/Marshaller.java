package com.netcracker.task.manager.controller.io;


import com.netcracker.task.manager.controller.PropertyReadException;
import com.netcracker.task.manager.controller.io.exception.*;
import com.netcracker.task.manager.model.Journal;

import java.io.IOException;


public interface Marshaller {
    
    Journal read() throws IOException, TextMarshallerReadException, CreateFileException, PropertyReadException,
                          BufferedReaderException, FileInputStreamException;
    
    Journal read(String pathToBackup) throws IOException, TextMarshallerReadException, CreateFileException, PropertyReadException,
                                         BufferedReaderException, FileInputStreamException;
    
    void write(Journal journal) throws CreateFileException, PropertyReadException, PrintWriterException,
                                       FileOutputStreamException;
}
