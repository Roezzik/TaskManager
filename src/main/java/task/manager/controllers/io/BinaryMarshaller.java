package task.manager.controllers.io;

import task.manager.models.Journal;
import task.manager.models.interfaces.Marshalled;
import java.io.*;

public class BinaryMarshaller implements Marshalled {

    @Override
    public Journal read(String path) {
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

    @Override
    public  void write(Journal journal, String path) {

        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(journal);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
