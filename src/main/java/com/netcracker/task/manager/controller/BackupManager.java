package com.netcracker.task.manager.controller;


//import com.netcracker.task.manager.controller.io.BinaryMarshaller;
import com.netcracker.task.manager.controller.io.Exception.CreateFileException;
import com.netcracker.task.manager.controller.io.Exception.TextMarshallerReadException;
import com.netcracker.task.manager.controller.io.Marshaller;
import com.netcracker.task.manager.controller.io.TextMarshaller;
import com.netcracker.task.manager.model.Journal;

import java.io.IOException;


public class BackupManager {
    
    private             Marshaller marshaller;
    public static final String     TXT_FORMAT = "txt", BIN_FORMAT = "bin";
    
    private void chooseFileFormat(String fileFormat) {
        
        if (fileFormat.equals(TXT_FORMAT)) {
            marshaller = TextMarshaller.getInstance();
        } else if (fileFormat.equals(BIN_FORMAT)) {
            //marshaller = BinaryMarshaller.getInstance();
        }
    }
    
    public void writeBackupJournal(Journal journal, String fileFormat) throws CreateFileException, PropertyReadException {
        chooseFileFormat(fileFormat);
        marshaller.write(journal);
    }
    
    public Journal readBackupJournal(String fileFormat) throws PropertyReadException, TextMarshallerReadException, CreateFileException, IOException {
        chooseFileFormat(fileFormat);
        return marshaller.read();
    }
}
