package task.manager;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import task.manager.view.utils.ViewConstants;
import task.manager.view.utils.ViewPathConstants;


public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Journal journal = TextMarshaller.getInstance().read(Setting.getPropertyValue("FILE_PATH"));
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ViewPathConstants.PATH_TO_MAIN_FORM_VIEW));
        Parent     root       = fxmlLoader.load();
        
        //MainFormController controller = fxmlLoader.getController();
        //controller.setJournal(journal);
        //controller.initData();
        
        primaryStage.setTitle(ViewConstants.TITLE_TO_MAIN_FORM_VIEW);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}