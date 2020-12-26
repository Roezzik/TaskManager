package com.netcracker.task.manager.controller;


import com.netcracker.task.manager.controller.io.BinaryMarshaller;
import com.netcracker.task.manager.controller.io.exception.*;
import com.netcracker.task.manager.controller.io.Marshaller;
import com.netcracker.task.manager.controller.io.TextMarshaller;
import com.netcracker.task.manager.model.Journal;

import java.io.IOException;


public class BackupManager {
    
    private Marshaller marshaller;
    
    private void chooseMarshaller() throws PropertyReadException {
        
        String fileFormat = PropertyParser.getPropertyValue("backup_format");
        
        if (fileFormat.equals(FileFormat.TEXT.getTitle())) {
            marshaller = TextMarshaller.getInstance();
        } else if (fileFormat.equals(FileFormat.BINARY.getTitle())) {
            marshaller = BinaryMarshaller.getInstance();
        }
    }
    
    public void writeBackupJournal(Journal journal) throws CreateFileException, PropertyReadException,
                                                           PrintWriterException, FileOutputStreamException {
        chooseMarshaller();
        marshaller.write(journal);
    }
    
    public Journal readBackupJournal() throws PropertyReadException, TextMarshallerReadException, CreateFileException,
                                              IOException, BufferedReaderException, FileInputStreamException {
        chooseMarshaller();
        return marshaller.read();
    }
    
    enum FileFormat {
        
        TEXT("txt"),
        BINARY("bin");
        
        private final String title;
        
        FileFormat(String title) {
            this.title = title;
        }
        
        public String getTitle() {
            return title;
        }
    }
}
