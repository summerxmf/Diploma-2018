package UI.manageDoctor.editDoctor;

import UI.UIhelper.AlertDialog;
import UI.managePatient.editPatient.EditPatient;
import UI.model.ModelDoctor;
import UI.model.ModelPatient;
import database.DBHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditDoctor implements Initializable {
    String name;
    //String dateOfBirth ;
    String homePhone ;
    String workPhone;
    String emergyPhone ;
    String email;
    String address ;

    @FXML
    private TextField tf_emergcyPhone;

    @FXML
    private TextField tf_homePhone;

    @FXML
    private DatePicker dp_dateOfBirth;

    @FXML
    private TextField tf_workPhone;

    @FXML
    private Button btn_cancel;

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_email;

    @FXML
    private Button btn_update;

    @FXML
    private TextArea ta_address;

    @FXML
    void updatePatient(ActionEvent event) {

        name = tf_name.getText();
        //dateOfBirth = dp_dateOfBirth.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        homePhone = tf_homePhone.getText();
        workPhone = tf_workPhone.getText();
        emergyPhone = tf_emergcyPhone.getText();
        email = tf_email.getText();
        address = ta_address.getText();


        if (homePhone.isEmpty()||workPhone.isEmpty()||emergyPhone.isEmpty()||email.isEmpty()
                ||name.isEmpty()||address.isEmpty()){
            AlertDialog.error(null,"Please enter in all fields");
            return;
        }


        String sql = "UPDATE DOCTORS SET NAME = ?, HOMEPHONE=?, WORKPHONE=?, EMERGPHONE=?, EMAIL=?, " +
                "ADDRESS=? WHERE DOCTORID =" + ModelDoctor.getInstance().doctorId;
        String[] params = { name,homePhone, workPhone, emergyPhone, email, address };
        if (DBHelper.executeUpdate(sql, params)){
            AlertDialog.info(null, "Updated! ");
        }else {
            AlertDialog.error(null, "Failed");
        }


    }

    @FXML
    void cancel(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDoctor();

    }
    private void showDoctor(){
        ResultSet rs;
        String sql = "SELECT * FROM DOCTORS WHERE DOCTORID =" + ModelDoctor.getInstance().doctorId;

        try {
            rs = DBHelper.executeQuery(sql, null);
            while (rs.next()) {
                name = rs.getString("name");
                //dateOfBirth = rs.getString("dateofbirth");
                homePhone = rs.getString("homephone");
                workPhone = rs.getString("workphone");
                emergyPhone = rs.getString("emergphone");
                email = rs.getString("email");
                address = rs.getString("address");

                tf_name.setText(name);
                //dp_dateOfBirth.setValue(LocalDate.parse(dateOfBirth,DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                tf_homePhone.setText(homePhone);
                tf_workPhone.setText(workPhone);
                tf_emergcyPhone.setText(emergyPhone);
                tf_email.setText(email);
                ta_address.setText(address);
            }

        } catch (SQLException e){
            Logger.getLogger(EditDoctor.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            DBHelper.close(DBHelper.getRs(), DBHelper.getPs(), DBHelper.getCon());
        }


    }



}
