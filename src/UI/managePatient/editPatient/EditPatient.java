package UI.managePatient.editPatient;

import UI.UIhelper.AlertDialog;
import UI.model.ModelPatient;
import database.DBHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditPatient implements Initializable {
    String title;
    String name;
    String dateOfBirth ;
    String phone ;
    String emergyPhone ;
    String email;
    String address ;
    String medicalHistory;

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_emergcyPhone;

    @FXML
    private TextField tf_dateOfBirth;


    @FXML
    private Button btn_cancel;


    @FXML
    private TextField tf_phone;

    @FXML
    private TextField tf_email;

    @FXML
    private Button btn_update;

    @FXML
    private TextArea ta_address;

    @FXML
    private ComboBox<String> cb_title;

    @FXML
    private TextArea ta_mediHistory;

    @FXML
    void updatePatient(ActionEvent event) {
        if (cb_title.getValue().isEmpty()||tf_name.getText().isEmpty()||tf_dateOfBirth.getText().isEmpty()
                ||tf_phone.getText().isEmpty()||tf_emergcyPhone.getText().isEmpty()||tf_email.getText().isEmpty()
                ||ta_address.getText().isEmpty()||ta_mediHistory.getText().isEmpty()){
            AlertDialog.error(null,"Please enter in all fields");
            return;
        }
        title = cb_title.getValue();
        name = tf_name.getText();
        dateOfBirth = tf_dateOfBirth.getText();
        phone = tf_phone.getText();
        emergyPhone = tf_emergcyPhone.getText();
        email = tf_email.getText();
        address = ta_address.getText();

        String mediHistory = ta_mediHistory.getText();





        String address ="";

        String sql = "UPDATE patients SET TITLE = ?, NAME = ?, DATEOFBIRTH =?, PHONE=?, EMERGPHONE=?, EMAIL=?, " +
                "ADDRESS=?, MEDICALHISTORY = ? WHERE PATIENTID =" + ModelPatient.getInstance().patientId;
        String[] params = {title, name,dateOfBirth, phone, emergyPhone, email, address,mediHistory };
        if (DBHelper.executeUpdate(sql, params)){
            AlertDialog.info(null, "Updated! ");
        }else {
            AlertDialog.error(null, "Failed");
        }

    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage)btn_cancel.getScene().getWindow();
        stage.close();


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showPatient();
        showTitleComboBox();

    }
    private void showPatient(){
        ResultSet rs;
        String sql = "SELECT * FROM PATIENTS WHERE PATIENTID =" + ModelPatient.getInstance().patientId;

        try {
            rs = DBHelper.executeQuery(sql, null);
            if (rs.next()) {
                title = rs.getString("title");
                name = rs.getString("name");
                dateOfBirth = rs.getString("dateofbirth");
                phone = rs.getString("phone");
                emergyPhone = rs.getString("emergphone");
                email = rs.getString("email");
                address = rs.getString("address");
                medicalHistory = rs.getString("medicalhistory");
                cb_title.setValue(title);
                tf_name.setText(name);
               tf_dateOfBirth.setText(dateOfBirth);
                tf_phone.setText(phone);
                tf_emergcyPhone.setText(emergyPhone);
                tf_email.setText(email);
                ta_address.setText(address);
                ta_mediHistory.setText(medicalHistory);

            }

            } catch (SQLException e){
            Logger.getLogger(EditPatient.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            DBHelper.close(DBHelper.getRs(), DBHelper.getPs(), DBHelper.getCon());
        }


    }


    private void showTitleComboBox(){
        cb_title.getItems().addAll(
                "Mr.",
                "Mrs.",
                "Ms.",
                "Miss"
        );
    }
}
