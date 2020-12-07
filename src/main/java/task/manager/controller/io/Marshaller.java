package task.manager.controller.io;


import task.manager.model.Journal;

import java.io.IOException;


interface Marshaller {
    Journal read(String path) throws IOException;
    
    void write(Journal journal, String path);
}
