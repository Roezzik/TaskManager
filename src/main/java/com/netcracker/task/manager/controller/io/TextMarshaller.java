package com.netcracker.task.manager.controller.io;


import com.netcracker.task.manager.controller.DateConverter;
import com.netcracker.task.manager.controller.PropertyParser;
import com.netcracker.task.manager.controller.PropertyReadException;
import com.netcracker.task.manager.controller.io.exception.BufferedReaderException;
import com.netcracker.task.manager.controller.io.exception.CreateFileException;
import com.netcracker.task.manager.controller.io.exception.PrintWriterException;
import com.netcracker.task.manager.controller.io.exception.TextMarshallerReadException;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;
import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.*;


public class TextMarshaller implements Marshaller {
    
    private static TextMarshaller instance;
    
    private static final String PATH_TO_TXT_BACKUP = "path_to_txt_backup";
    
    private TextMarshaller() {
    }
    
    public static TextMarshaller getInstance() {
        if (instance == null) {
            instance = new TextMarshaller();
        }
        return instance;
    }
    
    @Override
    public Journal read()
    throws CreateFileException, TextMarshallerReadException, IOException, PropertyReadException, BufferedReaderException {
    
        String pathToFile = PropertyParser.getPropertyValue(PATH_TO_TXT_BACKUP);
        File   file       = new File(pathToFile);
        
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            throw new CreateFileException(ViewConstants.ERROR_CREATE_FILE);
        }
        
        Journal        journal = new Journal();
        BufferedReader br      = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new BufferedReaderException(ViewConstants.ERROR_BUFFER_READ_EXCEPTION);
        }
        
        String str;
        if (br != null) {
            while ((str = br.readLine()) != null) {
                
                if (str.equals("")) continue;
                
                try {
                    Task task = new Task(Integer.parseInt(str),
                                         br.readLine(),
                                         br.readLine(),
                                         DateConverter.stringToDate(br.readLine()),
                                         null);
                    switch (br.readLine()) {
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
        }
        return journal;
    }
    
    @Override
    public void write(Journal journal) throws CreateFileException, PropertyReadException, PrintWriterException {
    
        String pathToFile = PropertyParser.getPropertyValue(PATH_TO_TXT_BACKUP);
        File   file       = new File(pathToFile);
        
        try {
            file.createNewFile();
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
