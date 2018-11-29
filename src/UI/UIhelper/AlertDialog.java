package UI.UIhelper;

import javafx.scene.control.Alert;

public class AlertDialog {
    public static void error(String title, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void info(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
