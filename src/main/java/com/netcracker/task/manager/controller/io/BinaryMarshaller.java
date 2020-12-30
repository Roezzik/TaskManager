package com.netcracker.task.manager.controller.io;


import com.netcracker.task.manager.controller.ControllerConstants;
import com.netcracker.task.manager.controller.PropertyParser;
import com.netcracker.task.manager.controller.exception.*;
import com.netcracker.task.manager.controller.factory.JournalFactory;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.*;


/**
 * Class for uploading and saving backup files in the format .bin
 */
public class BinaryMarshaller implements Marshaller {
    
    private static BinaryMarshaller instance;
    private final  PropertyParser   propertyParser;
    
    private boolean flag = false;
    
    private BinaryMarshaller() {
        propertyParser = PropertyParser.getInstance();
    }
    
    /**
     * Singleton implementation for BinaryMarshaller
     *
     * @return single BinaryMarshaller object
     */
    public static BinaryMarshaller getInstance() {
        if (instance == null) {
            instance = new BinaryMarshaller();
        }
        return instance;
    }
    
    /**
     * Function for checking the availability of the read file
     *
     * @return boolean value
     */
    public boolean checkCreateFile() { // todo kill me
        return flag;
    }
    
    @Override
    public Journal read(String pathToBackup) throws ReadFileException {
        
        File file = new File(pathToBackup);
        
        if (!file.exists()) {
            flag = true;
            JournalFactory journalFactory = new JournalFactory();
            return journalFactory.create();
        }
        
        Journal journal;
        
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            journal = (Journal) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ReadFileException(ViewConstants.ERROR_FILE_INPUT_STREAM_EXCEPTION);
        }
        return journal;
    }
    
    @Override
    public void write(Journal journal) throws WriteFileException {
        
        String pathToFile = propertyParser.getPropertyValue(ControllerConstants.PATH_TO_BACKUP_PROPERTY)
                            + "."
                            + propertyParser.getPropertyValue(ControllerConstants.BACKUP_FORMAT_PROPERTY);
        File file = new File(pathToFile);
        
        try {
            boolean create = file.createNewFile();
        } catch (IOException e) {
            throw new WriteFileException(ViewConstants.ERROR_CREATE_FILE);
        }
        
        try (FileOutputStream fos = new FileOutputStream(pathToFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(journal);
        } catch (IOException e) {
            throw new WriteFileException(ViewConstants.ERROR_FILE_OUTPUT_STREAM_EXCEPTION);
        }
    }
}
