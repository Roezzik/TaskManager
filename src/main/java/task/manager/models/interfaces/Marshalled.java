package sample.model.interfaces;

import sample.model.Journal;

import java.io.IOException;

public interface Marshalled {
    Journal read(String path) throws IOException;
    void write(Journal journal, String path);
}
