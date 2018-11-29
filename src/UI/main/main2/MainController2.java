package UI.main.main2;

import UI.UIhelper.LoadWindow;
import UI.main.main1.MainController1;
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

public class MainController2 implements Initializable {
    ObservableList<Patient> listPatient = FXCollections.observableArrayList();

    @FXML
    private Tab tb_patientInfo;



    @FXML
    private Button btn_viewAppointment;



    @FXML
    private TextField tf_searchPatient;

    @FXML
    private Button btn_cancelPatient;



    @FXML
    private Button btn_refreshPatient;

    @FXML
    private TableView<Patient> tv_patient;

    @FXML
    private TableColumn<Patient, Date> dateOfBirthCol_Patient;

    @FXML
    private TableColumn<Patient, String> nameCol_Patient;

    @FXML
    private TableColumn<Patient, String> titleCol_Patient;

    @FXML
    private TableColumn<Patient, String> emailCol_Patient;

    @FXML
    private TableColumn<Patient, String> emergPhoneCol_Patient;

    @FXML
    private TableColumn<Patient, String> phoneCol_Patient;

    @FXML
    private TableColumn<Patient, String> addressCol_Patient;

    @FXML
    private TableColumn<Patient, Integer> idCol_Patient;


    @FXML
    private TableColumn<Patient, String> mediHistoryCol_Patient;

    @FXML
    void addPatient_PatientInfo(ActionEvent event) {
        new LoadWindow().loadWindow("/UI/managePatient/addPatient/addPatient.fxml", "Add Patient");

    }

    @FXML
    void refreshPatient_PatientInfo(ActionEvent event) {

    }


    @FXML
    void cancelPatient_PatientInfo(ActionEvent event) {

    }

    @FXML
    void viewAppointment(ActionEvent event) {
        new LoadWindow().loadWindow("/UI/manageAppointments/viewAppointments/viewAppointments.fxml", "Doctor Info Management");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init_tb_patientInfo();
        initFilter();
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

}
