package com.netcracker.task.manager.controller;


import com.netcracker.task.manager.controller.io.BinaryMarshaller;
import com.netcracker.task.manager.controller.exception.*;
import com.netcracker.task.manager.controller.io.Marshaller;
import com.netcracker.task.manager.controller.io.TextMarshaller;
import com.netcracker.task.manager.model.Journal;

import java.io.IOException;
import java.util.Properties;


public class BackupManager {
    
    private Marshaller marshaller;
    PropertyParser propertyParser = PropertyParser.getInstance();
    
    private void chooseMarshaller() throws PropertyReadException {
        
        String fileFormat = propertyParser.getPropertyValue("backup_format");
       // String fileFormat = propertyParser.getFileFormat();

        if (fileFormat.equals(FileFormat.TEXT.getTitle())) {
            marshaller = TextMarshaller.getInstance();
        } else if (fileFormat.equals(FileFormat.BINARY.getTitle())) {
            marshaller = BinaryMarshaller.getInstance();
        }
    }
    
    public void writeBackup(Journal journal) throws CreateFileException, PropertyReadException,
                                                    PrintWriterException, FileOutputStreamException {
        chooseMarshaller();
        marshaller.write(journal);
    }
    
    public Journal readDefaultBackup() throws PropertyReadException, TextMarshallerReadException, CreateFileException,
                                              IOException, BufferedReaderException, FileInputStreamException {
        chooseMarshaller();
        return marshaller.read();
    }
    
    public Journal readOtherBackup(String pathToBackup) throws PropertyReadException, TextMarshallerReadException,
                                                               CreateFileException, IOException,
                                                               BufferedReaderException,
                                                               FileInputStreamException {
        chooseMarshaller();
        return marshaller.read(pathToBackup);
    }
    
    enum FileFormat {
        
        TEXT("txt"),
        BINARY("bin");
        
        private final String title;
        
        FileFormat(String title) {
            this.title = title;
        }
        
        public String getTitle() {
            return title;
        }
    }
}
