package com.netcracker.task.manager.controller.io;


import com.netcracker.task.manager.controller.PropertyParser;
import com.netcracker.task.manager.controller.exception.CreateFileException;
import com.netcracker.task.manager.controller.exception.FileInputStreamException;
import com.netcracker.task.manager.controller.exception.FileOutputStreamException;
import com.netcracker.task.manager.controller.factory.JournalFactory;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.*;


public class BinaryMarshaller implements Marshaller {
    
    private static BinaryMarshaller instance;
    
    PropertyParser propertyParser = PropertyParser.getInstance();
    
    private static final String FORMAT = "backup_format";
    private static final String PATH   = "path_to_backup";
    
    private boolean flag = false;
    
    private BinaryMarshaller() {
    }
    
    public static BinaryMarshaller getInstance() {
        if (instance == null) {
            instance = new BinaryMarshaller();
        }
        return instance;
    }
    
    public boolean checkCreateFile() {
        return flag;
    }
    
    @Override
    public Journal read(String pathToBackup) throws FileInputStreamException {
        
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
            throw new FileInputStreamException(ViewConstants.ERROR_FILE_INPUT_STREAM_EXCEPTION);
        }
        return journal;
    }
    
    @Override
    public void write(Journal journal)
    throws CreateFileException, FileOutputStreamException {
        
        String pathToFile = propertyParser.getPropertyValue(PATH) + "." + propertyParser.getPropertyValue(FORMAT);
        File file = new File(pathToFile);
        
        try {
            boolean create = file.createNewFile();
        } catch (IOException e) {
            throw new CreateFileException(ViewConstants.ERROR_CREATE_FILE);
        }
        
        try (FileOutputStream fos = new FileOutputStream(pathToFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(journal);
        } catch (IOException e) {
            throw new FileOutputStreamException(ViewConstants.ERROR_FILE_OUTPUT_STREAM_EXCEPTION);
        }
    }
}
