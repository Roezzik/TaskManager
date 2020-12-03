package sample.controller.work_with_file.binaryfile;

import sample.controller.resourсes.Dependences;
import sample.model.Journal;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WriteJournalForBinaryFile {

    public static void write(Journal journal, String path) {

        try ( FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(journal);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
