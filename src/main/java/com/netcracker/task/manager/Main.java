package com.netcracker.task.manager;


import com.netcracker.task.manager.controller.BackupManager;
import com.netcracker.task.manager.controller.Controller;
import com.netcracker.task.manager.controller.IdGenerator;
import com.netcracker.task.manager.controller.PropertyReadException;
import com.netcracker.task.manager.controller.io.Exception.CreateFileException;
import com.netcracker.task.manager.controller.io.Exception.TextMarshallerReadException;
import com.netcracker.task.manager.model.Journal;
import com.netcracker.task.manager.view.utils.Refresher;
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
        
        Controller controller = Controller.getInstance();
        Journal       journal       = new Journal();
        BackupManager backupManager = new BackupManager();
        
        try {
            journal = backupManager.readBackupJournal();
        } catch (PropertyReadException | CreateFileException | IOException | TextMarshallerReadException e) {
            e.printStackTrace();
        }
        
        controller.addTasks(journal);
        controller.initAllTask();
        IdGenerator.getInstance(controller.getLastTaskId());
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ViewPathConstants.PATH_TO_MAIN_FORM_VIEW));
        Parent     root       = fxmlLoader.load();
        primaryStage.setTitle(ViewConstants.TITLE_TO_MAIN_FORM_VIEW);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        Refresher.getInstance().getMainFormController().refreshTable();
        primaryStage.setOnCloseRequest(event -> {
            try {
                backupManager.writeBackupJournal(controller.getJournal());
            } catch (PropertyReadException | CreateFileException e) {
                e.printStackTrace();
            }
            Platform.exit();
            System.exit(0);
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}