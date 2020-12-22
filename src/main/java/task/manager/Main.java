package task.manager;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import task.manager.controller.Controller;
import task.manager.view.utils.ViewConstants;
import task.manager.view.utils.ViewPathConstants;


public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = Controller.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ViewPathConstants.PATH_TO_MAIN_FORM_VIEW));
        Parent     root       = fxmlLoader.load();
        
        primaryStage.setTitle(ViewConstants.TITLE_TO_MAIN_FORM_VIEW);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> controller.write());
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}