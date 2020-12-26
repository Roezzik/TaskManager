//package com.netcracker.task.manager.controller.io;
//
//
//import com.netcracker.task.manager.model.Journal;
//
//import java.io.*;
//
//
//public class BinaryMarshaller implements Marshaller {
//
//    private static BinaryMarshaller instance;
//
//    private BinaryMarshaller() {
//    }
//
//    public static BinaryMarshaller getInstance() {
//        if (instance == null) {
//            instance = new BinaryMarshaller();
//        }
//        return instance;
//    }
//
//    @Override
//    public Journal read(String path) {
//        Journal journal = null;
//        try (FileInputStream fis = new FileInputStream(path);
//             ObjectInputStream ois = new ObjectInputStream(fis)) {
//            journal = (Journal) ois.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace(); // todo you know
//        }
//        return journal;
//    }
//
//    @Override
//    public void write(Journal journal, String path) {
//
//        try (FileOutputStream fos = new FileOutputStream(path);
//             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
//            oos.writeObject(journal);
//        } catch (IOException e) {
//            e.printStackTrace(); // todo you know
//        }
//    }
//
//}
