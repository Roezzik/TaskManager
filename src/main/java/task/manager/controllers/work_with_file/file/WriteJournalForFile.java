package sample.controller.work_with_file.file;

import sample.controller.resourсes.Dependences;
import sample.model.Journal;
import java.io.*;

public class WriteJournalForFile {
    public static  void write(Journal journal, String path){

        File file = new  File(Dependences.FILE_PATH);

        try {
            if(!file.exists()){
                // тут алерт написать
                System.out.println("файла не найдено!");
                // хотите ли вы создать файл? - можно реализовать
               //file.createNewFile();
                return;
            }
        }catch (Exception e){
            // тут алерт написать
            System.out.println("ошибка обнаружения файла");
        }

        BufferedReader br = null; // нужен, чтобы узнать надо ли записывать CountTask
        PrintWriter pw=null;
        try {
            pw = new PrintWriter( new FileWriter(file, true));
            br = new BufferedReader(new FileReader(path));
            if(br.equals("")) pw.println(journal.getCountTask()); // записываем в файл CountTask только если он пустой

            pw.println(journal.getTaskByIndex(0));
           //pw.close();
        }catch (FileNotFoundException e){
            // алерт
            System.out.println("ошибка записи в файл!");
        } catch (IOException e) {
            // алерт
            e.printStackTrace();
        }
        finally {
            try {
                pw.close();
                br.close();
            } catch (IOException e) {
                //алерт - ошибка закрытия потоков
                e.printStackTrace();
            }
        }
    }
}
