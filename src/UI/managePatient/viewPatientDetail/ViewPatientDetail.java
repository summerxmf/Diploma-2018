package UI.managePatient.viewPatientDetail;
import UI.manageAppointments.viewAppointments.ViewAppointments;
import UI.model.ModelPatient;
import database.DBHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewPatientDetail implements Initializable {
    int patientId;
    String patientTitle ="";
    String patientName = "";
    Date patientBirth ;
    String patientPhone ="";
    String patientEmergyPhone ="";
    String patientEmail ="";
    String patientAddress = "";

    String patientMedicalHistory="";

    @FXML
    private Text address;

    @FXML
    private Text phone;

    @FXML
    private Text name;

    @FXML
    private Text dateOfBirth;

    @FXML
    private Text title;

    @FXML
    private Text medicalHistory;



    @FXML
    private Text emergyPhone;

    @FXML
    private Text email;

    @FXML
    private Button btn_close;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patientId=  ModelPatient.getInstance().patientId ;
        getDataFromDB();
        setDataToUI();


    }
    private  void getDataFromDB(){

        String sql = "SELECT * FROM PATIENTS WHERE PATIENTID = ?";
        String []params ={Integer.toString(ModelPatient.getInstance().patientId)};

        ResultSet rs = DBHelper.executeQuery(sql, params);
        try {
        while (rs.next()) {
            patientTitle = rs.getString("title");
            patientName = rs.getString("name");
            patientBirth = rs.getDate("dateofbirth");
            patientPhone = rs.getString("phone");
            patientEmergyPhone =rs.getString("emergphone");
            patientEmail =rs.getString("email");
            patientAddress = rs.getString("address");

            patientMedicalHistory = rs.getString("medicalhistory");

        }
    } catch (SQLException e) {
        Logger.getLogger(ViewAppointments.class.getName()).log(Level.SEVERE, null, e);
    } finally {
        DBHelper.close(DBHelper.getRs(), DBHelper.getPs(), DBHelper.getCon());
    }
}

    private void setDataToUI(){


    title.setText(patientTitle);
    name.setText(patientName);
    dateOfBirth.setText(new SimpleDateFormat("yyyy-MM-dd").format(patientBirth));
    phone.setText(patientPhone);
    emergyPhone.setText(patientEmergyPhone);
    email.setText(patientEmail);
    address.setText(patientAddress);

    medicalHistory.setText(patientMedicalHistory);

}
}
