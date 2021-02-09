package shared.utils;


import shared.constant.ViewConstants;
import javafx.scene.control.Alert;


/**
 * Class for creating alerts
 */
public class AlertForm {
    
    public static void errorAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ViewConstants.TITLE_TO_ERROR);
        alert.setHeaderText(info);
        alert.showAndWait();
    }
    
    public static void helloAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ViewConstants.TITLE_TO_HELLO);
        alert.setHeaderText(info);
        alert.showAndWait();
    }
    
    public static void infoAddAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ViewConstants.TITLE_TO_ALERT_ERROR_ADDING);
        alert.setHeaderText(info);
        alert.showAndWait();
    }
    
    public static void warningAddAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
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
    
    public static void warningEditAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(ViewConstants.TITLE_TO_ALERT_ERROR_ADDING);
        alert.setHeaderText(info);
        alert.showAndWait();
    }
    
    public static void warningPostponeAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(ViewConstants.TITLE_TO_POSTPONE_ERROR_EDITING);
        alert.setHeaderText(info);
        alert.showAndWait();
    }
}
