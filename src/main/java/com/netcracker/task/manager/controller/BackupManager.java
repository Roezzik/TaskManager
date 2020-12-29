package com.netcracker.task.manager.controller;


import com.netcracker.task.manager.controller.io.BinaryMarshaller;
import com.netcracker.task.manager.controller.exception.*;
import com.netcracker.task.manager.controller.io.Marshaller;
import com.netcracker.task.manager.controller.io.TextMarshaller;
import com.netcracker.task.manager.model.Journal;

import java.io.IOException;


public class BackupManager {
    
    private static BackupManager instance;
    
    private BackupManager() {
    }
    
    public static BackupManager getInstance() {
        if (instance == null) {
            instance = new BackupManager();
        }
        return instance;
    }
    
    private Marshaller marshaller;
    PropertyParser propertyParser = PropertyParser.getInstance();
    private String pathToDefaultBackup;
    
    private void chooseMarshaller() {
        
        String fileFormat = propertyParser.getPropertyValue("backup_format");
        
        if (fileFormat.equals(FileFormat.TEXT.getTitle())) {
            marshaller = TextMarshaller.getInstance();
        } else if (fileFormat.equals(FileFormat.BINARY.getTitle())) {
            marshaller = BinaryMarshaller.getInstance();
        }
        pathToDefaultBackup = propertyParser.getPropertyValue("path_to_backup") + "." + fileFormat;
    }
    
    public void writeBackup(Journal journal) throws CreateFileException, PropertyReadException,
                                                    PrintWriterException, FileOutputStreamException {
        chooseMarshaller();
        marshaller.write(journal);
    }
    
    public Journal readDefaultBackup() throws PropertyReadException, TextMarshallerReadException,
                                              CreateFileException, IOException, BufferedReaderException,
                                              FileInputStreamException {
        chooseMarshaller();
        return marshaller.read(pathToDefaultBackup);
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
