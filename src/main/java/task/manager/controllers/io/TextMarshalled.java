package task.manager.controllers.io;

import task.manager.controllers.Constant;
import task.manager.controllers.DateChange;
import task.manager.model.Journal;
import task.manager.model.Status;
import task.manager.model.Task;

import java.io.*;
import java.text.ParseException;

public class TextMarshalled implements Marshalled {

    private static final TextMarshalled tm = new TextMarshalled();

    public static TextMarshalled getTextMarshaller() {
        return tm;
    }
    
    private TextMarshalled(){};

    @Override
    public Journal read(String path) throws IOException {
        Journal journal = new Journal();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String str = "";
        while ((str = br.readLine()) != null) {

            if (str.equals("")) continue;

            try {
                Task task = new Task(Integer.parseInt(str),
                        br.readLine(),
                        br.readLine(),
                        DateChange.stringToDate(br.readLine()),
                        null);
                switch (br.readLine()) {
                    case "Scheduled":
                        task.setStatus(Status.SCHEDULED);
                        break;
                    case "Done":
                        task.setStatus(Status.DONE);
                        break;
                    case "Postponed":
                        task.setStatus(Status.POSTPONED);
                        break;
                    case "Expired":
                        task.setStatus(Status.EXPIRED);
                        break;
                    case "Cancelled":
                        task.setStatus(Status.CANCELLED);
                        break;
                }
                journal.addTask(task);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return journal;
    }

    @Override
    public void write(Journal journal, String path) {

        File file = new File(Constant.FILE_PATH);

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
                pw.println(DateChange.getStringDate(task.getDate()));
                pw.println((task.getStatus()).getTitle());
            });

        } catch (IOException e) {
            //alert
            e.printStackTrace();
        }

    }
}
