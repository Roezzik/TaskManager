package com.netcracker.task.manager.controller.io;


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


public class TextMarshaller implements Marshaller {
    
    private static TextMarshaller instance;

    PropertyParser propertyParser = PropertyParser.getInstance();
    
    JournalFactory journalFactory = new JournalFactory();
    
    private static final String PATH_TO_BACKUP = "path_to_backup";
    private static final String BACKUP_FORMAT  = "backup_format";
    private boolean flag = false;
    
    private TextMarshaller() {
    }
    
    public static TextMarshaller getInstance() {
        if (instance == null) {
            instance = new TextMarshaller();
        }
        return instance;
    }
    


    private Journal readTextBackup(File file) throws BufferedReaderException, IOException, TextMarshallerReadException {
        
        Journal        journal = journalFactory.create();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new BufferedReaderException(ViewConstants.ERROR_BUFFER_READ_EXCEPTION);
        }
        
        String firstLine;
        String buffer;
        
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
                journal.addTask(task);
            } catch (Exception e) {
                throw new TextMarshallerReadException(ViewConstants.ERROR_READ_FILE);
            }
        }
        return journal;
    }
    
    public boolean checkCreateFile() {
        return flag;
    }
    
    @Override
    public Journal read()
    throws TextMarshallerReadException, IOException, PropertyReadException, BufferedReaderException {
        
        String pathToFile = propertyParser.getPropertyValue(PATH_TO_BACKUP) + "." + propertyParser.getPropertyValue(BACKUP_FORMAT);
      //  String pathToFile = propertyParser.getPathToBackup();
        File   file       = new File(pathToFile);
        
        if (!file.exists()) {
            flag = true;
            return journalFactory.create();
        }
        
        return readTextBackup(file);
    }
    
    @Override
    public Journal read(String pathToBackup) throws IOException, TextMarshallerReadException, BufferedReaderException {
        
        File file = new File(pathToBackup);
        
        return readTextBackup(file);
    }
    
    @Override
    public void write(Journal journal) throws CreateFileException, PropertyReadException, PrintWriterException {
        
        String pathToFile = propertyParser.getPropertyValue(PATH_TO_BACKUP) + "." + propertyParser.getPropertyValue(BACKUP_FORMAT);
        //String pathToFile = propertyParser.getPathToBackup();
        File   file       = new File(pathToFile);
        
        try {
            boolean create = file.createNewFile();
        } catch (IOException e) {
            throw new CreateFileException(ViewConstants.ERROR_CREATE_FILE);
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
            throw new PrintWriterException(ViewConstants.ERROR_PRINT_WRITER_EXCEPTION);
        }
    }
}
