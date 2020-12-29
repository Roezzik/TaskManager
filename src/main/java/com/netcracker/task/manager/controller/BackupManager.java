package com.netcracker.task.manager.controller;


import com.netcracker.task.manager.controller.io.BinaryMarshaller;
import com.netcracker.task.manager.controller.exception.*;
import com.netcracker.task.manager.controller.io.Marshaller;
import com.netcracker.task.manager.controller.io.TextMarshaller;
import com.netcracker.task.manager.model.Journal;


/**
 * Class for working with backup files
 */
public class BackupManager {
    
    private static BackupManager instance;
    private        Marshaller    marshaller;
    PropertyParser propertyParser = PropertyParser.getInstance();
    private String pathToDefaultBackup;
    
    private BackupManager() {
        chooseMarshaller();
    }
    
    /**
     * Singleton implementation for BackupManager
     *
     * @return single BackupManager object
     */
    public static BackupManager getInstance() {
        if (instance == null) {
            instance = new BackupManager();
        }
        return instance;
    }
    
    /**
     * Function to select the required marshaller depending on the specified backup file format,
     * gets the path to the backup file depending on the properties
     */
    private void chooseMarshaller() {
        
        String fileFormat = propertyParser.getPropertyValue("backup_format");
        
        if (fileFormat.equals(FileFormat.TEXT.getTitle())) {
            marshaller = TextMarshaller.getInstance();
        } else if (fileFormat.equals(FileFormat.BINARY.getTitle())) {
            marshaller = BinaryMarshaller.getInstance();
        }
        pathToDefaultBackup = propertyParser.getPropertyValue("path_to_backup") + "." + fileFormat;
    }
    
    /**
     * Function for writing the journal to a backup file
     *
     * @param journal - journal with tasks
     */
    public void writeBackup(Journal journal) throws WriteFileException {
        marshaller.write(journal);
    }
    
    /**
     * Function for reading the journal from the backup file using the default path
     *
     * @return journal with tasks from the uploaded file
     */
    public Journal readDefaultBackup() throws ReadFileException {
        return marshaller.read(pathToDefaultBackup);
    }
    
    /**
     * Function for reading the journal from the backup file at the path selected by the user
     *
     * @param pathToBackup - path to the backup file
     * @return journal with tasks from the uploaded file
     */
    public Journal readOtherBackup(String pathToBackup) throws ReadFileException {
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
