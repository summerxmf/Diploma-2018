package UI.manageAppointments.makeAppointment;


import UI.UIhelper.AlertDialog;
import UI.main.main1.MainController1;
import UI.model.ModelAppointment;
import database.DBHelper;
import domain.Doctor;
import domain.Patient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SelectPatient implements Initializable {
//    // Controllers
//
//    private MakeAppointment mainController ;
//
//    //Set main controller
//    public void setMakeAppointment(MakeAppointment mainController) {
//        this.mainController = mainController ;
//    }

    ObservableList<Patient> listPatient = FXCollections.observableArrayList();
    Doctor apptDoctor ;
    int doctorId;

    LocalDate apptDate ;
    String apptDateStr;
    int apptSlot ;
    Patient apptPatient;
    int patientId;

    @FXML
    private Label lb_date;
    @FXML
    private Label lb_doctor;

    @FXML
    private Label lb_strarTime;

    @FXML
    private TableColumn<Patient, String> phoneCol;

    @FXML
    private TableColumn<Patient, String> titleCol;

    @FXML
    private TableColumn<Patient, String> dateOfBirthCol;

    @FXML
    private TableColumn<Patient, String> addressCol;

    @FXML
    private TableColumn<Patient, String> mediHistoryCol;


    @FXML
    private Button btn_confrm;

    @FXML
    private TableColumn<Patient, Integer> idCol;

    @FXML
    private Button btn_cancel;

    @FXML
    private TextField tf_filter;

    @FXML
    private TableColumn<Patient, String> nameCol;

    @FXML
    private TableColumn<Patient, String> emailCol;

    @FXML
    private TableView<Patient> tv_patient;

    @FXML
    private TableColumn<Patient, String> emergPhoneCol;




    @FXML
    void confrim_makeAppointment(ActionEvent event) {
        if (tv_patient.getSelectionModel().getSelectedItem() == null) {
            AlertDialog.error(null, "Please select a patient!");
            return;
        }
        apptPatient = tv_patient.getSelectionModel().getSelectedItem();
        patientId = apptPatient.getPatientId();

        doctorId = apptDoctor.getDoctorId();
        apptSlot = ModelAppointment.getInstance().apptSlot;



        String sql = "INSERT INTO APPOINTMENTS VALUES( ?, ? , ? , ? )";


        String[] params = {Integer.toString(doctorId), Integer.toString(patientId), apptDateStr, Integer.toString(apptSlot)};
        if (DBHelper.executeUpdate(sql, params)) {
            AlertDialog.info(null, "You have made an appointment for " + apptPatient.getName());
            Stage stage = (Stage)btn_confrm.getScene().getWindow();
            stage.close();


            updateTableSchedules(doctorId, apptDateStr, apptSlot);


        } else {
            AlertDialog.error(null, "Failed");
        }
    }
    private void updateTableSchedules(int doctorId, String date, int slot) {

        int apptId = getAppointmentId(doctorId, apptDateStr, apptSlot);
        if (apptId != 0) {
            String sql = "UPDATE SCHEDULES SET _" + slot + " = ?  WHERE SCHDDATE= ? AND DOCTORID = ?";
            String[] params = {Integer.toString(apptId), date, Integer.toString(doctorId)};
            //!!!!
            DBHelper.executeUpdate(sql, params);
        }
    }

    private int getAppointmentId(int doctorId, String date, int slot){
        ResultSet rs;
        String sql = "SELECT APPTID FROM APPOINTMENTS WHERE DOCTORID =? AND DATE = ? AND SLOT =?";
        String[] params = {Integer.toString(doctorId), date, Integer.toString(slot)};
        rs = DBHelper.executeQuery(sql, params);
        try {
            while (rs.next()){
                int apptId = rs.getInt("apptid");
                return apptId;

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBHelper.close(DBHelper.getRs(),DBHelper.getPs(),DBHelper.getCon());
        }
        return 0;

     }


    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage)btn_cancel.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initLabel();
        initPatientCol();
        loadData_tv_patient();
        initFilter();

    }

    private void initLabel(){
        apptDoctor = ModelAppointment.getInstance().apptDoctor;
        apptDate =ModelAppointment.getInstance().apptDate;
        apptDateStr =  apptDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        apptSlot = ModelAppointment.getInstance().apptSlot;


        lb_doctor.setText("Doctor: " + apptDoctor.getName());
        lb_date.setText("Date: " + apptDateStr);
        lb_strarTime.setText("Start time: " + apptSlot);

    }


    private void initPatientCol(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emergPhoneCol.setCellValueFactory(new PropertyValueFactory<>("emergyPhone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        mediHistoryCol.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));



    }
    private void loadData_tv_patient(){
        ResultSet rs ;
        String sql = "SELECT * FROM PATIENTS";
        try {
            rs = DBHelper.executeQuery(sql, null);
            while(rs.next()){
                int id = rs.getInt("patientId");
                String title = rs.getString("title");
                String name = rs.getString("name");
                String dateOfBirth = rs.getString("dateofbirth");
                String phone = rs.getString("phone");
                String emergyphone = rs.getString("emergphone");
                String email = rs.getString("email");
                String address = rs.getString("address");

                String medicalHistory = rs.getString("medicalhistory");

                listPatient.add(new Patient(id, title,name,dateOfBirth, phone, emergyphone, email, address, medicalHistory));
            }


        }catch (SQLException e){
            Logger.getLogger(MainController1.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            DBHelper.close(DBHelper.getRs(), DBHelper.getPs(), DBHelper.getCon());
        }
        tv_patient.getItems().addAll(listPatient);
    }

    private void initFilter(){
        FilteredList<Patient> filteredData = new FilteredList<>(listPatient, e->true);
        tf_filter.setOnKeyReleased(e ->{
            tf_filter.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    filteredData.setPredicate((Predicate<? super Patient>) patient ->{
                        if(newValue == null || newValue.isEmpty()){
                            return true;
                        }
                        String lowerCaseFilter = newValue.toLowerCase();
                        if(patient.getName().toLowerCase().contains(lowerCaseFilter)){
                            return true;
                        }else if(patient.getPhone().contains(lowerCaseFilter)){
                            return true;
                        }else if(patient.getEmail().toLowerCase().contains(lowerCaseFilter)){
                            return true;
                        }
                        return false;
                    });
                }
            });
        });
        SortedList<Patient> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tv_patient.comparatorProperty());
        tv_patient.setItems(sortedList);
    }





}

