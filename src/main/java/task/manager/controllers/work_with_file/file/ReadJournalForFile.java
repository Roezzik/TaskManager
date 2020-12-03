package sample.controller.work_with_file.file;

// todo this class do not work, imports is invalid
import sample.model.Journal;
import sample.model.enums.Status;
import sample.model.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// todo read and write should be implement in one class, do not separate it to two classes
// also you can create interface with two methods - read and write and do implementation for it for binary and txt

// todo create interface Marshalled
// this class will be TextMarshaller
// binary class will be BinaryMarshaller
public class ReadJournalForFile {

    public static Journal read(String filename) throws IOException {
        Journal journal = new Journal();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int sizeList = Integer.parseInt( br.readLine());         
        for (int i = 0; i < sizeList; i++) {
          Task task = new Task(Integer.parseInt(br.readLine()));   
          for (int j = 0; j< 4; j++){                             
              if (j==0) {
                  task.setName( br.readLine());
                  continue;
              }
             else if ( j==1) {
                 task.setDescription(br.readLine());
                 continue;
             }
             else  if (j==2) {
                 // 13/10/2009/17/55/20   13-day, 10-month, 17/55/20-time
                 DateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy/hh/mm/ss");
                 Date date = null;
                 try {
                     date = dateFormat.parse(br.readLine());
                 } catch (ParseException e) {
                     System.out.println("ошибка считывания времени!");
                     e.printStackTrace();
                 }
                 task.setDate(date);
                 continue;

             } else if (j == 3) {
                 switch (br.readLine()){
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
                 continue;
             }
          }
          journal.addTask(task);
        }
        br.close();
        return journal;
    }
}
