package com.netcracker.task.manager;


import com.netcracker.task.manager.controller.BackupManager;
import com.netcracker.task.manager.controller.Controller;
import com.netcracker.task.manager.controller.IdGenerator;
import com.netcracker.task.manager.controller.PropertyReadException;
import com.netcracker.task.manager.controller.factory.JournalFactory;
import com.netcracker.task.manager.controller.io.BinaryMarshaller;
import com.netcracker.task.manager.controller.io.TextMarshaller;
import com.netcracker.task.manager.controller.io.exception.*;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.view.utils.AlertForm;
import com.netcracker.task.manager.view.utils.ViewPathConstants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.netcracker.task.manager.view.utils.ViewConstants;

import java.io.IOException;


public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        JournalFactory journalFactory = new JournalFactory();
        Journal        journal        = journalFactory.create();
        BackupManager  backupManager  = new BackupManager();
        
        try {
            journal = backupManager.readDefaultBackup();
            if (TextMarshaller.getInstance().checkCreateFile() | BinaryMarshaller.getInstance().checkCreateFile()) {
                AlertForm.helloAlert(ViewConstants.ERROR_BACKUP_NOT_FOUND);
            }
        } catch (PropertyReadException | CreateFileException | IOException | BufferedReaderException
                | TextMarshallerReadException | FileInputStreamException e) {
            AlertForm.errorAlert(e.getMessage());
            System.exit(2);
        }
        
        Controller controller = Controller.getInstance();
        controller.addTasks(journal);
        
        IdGenerator.getInstance(controller.getLastTaskId());
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ViewPathConstants.PATH_TO_MAIN_FORM_VIEW));
        Parent     root       = fxmlLoader.load();
        primaryStage.setTitle(ViewConstants.TITLE_TO_MAIN_FORM_VIEW);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            try {
                backupManager.writeBackup(controller.getJournal());
            } catch (PropertyReadException | CreateFileException | PrintWriterException | FileOutputStreamException e) {
                AlertForm.errorAlert(e.getMessage());
                System.exit(3);
            }
            Platform.exit();
            System.exit(0);
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}