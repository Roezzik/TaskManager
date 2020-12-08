package task.manager.controller.io;


import task.manager.controller.Constants;
import task.manager.controller.DateConverter;
import task.manager.model.Journal;
import task.manager.model.Status;
import task.manager.model.Task;

import java.io.*;
import java.text.ParseException;


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
    public Journal read(String path) throws IOException {
        Journal journal = new Journal();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        String str = "";
//        while ((str = br.readLine()) != null) {
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
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }
        return journal;
    }

    @Override
    public void write(Journal journal, String path) {

        File file = new File(Constants.FILE_PATH);

        try {
            if (!file.exists()) {
                //alert errors
                System.out.println("error!!");
                // do you want to create a file? - can be implemented
                return;
            }
        } catch (Exception e) {
            // alert
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(file, true));
             BufferedReader br = new BufferedReader(new FileReader(path))) {

            journal.getTasksMap().values().forEach(task -> {
                pw.println(task.getId());
                pw.println(task.getName());
                pw.println(task.getDescription());
                pw.println(DateConverter.getStringDate(task.getDate()));
                pw.println((task.getStatus()).getTitle());
            });

        } catch (IOException e) {
            //alert
            e.printStackTrace();
        }

    }
}
