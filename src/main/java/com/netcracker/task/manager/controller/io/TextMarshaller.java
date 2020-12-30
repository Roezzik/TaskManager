package com.netcracker.task.manager.controller.io;


import com.netcracker.task.manager.controller.ControllerConstants;
import com.netcracker.task.manager.controller.DateConverter;
import com.netcracker.task.manager.controller.PropertyParser;
import com.netcracker.task.manager.controller.exception.*;
import com.netcracker.task.manager.controller.factory.JournalFactory;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;
import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.*;
import java.util.Date;


/**
 * Class for uploading and saving backup files in the format .txt
 */
public class TextMarshaller implements Marshaller {
    
    private static TextMarshaller instance;
    PropertyParser propertyParser;
    
    JournalFactory journalFactory = new JournalFactory();
    
    private boolean flag = false;
    
    private TextMarshaller() {
        propertyParser = PropertyParser.getInstance();
    }
    
    /**
     * Singleton implementation for TextMarshaller
     *
     * @return single TextMarshaller object
     */
    public static TextMarshaller getInstance() {
        if (instance == null) {
            instance = new TextMarshaller();
        }
        return instance;
    }
    
    /**
     * Function for checking the availability of the read file
     *
     * @return boolean value
     */
    public boolean checkCreateFile() {
        return flag;
    }
    
    @Override
    public Journal read(String pathToBackup) throws ReadFileException {
        
        File file = new File(pathToBackup);
        
        if (!file.exists()) {
            flag = true;
            return journalFactory.create();
        }
        
        Journal        journal = journalFactory.create();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new ReadFileException(ViewConstants.ERROR_BUFFER_READ_EXCEPTION);
        }
        
        String firstLine;
        String buffer;
        
        try {
            while ((firstLine = br.readLine()) != null) {
                
                if (firstLine.equals("")) continue;
                try {
                    int    levelOfTask     = 0;
                    int    idTask;
                    String taskName        = "";
                    String taskDescription = "";
                    Date   taskDate        = null;
                    
                    idTask = Integer.parseInt(firstLine);
                    while (true) {
                        buffer = br.readLine();
                        
                        if (levelOfTask == 0) {
                            taskName = buffer;
                        }
                        
                        if (levelOfTask == 1) {
                            StringBuilder descriptionBuffer;
                            if (buffer.equals("")) {
                                descriptionBuffer = new StringBuilder();
                            } else {
                                descriptionBuffer = new StringBuilder(buffer);
                            }
                            while (true) {
                                buffer = br.readLine();
                                if (buffer.equals("")) {
                                    descriptionBuffer.append('\n' + "");
                                } else {
                                    try {
                                        taskDate = DateConverter.stringToDate(buffer);
                                        taskDescription = descriptionBuffer.toString();
                                        break;
                                    } catch (Exception e) {
                                        descriptionBuffer.append('\n').append(buffer);
                                    }
                                }
                            }
                        }
                        if (levelOfTask == 2) {
                            break;
                        }
                        levelOfTask++;
                    }
                    
                    Task task = new Task(idTask, taskName, taskDescription, taskDate, null);
                    
                    switch (buffer) {
                        case "Scheduled" -> task.setStatus(Status.SCHEDULED);
                        case "Done" -> task.setStatus(Status.DONE);
                        case "Postponed" -> task.setStatus(Status.POSTPONED);
                        case "Expired" -> task.setStatus(Status.EXPIRED);
                        case "Cancelled" -> task.setStatus(Status.CANCELLED);
                    }
                    
                    if (task.getStatus() == null) throw new ReadFileException(ViewConstants.ERROR_READ_FILE);
                    
                    journal.addTask(task);
                } catch (Exception e) {
                    throw new ReadFileException(ViewConstants.ERROR_READ_FILE);
                }
            }
        } catch (IOException e) {
            throw new ReadFileException(ViewConstants.ERROR_READ_FILE);
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
            if (!file.exists()) {
                boolean create = file.createNewFile();
            }
        } catch (IOException e) {
            throw new WriteFileException(ViewConstants.ERROR_CREATE_FILE);
        }
        
        try (PrintWriter pw = new PrintWriter(file)) {
            journal.getListAllTasks().forEach(task -> {
                pw.println(task.getId());
                pw.println(task.getName());
                pw.println(task.getDescription());
                pw.println(DateConverter.getStringDate(task.getDate()));
                pw.println((task.getStatus()).getTitle());
                pw.println();
            });
        } catch (FileNotFoundException fileNotFoundException) {
            throw new WriteFileException(ViewConstants.ERROR_PRINT_WRITER_EXCEPTION);
        }
    }
}
