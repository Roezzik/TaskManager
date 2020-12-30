package com.netcracker.task.manager;


import com.netcracker.task.manager.controller.BackupManager;
import com.netcracker.task.manager.controller.Controller;
import com.netcracker.task.manager.controller.IdGenerator;
import com.netcracker.task.manager.controller.exception.*;
import com.netcracker.task.manager.controller.factory.JournalFactory;
import com.netcracker.task.manager.controller.io.BinaryMarshaller;
import com.netcracker.task.manager.controller.io.TextMarshaller;
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


/**
 * Class from which the application starts working
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        JournalFactory journalFactory = new JournalFactory();
        Journal        journal        = journalFactory.create();
        BackupManager  backupManager  = BackupManager.getInstance();
        
        try {
            journal = backupManager.readDefaultBackup();
            if (TextMarshaller.getInstance().checkCreateFile() | BinaryMarshaller.getInstance().checkCreateFile()) {
                AlertForm.helloAlert(ViewConstants.ERROR_BACKUP_NOT_FOUND);
            }
        } catch (ReadFileException e) {
            AlertForm.errorAlert(e.getMessage() + " Create new file!");
        }
        
        Controller controller = Controller.getInstance();
        controller.restoreBackupTasks(journal);
        
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
            } catch (WriteFileException e) {
                AlertForm.errorAlert(e.getMessage());
            }
            Platform.exit();
            System.exit(0);
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}