package task.manager.controllers.io;

import task.manager.models.Journal;
import task.manager.models.Status;
import task.manager.models.Task;
import task.manager.models.interfaces.Marshalled;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextMarshaller implements Marshalled {

    @Override
    public Journal read(String path) throws IOException {
        Journal journal = new Journal();
        BufferedReader br = new BufferedReader(new FileReader(path));
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

    @Override
    public void write(Journal journal, String path){

        File file = new  File(path);

        try {
            if(!file.exists()){
                //alert errors
                System.out.println("error!!");
                // do you want to create a file? - can be implemented
                return;
            }
        }catch (Exception e){
            // alert
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter( new FileWriter(file, true));
             BufferedReader br = new BufferedReader(new FileReader(path)) ) {

            if (br.equals("")) pw.println(journal.getCountTask());
            for (int i=0; i< journal.getCountTask(); i++)
                pw.println(journal.getTaskByIndex(i));
        }
        catch ( IOException e){
            //alert
            e.printStackTrace();
        }
    }
}
