package task.manager.view.editForm;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import task.manager.view.utils.ViewConstants;
import task.manager.view.utils.ViewPathConstants;


public class EditTaskForm {
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(ViewPathConstants.PATH_TO_EDIT_FORM_VIEW));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(ViewConstants.TITLE_TO_EDIT_FORM_VIEW);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
