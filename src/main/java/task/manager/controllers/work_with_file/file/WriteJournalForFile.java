package sample.controller.work_with_file.file;

import sample.controller.resourсes.Dependences;
import sample.model.Journal;
import java.io.*;

// todo the same like for ReadJournalForFile
public class WriteJournalForFile {
    public static  void write(Journal journal, String path){

        File file = new  File(Dependences.FILE_PATH);

        try {
            if(!file.exists()){
                // тут алерт написать
                System.out.println("файла не найдено!"); // todo english and move all messages to constant class
                // хотите ли вы создать файл? - можно реализовать
               //file.createNewFile();
                return;
            }
        }catch (Exception e){
            // тут алерт написать
            System.out.println("ошибка обнаружения файла");
        }

      
        try ( PrintWriter pw = new PrintWriter( new FileWriter(file, true));
              BufferedReader br = new BufferedReader(new FileReader(path)) ) {
            
            if (br.equals("")) pw.println(journal.getCountTask()); // записываем в файл CountTask только если он пустой
            for (int i=0; i< journal.getCountTask(); i++)
                  pw.println(journal.getTaskByIndex(i));
        }
        catch ( IOException e){
            // алерт 
            System.out.println("ошибка записи в файл!");
        }
    }
}
