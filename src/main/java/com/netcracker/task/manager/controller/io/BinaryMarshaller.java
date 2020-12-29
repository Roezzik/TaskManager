package com.netcracker.task.manager.controller.io;


import com.netcracker.task.manager.controller.PropertyParser;
import com.netcracker.task.manager.controller.exception.CreateFileException;
import com.netcracker.task.manager.controller.exception.FileInputStreamException;
import com.netcracker.task.manager.controller.exception.FileOutputStreamException;
import com.netcracker.task.manager.controller.exception.PropertyReadException;
import com.netcracker.task.manager.controller.factory.JournalFactory;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.*;


public class BinaryMarshaller implements Marshaller {

    private static BinaryMarshaller instance;
    PropertyParser propertyParser = PropertyParser.getInstance();

    private static final String PATH_TO_BACKUP = "path_to_backup";
    private static final String BACKUP_FORMAT  = "backup_format";
    private boolean flag = false;

    private BinaryMarshaller() {
    }

    public static BinaryMarshaller getInstance() {
        if (instance == null) {
            instance = new BinaryMarshaller();
        }
        return instance;
    }

    private Journal readBinaryBackup(File file) throws FileInputStreamException {

        Journal journal;

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            journal = (Journal) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileInputStreamException(ViewConstants.ERROR_FILE_INPUT_STREAM_EXCEPTION);
        }
        return journal;
    }

    public boolean checkCreateFile() {
        return flag;
    }

    @Override
    public Journal read() throws PropertyReadException, FileInputStreamException {

        String pathToFile = propertyParser.getPropertyValue(PATH_TO_BACKUP) + "." + propertyParser.getPropertyValue(BACKUP_FORMAT);

        System.out.println(pathToFile);

        File file = new File(pathToFile);

        if (!file.exists()) {
            flag = true;
            JournalFactory journalFactory = new JournalFactory();
            return journalFactory.create();
        }

        return readBinaryBackup(file);
    }

    @Override
    public Journal read(String pathToBackup) throws FileInputStreamException {

        //String pathToFile = PropertyParser.getPropertyValue(PATH_TO_BIN_BACKUP);
        File file = new File(pathToBackup);
        return readBinaryBackup(file);
    }

    @Override
    public void write(Journal journal)
    throws CreateFileException, PropertyReadException, FileOutputStreamException {

        String pathToFile = propertyParser.getPropertyValue(PATH_TO_BACKUP) + "." + propertyParser.getPropertyValue(BACKUP_FORMAT);
       // String pathToFile = propertyParser.getPathToBackup();
        File   file       = new File(pathToFile);

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
