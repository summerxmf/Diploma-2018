package UI.managePatient.addPatient;

import UI.UIhelper.AlertDialog;
import database.DBHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddPatient implements Initializable {


    @FXML // fx:id="btn_clear"
    private Button btn_clear; // Value injected by FXMLLoader

    @FXML // fx:id="tf_addressUnit"
    private TextField tf_addressUnit; // Value injected by FXMLLoader

    @FXML // fx:id="tf_firstName"
    private TextField tf_firstName; // Value injected by FXMLLoader

    @FXML // fx:id="tf_lastName"
    private TextField tf_lastName; // Value injected by FXMLLoader

    @FXML // fx:id="btn_save"
    private Button btn_save; // Value injected by FXMLLoader

    @FXML // fx:id="tf_email"
    private TextField tf_email; // Value injected by FXMLLoader

    @FXML // fx:id="cb_state"
    private ComboBox<String> cb_state; // Value injected by FXMLLoader

    @FXML // fx:id="tf_addressCity"
    private TextField tf_addressCity; // Value injected by FXMLLoader

    @FXML // fx:id="tf_emergcyPhone"
    private TextField tf_emergcyPhone; // Value injected by FXMLLoader

    @FXML // fx:id="tf_dateOfBirth"
    private TextField tf_dateOfBirth; // Value injected by FXMLLoader

    @FXML // fx:id="tf_workPhone"
    private TextField tf_phone; // Value injected by FXMLLoader


    @FXML // fx:id="btn_cancel"
    private Button btn_cancel; // Value injected by FXMLLoader

    @FXML // fx:id="tf_addressStreet"
    private TextField tf_addressStreet; // Value injected by FXMLLoader

    @FXML // fx:id="cb_title"
    private ComboBox<String> cb_title;
    // Value injected by FXMLLoader

    @FXML // fx:id="ta_mediHistory"
    private TextArea ta_mediHistory;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_title.getItems().addAll(
                "Mr.",
                "Mrs.",
                "Ms.",
                "Miss"
        );
        cb_state.getItems().addAll(
                "ACT", "NSW","QLD","SA","VIC","WA"
        );


    }


    @FXML
    void addPatient(ActionEvent event) {

        if (cb_title.getValue().isEmpty()||tf_firstName.getText().isEmpty()|| tf_lastName.getText().isEmpty()
        ||tf_dateOfBirth.getText().isEmpty()|| tf_phone.getText().isEmpty()||tf_emergcyPhone.getText().isEmpty()
        ||tf_email.getText().isEmpty()||tf_addressStreet.getText().isEmpty()|| tf_addressCity.getText().isEmpty()
        ||cb_state.getValue().isEmpty()||ta_mediHistory.getText().isEmpty()){
            AlertDialog.error(null,"Please enter in all fields");
            return;
        }
        String title = cb_title.getValue();
        String firstName = tf_firstName.getText();
        System.out.println(firstName);
        String lastName = tf_lastName.getText();


        String phone = tf_phone.getText();
        String emergencyPhone = tf_emergcyPhone.getText();
        String email = tf_email.getText();
        String addressUnit = tf_addressUnit.getText();
        String addressStreet = tf_addressStreet.getText();
        String addressCity = tf_addressCity.getText();
        String addressState =  cb_state.getValue();

        String mediHistory = ta_mediHistory.getText();

        String name = firstName + " " +lastName;
        String dateOfBirth =tf_dateOfBirth.getText();


        String address ="";
        if (addressUnit.isEmpty()){
            address = addressStreet + ", " + addressCity + ", " + addressState;
        }else {
            address =  addressUnit + "/" + addressStreet + ", " + addressCity + ", " + addressState;
        }


        String sql = "INSERT patients VALUES(?,?,?,?,?,?, ?, ?)";
        String[] params = {title, name,dateOfBirth, phone, emergencyPhone, email, address,
                mediHistory };
        if (DBHelper.executeUpdate(sql, params)){
            AlertDialog.info(null, "Saved! ");
        }else {
            AlertDialog.error(null, "Failed");
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage)btn_cancel.getScene().getWindow();
        stage.close();

    }

    @FXML
    void clearCells (ActionEvent event) {
        tf_firstName.setText("");
        tf_lastName.setText("");
        tf_phone.setText("");
        tf_emergcyPhone.setText("");
        tf_email.setText("");
        tf_addressUnit.setText("");
        tf_addressStreet.setText("");
        tf_addressCity.setText("");
        ta_mediHistory.setText("");
        tf_dateOfBirth.setText("");
        cb_title.getSelectionModel().clearSelection();
        cb_state.getSelectionModel().clearSelection();

    }
}
