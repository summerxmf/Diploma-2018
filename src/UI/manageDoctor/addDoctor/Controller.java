package UI.manageDoctor.addDoctor;

import database.DBHelper;
import UI.UIhelper.AlertDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    ObservableList<String> states = FXCollections
            .observableArrayList("ACT", "NSW","QLD","SA","VIC","WA");

    @FXML
    private TextField tf_addressUnit;

    @FXML
    private TextField tf_firstName;

    @FXML
    private TextField tf_homePhone;

    @FXML
    private TextField tf_lastName;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_clear;

    @FXML
    private TextField tf_email;

    @FXML
    private ComboBox<String> cb_state;

    @FXML
    private TextField tf_addressCity;

    @FXML
    private TextField tf_emergcyPhone;

    @FXML
    private DatePicker dp_dateOfBirth;

    @FXML
    private TextField tf_workPhone;

    @FXML
    private Button btn_cancel;

    @FXML
    private TextField tf_addressStreet;
        @FXML
    void addDoctor(ActionEvent event) {
        if (tf_firstName.getText().isEmpty()||tf_lastName.getText().isEmpty()||tf_homePhone.getText().isEmpty()
                ||tf_workPhone.getText().isEmpty()||tf_emergcyPhone.getText().isEmpty()
                ||tf_email.getText().isEmpty()||tf_addressStreet.getText().isEmpty()||tf_addressCity.getText().isEmpty()
                ||cb_state.getValue().isEmpty()){
            AlertDialog.error(null,"Please enter in all fields");
            return;
        }
        String firstName = tf_firstName.getText();
        String lastName = tf_lastName.getText();
        String name = firstName + " " +lastName;
        String homePhone = tf_homePhone.getText();
        String workPhone = tf_workPhone.getText();
        String emergencyPhone = tf_emergcyPhone.getText();
        String email = tf_email.getText();
        String addressUnit = tf_addressUnit.getText();
        String addressStreet = tf_addressStreet.getText();
        String addressCity = tf_addressCity.getText();
        String addressState =  cb_state.getValue();



        String address ="";
        if (addressUnit.isEmpty()){
            address = addressStreet + ", " + addressCity + ", " + addressState;
        }else {
            address =  addressUnit + "/" + addressStreet + ", " + addressCity + ", " + addressState;
        }


        String sql = "INSERT doctors VALUES(?,?,?,?,?,?)";
        String[] params = {name, homePhone, workPhone, emergencyPhone, email, address};
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
        tf_homePhone.setText("");
        tf_workPhone.setText("");
        tf_emergcyPhone.setText("");
        tf_email.setText("");
        tf_addressUnit.setText("");
        tf_addressStreet.setText("");
        tf_addressCity.setText("");
        dp_dateOfBirth.getEditor().clear();
        cb_state.getSelectionModel().clearSelection();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_state.setItems(states);

    }
}
