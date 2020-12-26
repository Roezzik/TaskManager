package com.netcracker.task.manager.controller.io;


import com.netcracker.task.manager.controller.PropertyParser;
import com.netcracker.task.manager.controller.PropertyReadException;
import com.netcracker.task.manager.controller.io.exception.*;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.view.utils.AlertForm;
import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.*;


public class BinaryMarshaller implements Marshaller {

    private static BinaryMarshaller instance;

    private static final String PATH_TO_BIN_BACKUP = "path_to_bin_backup";

    private BinaryMarshaller() {
    }

    public static BinaryMarshaller getInstance() {
        if (instance == null) {
            instance = new BinaryMarshaller();
        }
        return instance;
    }

    @Override
    public Journal read() throws  CreateFileException, PropertyReadException,  FileInputStreamException {

        String pathToFile = PropertyParser.getPropertyValue(PATH_TO_BIN_BACKUP);

        File   file       = new File(pathToFile);
        Journal journal = new Journal();

        try {
            if (!file.exists()) {
                file.createNewFile();
                write(journal);
                AlertForm.errorAlert(ViewConstants.ERROR_NO_BINARY_FILE_FOUND);
            }
        } catch (Exception e) {
            throw new CreateFileException(ViewConstants.ERROR_CREATE_FILE);
        }

        try (FileInputStream fis = new FileInputStream(pathToFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
             journal = (Journal) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
           throw new FileInputStreamException(ViewConstants.ERROR_FILE_INPUT_STREAM_EXCEPTION);
        }
        return journal;
    }

    @Override
    public void write(Journal journal) throws CreateFileException, PropertyReadException, PrintWriterException, FileOutputStreamException {

        String pathToFile = PropertyParser.getPropertyValue(PATH_TO_BIN_BACKUP);

        //
        File   file       = new File(pathToFile);

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new CreateFileException(ViewConstants.ERROR_CREATE_FILE);
        }
        //

        try (FileOutputStream fos = new FileOutputStream(pathToFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
             oos.writeObject(journal);
        } catch (IOException e) {
            throw new FileOutputStreamException(ViewConstants.ERROR_FILE_OUTPUT_STREAM_EXCEPTION);
        }
    }

}
