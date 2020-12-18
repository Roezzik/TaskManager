package task.manager.view.utils;


import javafx.scene.control.Alert;


public class AlertForm {
    
    public static void infoAddAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ViewConstants.TITLE_TO_ALERT_ERROR_ADDING);
        alert.setHeaderText(info);
        alert.showAndWait();
    }
    
    public static void errorAddAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ViewConstants.TITLE_TO_ALERT_ERROR_ADDING);
        alert.setHeaderText(info);
        alert.showAndWait();
    }
    
    public static void infoEditAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ViewConstants.TITLE_TO_ALERT_ERROR_EDITING);
        alert.setHeaderText(info);
        alert.showAndWait();
    }
    
    public static void errorEditAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ViewConstants.TITLE_TO_ALERT_ERROR_EDITING);
        alert.setHeaderText(info);
        alert.showAndWait();
    }

    public static void infoNewAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ViewConstants.TITLE_TO_ALERT_ERROR_EXIST_FILE);
        alert.setHeaderText(info);
        alert.showAndWait();
    }
}
