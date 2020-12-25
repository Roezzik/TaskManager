package com.netcracker.task.manager.controller.io;


import com.netcracker.task.manager.controller.DateConverter;
import com.netcracker.task.manager.controller.io.Exception.CreateFileException;
import com.netcracker.task.manager.controller.io.Exception.TextMarshallerReadException;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.model.Status;
import com.netcracker.task.manager.model.Task;
import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.*;


public class TextMarshaller implements Marshaller {


    private static TextMarshaller instance;

    private TextMarshaller() {
    }

    public static TextMarshaller getInstance() {
        if (instance == null) {
            instance = new TextMarshaller();
        }
        return instance;
    }

    @Override
    public Journal read(String path) throws CreateFileException, TextMarshallerReadException, IOException {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            throw new CreateFileException(ViewConstants.ERROR_CREATE_FILE);
        }

        Journal journal = new Journal();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            //e.printStackTrace(); ???
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
    public void write(Journal journal, String path) throws CreateFileException {

        File file = new File(path);

        try {
            if (!file.exists()) {
                file.createNewFile();
               // return; или выкидывать ошибку, в контроллере ее ловить, потом создавать файл, потом уже записывать?
            }
        } catch (Exception e) {
            throw new CreateFileException(ViewConstants.ERROR_CREATE_FILE);
        }

        try (PrintWriter pw = new PrintWriter(path)) {
            journal.getTasksMap().values().forEach(task -> {
                pw.println(task.getId());
                pw.println(task.getName());
                pw.println(task.getDescription());
                pw.println(DateConverter.getStringDate(task.getDate()));
                pw.println((task.getStatus()).getTitle());
                pw.println();
            });
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();//???
        }

    }
}
