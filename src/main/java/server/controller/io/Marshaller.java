package server.controller.io;


import shared.model.Journal;
import server.controller.exception.ReadFileException;
import server.controller.exception.WriteFileException;


public interface Marshaller {
    /**
     * Function for reading the file
     *
     * @param pathToBackup - path to the backup file
     * @return journal with tasks from the uploaded file
     */
    Journal read(String pathToBackup) throws ReadFileException;
    
    /**
     * Function for writing the file
     *
     * @param journal - journal with tasks
     */
    void write(Journal journal) throws WriteFileException;
}
