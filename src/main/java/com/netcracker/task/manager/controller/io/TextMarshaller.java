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
import com.netcracker.task.manager.view.utils.AlertForm;
import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.*;
import java.util.Date;


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
            throws CreateFileException, TextMarshallerReadException, IOException, PropertyReadException,
            BufferedReaderException {

        String pathToFile = PropertyParser.getPropertyValue(PATH_TO_TXT_BACKUP);
        File file = new File(pathToFile);

        try {
            if (!file.exists()) {
                boolean create = file.createNewFile();
                AlertForm.helloAlert(ViewConstants.ERROR_BACKUP_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new CreateFileException(ViewConstants.ERROR_CREATE_FILE);
        }

        Journal journal = new Journal();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new BufferedReaderException(ViewConstants.ERROR_BUFFER_READ_EXCEPTION);
        }

        String str;
        String str2;

        while ((str = br.readLine()) != null) {

            // отступы между тасками
            if (str.equals("")) continue;

            try {
                int sum = 0;
                int idTask = 0;
                String nameTask = "";
                String descriptionTask = "";
                Date dateTask = null;

                idTask = Integer.parseInt(str);
                while (true) {
                    str2 = br.readLine();

                    if (sum == 0) {
                        nameTask = str2;
                    }
                    // description tasks
                    if (sum == 1) {
                        String str3 = "";
                        if (str2.equals("")) {
                            str3 = "";
                        } else {
                            str3 = str2;
                        }
                        while (true) {
                            str2 = br.readLine();
                            if (str2.equals("")) {
                                str3 += '\n' + "";
                            } else {
                                try {
                                    dateTask = DateConverter.stringToDate(str2);
                                    descriptionTask = str3;
                                    break;
                                } catch (Exception e) {
                                    str3 += '\n' + str2;
                                }
                            }
                        }
                    }
                    if (sum == 2) {
                        break;
                    }
                    sum++;
                }
                Task task = new Task(idTask, nameTask, descriptionTask, dateTask, null);

                switch (str2) {
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

    @Override
    public void write(Journal journal) throws CreateFileException, PropertyReadException, PrintWriterException {

        String pathToFile = PropertyParser.getPropertyValue(PATH_TO_TXT_BACKUP);
        File file = new File(pathToFile);

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
