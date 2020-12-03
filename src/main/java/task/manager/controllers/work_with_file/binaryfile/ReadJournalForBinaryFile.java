package sample.controller.work_with_file.binaryfile;

import sample.controller.resourсes.Dependences;
import sample.model.Journal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class ReadJournalForBinaryFile {

    public static Journal read(String path){
        Journal journal = null;
        try ( FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            journal = (Journal) ois.readObject();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return journal;
    }
}
