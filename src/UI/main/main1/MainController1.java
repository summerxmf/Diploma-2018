package UI.main.main1;

import UI.UIhelper.LoadWindow;
import UI.model.ModelDoctor;
import UI.model.ModelPatient;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController1 implements Initializable {

    ObservableList<Doctor> listDoctor = FXCollections.observableArrayList();
    ObservableList<Patient> listPatient = FXCollections.observableArrayList();



    @FXML // fx:id="idCol_Doctor"
    private TableColumn<Doctor, Integer> idCol_Doctor; // Value injected by FXMLLoader

    @FXML // fx:id="nameCol_Patient"
    private TableColumn<Patient, String> nameCol_Patient; // Value injected by FXMLLoader

    @FXML // fx:id="addressCol_Doctor"
    private TableColumn<Doctor, String> addressCol_Doctor; // Value injected by FXMLLoader

    @FXML // fx:id="btn_setting"
    private Button btn_makeSchedule; // Value injected by FXMLLoader

    @FXML // fx:id="workphoneCol_Doctor"
    private TableColumn<Doctor, String> workphoneCol_Doctor; // Value injected by FXMLLoader


    @FXML // fx:id="tb_doctorInfo"
    private Tab tb_doctorInfo; // Value injected by FXMLLoader

    @FXML // fx:id="btn_viewDoctors"
    private Button btn_viewDoctors; // Value injected by FXMLLoader

    @FXML // fx:id="btn_refreshDoctor"
    private Button btn_refreshDoctor; // Value injected by FXMLLoader

    @FXML // fx:id="titleCol_Patient"
    private TableColumn<Patient, String> titleCol_Patient; // Value injected by FXMLLoader

    @FXML // fx:id="dateOfBirthCol_Patient"
    private TableColumn<Patient, Date> dateOfBirthCol_Patient; // Value injected by FXMLLoader


    @FXML // fx:id="btn_refreshPatient"
    private Button btn_refreshPatient; // Value injected by FXMLLoader

    @FXML // fx:id="btn_savePatient"
    private Button btn_updatePatient; // Value injected by FXMLLoader

    @FXML // fx:id="tv_doctor"
    private TableView<Doctor> tv_doctor; // Value injected by FXMLLoader

    @FXML // fx:id="emergPhoneCol_Patient1"
    private TableColumn<Patient, String> emergPhoneCol_Patient; // Value injected by FXMLLoader

    @FXML // fx:id="homephoneCol_Doctor"
    private TableColumn<Doctor, String> homephoneCol_Doctor; // Value injected by FXMLLoader

    @FXML // fx:id="btn_deleteDoctor"
    private Button btn_deleteDoctor; // Value injected by FXMLLoader


    @FXML // fx:id="idCol_Patient"
    private TableColumn<Patient, Integer> idCol_Patient; // Value injected by FXMLLoader

    @FXML // fx:id="emailCol_Patient"
    private TableColumn<Patient, String> emailCol_Patient; // Value injected by FXMLLoader

    @FXML // fx:id="mediHistoryCol_Patient"
    private TableColumn<Patient, String> mediHistoryCol_Patient; // Value injected by FXMLLoader

    @FXML // fx:id="emergyphoneCol_Doctor"
    private TableColumn<Doctor, String> emergyphoneCol_Doctor; // Value injected by FXMLLoader

    @FXML // fx:id="tb_patientInfo"
    private Tab tb_patientInfo; // Value injected by FXMLLoader

    @FXML // fx:id="btn_deletePatient"
    private Button btn_deletePatient; // Value injected by FXMLLoader

    @FXML // fx:id="btn_saveDoctor"
    private Button btn_editDoctor; // Value injected by FXMLLoader

    @FXML // fx:id="btn_cancelDoctor"
    private Button btn_cancelDoctor; // Value injected by FXMLLoader

    @FXML // fx:id="btn_addDoctor"
    private Button btn_addDoctor; // Value injected by FXMLLoader

    @FXML // fx:id="btn_addPatient"
    private Button btn_addPatient; // Value injected by FXMLLoader

    @FXML // fx:id="tf_searchPatient"
    private TextField tf_searchPatient; // Value injected by FXMLLoader


    @FXML // fx:id="btn_cancelPatient"
    private Button btn_cancelPatient; // Value injected by FXMLLoader

    @FXML // fx:id="btn_makeAppointment"
    private Button btn_makeAppointment; // Value injected by FXMLLoader

    @FXML // fx:id="nameCol_Doctor"
    private TableColumn<Doctor, String> nameCol_Doctor; // Value injected by FXMLLoader

    @FXML // fx:id="phoneCol_Patient"
    private TableColumn<Patient, String> phoneCol_Patient; // Value injected by FXMLLoader

    @FXML // fx:id="addressCol_Patient"
    private TableColumn<Patient, String> addressCol_Patient; // Value injected by FXMLLoader

    @FXML // fx:id="tf_searchDoctor"
    private TextField tf_searchDoctor; // Value injected by FXMLLoader

    @FXML // fx:id="tv_patient"
    private TableView<Patient> tv_patient; // Value injected by FXMLLoader

    @FXML // fx:id="emailCol_Doctor"
    private TableColumn<Doctor, String> emailCol_Doctor; // Value injected by FXMLLoader




    @FXML
        void addPatient_PatientInfo(ActionEvent event) {
            loadWindow("/UI/managePatient/addPatient/addPatient.fxml", "Add Patient");

        }

        @FXML
        void deletePatient_PatientInfo(ActionEvent event) {
            if(tv_patient.getSelectionModel().getSelectedItem()!=null) {
                Patient selectPatient = tv_patient.getSelectionModel().getSelectedItem();
                int selectId = selectPatient.getPatientId();
                String sql = "DELETE FROM PATIENTS WHERE PATIENTID = ?";
                String[] params = {Integer.toString(selectId)};
                DBHelper.executeUpdate(sql, params);
            }

        }

        @FXML
        void refreshPatient_PatientInfo(ActionEvent event) {

        }

        @FXML
        void updatePatient_PatientInfo(ActionEvent event) {
            if(tv_patient.getSelectionModel().getSelectedItem()!=null) {
                Patient selectPatient = tv_patient.getSelectionModel().getSelectedItem();
                int selectId = selectPatient.getPatientId();
                ModelPatient.getInstance().patientId = selectId;
                new LoadWindow().loadWindow("/UI/managePatient/editPatient/editPatient.fxml", "Edit Patient Info");
            }


        }

        @FXML
        void cancelPatient_PatientInfo(ActionEvent event) {

        }

        @FXML
        void addDoctor_DoctorInfo(ActionEvent event) {
            loadWindow("/UI/manageDoctor/addDoctor/addDoctor.fxml", "Add Doctor");

        }

        @FXML
        void deleteDoctor_DoctorInfo(ActionEvent event) {
        if(tv_doctor.getSelectionModel().getSelectedItem()!=null){
            Doctor selectDoctor = tv_doctor.getSelectionModel().getSelectedItem();
            int selectDoctorId = selectDoctor.getDoctorId();
            String sql = "DELETE FROM DOCTORS WHERE DOCTORID = ?";
            String[] params= {Integer.toString(selectDoctorId)};
            DBHelper.executeUpdate(sql, params);
        }
        }

        @FXML
        void refreshDoctor_DoctorInfo(ActionEvent event) {
            init_tb_doctorInfo();

        }

        @FXML
        void editDoctor_DoctorInfo(ActionEvent event) {
            if(tv_doctor.getSelectionModel().getSelectedItem()!=null) {
                Doctor selectDoctor = tv_doctor.getSelectionModel().getSelectedItem();
                int selectId = selectDoctor.getDoctorId();
                ModelDoctor.getInstance().doctorId = selectId;
                new LoadWindow().loadWindow("/UI/manageDoctor/editDoctor/editDotor.fxml", "Edit Doctor Info");
            }

        }

        @FXML
        void cancelDoctor_DoctorInfo(ActionEvent event) {

        }

        @FXML
        void makeAppointment(ActionEvent event) {
            loadWindow("/UI/manageAppointments/makeAppointment/makeAppointment.fxml", "Make Appointment- Select a docotr");

        }

        @FXML
        void addDoctor(ActionEvent event) {
            loadWindow("/UI/manageDoctor/addDoctor/addDoctor.fxml", "Add Doctor");

        }

        @FXML
        void addPatients(ActionEvent event) {

            loadWindow("/UI/managePatient/addPatient/addPatient.fxml", "Add Patient");

        }

        @FXML
        void viewAppointment(ActionEvent event) {
            loadWindow("/UI/manageAppointments/viewAppointments/viewAppointments.fxml", "Doctor Info Management");

        }

        @FXML
        void makeSchedule(ActionEvent event) {
            loadWindow("/UI/makeStaffScheule/mainschedule/mainSchedule.fxml", "Make Schedule ");

        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init_tb_doctorInfo();
        init_tb_patientInfo();
        initFilter();


    }

    private void init_tb_doctorInfo(){
            initDoctorCol();
            loadData_tv_doctor();
    }
    private void initDoctorCol(){
        idCol_Doctor.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        nameCol_Doctor.setCellValueFactory(new PropertyValueFactory<>("name"));
        homephoneCol_Doctor.setCellValueFactory(new PropertyValueFactory<>("homePhone"));
        workphoneCol_Doctor.setCellValueFactory(new PropertyValueFactory<>("workPhone"));
        emergyphoneCol_Doctor.setCellValueFactory(new PropertyValueFactory<>("emergPhone"));
        emailCol_Doctor.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol_Doctor.setCellValueFactory(new PropertyValueFactory<>("address"));

    }
    private void loadData_tv_doctor(){
        ResultSet rs ;
        String sql = "SELECT * FROM DOCTORS";
        try {
            rs = DBHelper.executeQuery(sql, null);
            while(rs.next()){
                int id = rs.getInt("doctorid");
                String name = rs.getString("name");
                String homephone = rs.getString("homephone");
                String workphone = rs.getString("workphone");
                String emergyphone = rs.getString("emergphone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                listDoctor.add(new Doctor(id, name, homephone, workphone, emergyphone, email, address));
            }


        }catch (SQLException e){
            Logger.getLogger(MainController1.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            DBHelper.close(DBHelper.getRs(), DBHelper.getPs(), DBHelper.getCon());
        }
        tv_doctor.getItems().addAll(listDoctor);
        // you can only selet one record each time
        tv_doctor.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void init_tb_patientInfo(){
        initPatientCol();
        loadData_tv_patient();
        initFilter();
    }
    private void initPatientCol(){
        idCol_Patient.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        titleCol_Patient.setCellValueFactory(new PropertyValueFactory<>("title"));
        nameCol_Patient.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateOfBirthCol_Patient.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        phoneCol_Patient.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emergPhoneCol_Patient.setCellValueFactory(new PropertyValueFactory<>("emergyPhone"));
        emailCol_Patient.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol_Patient.setCellValueFactory(new PropertyValueFactory<>("address"));
        mediHistoryCol_Patient.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));



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
                String dateOfbirth = rs.getString("dateofbirth");
                String phone = rs.getString("phone");
                String emergyphone = rs.getString("emergphone");
                String email = rs.getString("email");
                String address = rs.getString("address");

                String medicalHistory = rs.getString("medicalhistory");

                listPatient.add(new Patient(id, title,name,dateOfbirth, phone, emergyphone, email, address,  medicalHistory));
            }


        }catch (SQLException e){
            Logger.getLogger(MainController1.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            DBHelper.close(DBHelper.getRs(), DBHelper.getPs(), DBHelper.getCon());
        }
        tv_patient.getItems().addAll(listPatient);
        // you can only selet one record each time
        tv_patient.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    private void initFilter(){
        FilteredList<Patient> filteredData = new FilteredList<>(listPatient, e->true);
       tf_searchPatient.setOnKeyReleased(e ->{
           tf_searchPatient.textProperty().addListener(new ChangeListener<String>() {
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




     private void loadWindow(String loc, String title){
         try {
             Parent parent = FXMLLoader.load(getClass().getResource(loc));
             Stage stage = new Stage(StageStyle.DECORATED);
             stage.setTitle(title);
             stage.setScene(new Scene(parent));
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
         }


     }

}
