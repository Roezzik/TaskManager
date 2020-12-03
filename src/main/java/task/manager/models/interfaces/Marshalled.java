package task.manager.models.interfaces;

import task.manager.models.Journal;

import java.io.IOException;

public interface Marshalled {
    Journal read(String path) throws IOException;
    void write(Journal journal, String path);
}
