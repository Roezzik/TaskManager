package task.manager;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import task.manager.controller.io.TextMarshaller;
import task.manager.model.Journal;
import task.manager.view.ViewPathConstants;

import java.io.IOException;


public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Journal journal = TextMarshaller.getInstance().read(Setting.getPropertyValue("FILE_PATH"));
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ViewPathConstants.PATH_TO_MAIN_FORM_VIEW));
        Parent     root       = fxmlLoader.load();
        
        //MainFormController controller = fxmlLoader.getController();
        //controller.setJournal(journal);
        //controller.initData();
        
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
//        primaryStage.setOnCloseRequest(new javafx.event.EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent event) {
//                    TextMarshaller.getInstance().write(journal, Setting.getPropertyValue("FILE_PATH"));
//            }
//        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}