package task.manager.controller.io;


import task.manager.model.Journal;

import java.io.*;


public class BinaryMarshaller implements Marshaller {
    
    private static final BinaryMarshaller bm = new BinaryMarshaller();
    
    public static BinaryMarshaller getBinaryMarshaller() {
        return bm;
    }
    
    private BinaryMarshaller() {
    }
    
    @Override
    public Journal read(String path) {
        Journal journal = null;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            journal = (Journal) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return journal;
    }
    
    @Override
    public void write(Journal journal, String path) {
        
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(journal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}