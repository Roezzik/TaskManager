package task.manager.controller.io;


import task.manager.controller.io.Exception.CreateFileException;
import task.manager.controller.io.Exception.TextMarshallerReadException;
import task.manager.model.Journal;

import java.io.IOException;


public interface Marshaller {
    Journal read(String path) throws IOException, TextMarshallerReadException, CreateFileException;

    void write(Journal journal, String path) throws CreateFileException;
}
