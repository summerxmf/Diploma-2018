package UI.manageAppointments.viewAppointments;

import UI.main.main1.MainController1;
import UI.model.ModelSchedule;
import database.DBHelper;
import domain.Appointment;
import domain.Doctor;
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
import javafx.util.Callback;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewAppointments implements Initializable {
    ObservableList<Appointment> listAppointments;
    FilteredList<Appointment> filteredData;
    private final String[] allTimeSlots = ModelSchedule.getInstance().allTimeSlots;

    ResultSet rs ;


    @FXML
    private DatePicker dp_date;

    @FXML
    private ComboBox<Doctor> cb_doctor;



    @FXML
    private TableView<Appointment> tv_appointment;
    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> dateCol;
    @FXML
    private TableColumn<?, ?> doctorCol;

    @FXML
    private TableColumn<?, ?> patientCol;

    @FXML
    private TableColumn<?, ?> startTimeCol;
    @FXML
    private TableColumn<?, ?> patientDetailCol;

    @FXML
    private TableColumn<?, ?> cancelCol;

    @FXML
    private Button btn_cancel;
    @FXML
    private Button btn_refresh;


    @FXML
    void clearFilterDoctor(ActionEvent event) {
        cb_doctor.getSelectionModel().clearSelection();

    }

    @FXML
    void clearFilterDate(ActionEvent event) {
        dp_date.setValue(null);


    }


    @FXML
    void refresh(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initilizeDoctorComboBox();
        innitialize_tv_appointment();
        initializeDateFilter();
        initializeDoctorFilter();

    }

    private void initilizeDoctorComboBox() {

        ObservableList<Doctor> observableList = FXCollections.observableArrayList();
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
                observableList.add(new Doctor(id, name, homephone, workphone, emergyphone, email, address));
            }


        }catch (SQLException e){
            Logger.getLogger(ViewAppointments.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            DBHelper.close(DBHelper.getRs(), DBHelper.getPs(), DBHelper.getCon());
        }

        cb_doctor.setItems(observableList);

        cb_doctor.setCellFactory(new Callback<ListView<Doctor>, ListCell<Doctor>>() {

            @Override
            public ListCell<Doctor> call(ListView<Doctor> arg0) {
                // TODO Auto-generated method stub
                return new ListCell<Doctor>(){
                    public void updateItem(Doctor item,boolean empty){
                        super.updateItem(item, empty);
                        if(item!=null&&!empty){
                            this.setText(item.getName());
                        }

                    }
                };
            }
        });
        cb_doctor.setButtonCell(new ListCell<Doctor>() {
            @Override
            protected void updateItem(Doctor t, boolean bln) {
                super.updateItem(t, bln);
                if (t!= null) {
                    setText(t.getName());
                } else {
                    setText(null);
                }
            }
        });


    }


    private void innitialize_tv_appointment(){
        initializeAppointmentCol();
        loadData_tv_appointment();


    }

    private void initializeAppointmentCol(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        doctorCol.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        patientCol.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        patientDetailCol.setCellValueFactory(new PropertyValueFactory<>("btnPatientDetail"));
        cancelCol.setCellValueFactory(new PropertyValueFactory<>("btnCancel"));

    }

    // display all of the appointments from the current day to 14 days hence
    private void loadData_tv_appointment(){
         listAppointments = FXCollections.observableArrayList();


        String sql = "SELECT A.apptid, A.date, A.doctorid, D.name, A.patientid,P.name, A.slot FROM appointments AS A" +
                " JOIN DOCTORS AS D ON A.doctorid = D.doctorid " +
                "jOIN patients AS P ON A.patientid =P.patientid " +
                "WHERE A.date >= ? AND A.date< ?";
        String[] params = {LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDate.now().plusDays(14).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))};
        try {
            rs = DBHelper.executeQuery(sql, params);

            while(rs.next()){
                int id = rs.getInt(1);
                Date date  = rs.getDate(2);
                int doctorId = rs.getInt(3);
                String doctorName = rs.getString(4);
                int patientId = rs.getInt(5);
                String patientName = rs.getString(6);
                int slot = rs.getInt(5);
                String startTime = ModelSchedule.getInstance().allTimeSlots[slot-1];

                // Initially add all data to listAppointment
                listAppointments.add(new Appointment(id, date,doctorId, doctorName, patientId, patientName, startTime));

            }


        }catch (SQLException e){
            Logger.getLogger(MainController1.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            DBHelper.close(DBHelper.getRs(), DBHelper.getPs(), DBHelper.getCon());
        }
        // load all appointments to tableView
        tv_appointment.getItems().addAll(listAppointments);




    }

    private void initializeDateFilter() {

        filteredData= new FilteredList<>(listAppointments);

        // bind predicate based on datepicker choices
        dp_date.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                filteredData.setPredicate((Predicate<? super Appointment>) appoinment -> {
                    if (newValue == null) {
                        return true;
                    }
                    if (appoinment.getDate().equals(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(newValue))) {
                        return true;
                    }
                    return false;
                });
            }
        });
        SortedList<Appointment> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tv_appointment.comparatorProperty());
        tv_appointment.setItems(sortedList);
    }

    private void initializeDoctorFilter(){

        filteredData= new FilteredList<>(listAppointments);

        cb_doctor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Doctor>() {
            @Override
            public void changed(ObservableValue<? extends Doctor> observable, Doctor oldValue, Doctor newValue) {
                filteredData.setPredicate((Predicate<? super Appointment>) appointment ->{
                    if(newValue ==null){
                        return true;
                    }
                    if(appointment.getDoctorId()== newValue.getDoctorId()){
                        return true;
                    }
                    return false;

                });
            }
        });
        SortedList<Appointment> sortedList = new SortedList<>(filteredData);
        sortedList.comparatorProperty().bind(tv_appointment.comparatorProperty());
        tv_appointment.setItems(sortedList);
    }

}
