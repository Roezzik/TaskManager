package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.controller.Controller;
import server.controller.exception.ReadFileException;
import server.controller.exception.WriteFileException;
import server.controller.io.BinaryMarshaller;
import server.controller.io.TextMarshaller;
import server.controller.utils.BackupManager;
import shared.Connection;
import shared.ConnectionListener;
import shared.constant.ViewConstants;
import shared.constant.ViewPathConstants;
import shared.factory.JournalFactory;
import shared.model.Journal;
import shared.utils.AlertForm;
import shared.utils.IdGenerator;

import java.io.*;

public class Client extends Application implements ConnectionListener {

    private Connection connection;

    public void start(Stage primaryStage) {
        try {
            connection = new Connection(this, "localhost", 8080);
            JournalFactory journalFactory = new JournalFactory();
            Journal journal = journalFactory.create();
            BackupManager backupManager = BackupManager.getInstance();

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
            Parent root = fxmlLoader.load();
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
         catch (IOException e) {
             System.out.println("ыыыыы");
             System.exit(0);
        }
    }

    public static void main(String[] args) { launch(args);
    }

    @Override
    public void connect(Connection connection) {
        System.out.println("so good");
    }

    @Override
    public void receive(Connection connection, String string) {
    }

    @Override
    public void send(Connection connection, String string) {
    }

    @Override
    public void disconnect(Connection connection) {
        System.out.println("bye");

    }
}
