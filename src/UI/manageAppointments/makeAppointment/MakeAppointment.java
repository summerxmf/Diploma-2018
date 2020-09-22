package UI.manageAppointments.makeAppointment;


import UI.UIhelper.LoadWindow;
import UI.model.ModelAppointment;
import database.DBHelper;
import domain.Doctor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MakeAppointment implements Initializable {
    private Doctor selectedDoctor =null;
    private LocalDate selectedDate;


    @FXML
    private Button btn_1615;


    @FXML
    private Button btn_1715;

    @FXML
    private Button btn_1215;

    @FXML
    private Button btn_today;

    @FXML
    private Button btn_1315;

    @FXML
    private Button btn_1415;

    @FXML
    private Button btn_1515;

    @FXML
    private Button btn_1530;

    @FXML
    private Button btn_1630;

    @FXML
    private Button btn_1015;

    @FXML
    private Button btn_1730;

    @FXML
    private Button btn_1115;

    @FXML
    private Button btn_tomorrow;

    @FXML
    private Button btn_1130;

    @FXML
    private Button btn_1230;

    @FXML
    private Button btn_1330;

    @FXML
    private Button btn_1430;

    @FXML
    private DatePicker dp_preferedDate;

    @FXML
    private Button btn_900;

    @FXML
    private Button btn_945;

    @FXML
    private Button btn_1600;

    @FXML
    private Button btn_1545;

    @FXML
    private Button btn_1700;

    @FXML
    private Button btn_1645;

    @FXML
    private ListView<Doctor> lv_doctors;

    @FXML
    private Button btn_1745;

    @FXML
    private Button btn_1145;

    @FXML
    private Button btn_1200;

    @FXML
    private Button btn_1300;

    @FXML
    private Button btn_1245;

    @FXML
    private Button btn_1400;

    @FXML
    private Button btn_1345;

    @FXML
    private Button btn_1500;

    @FXML
    private Button btn_1445;

    @FXML
    private Button btn_1000;

    @FXML
    private Button btn_1045;

    @FXML
    private Button btn_1100;

    @FXML
    private Button btn_1030;

    @FXML
    private Button btn_915;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_refresh;

    @FXML
    private Button btn_930;

    @FXML
    void select_today(ActionEvent event) {
        dp_preferedDate.setValue(LocalDate.now());
        selectedDate=LocalDate.now();
        if(selectedDoctor!=null){
            RepaintSlotButtons();
        }

    }

    @FXML
    void select_tomorrow(ActionEvent event) {
        dp_preferedDate.setValue(LocalDate.now().plusDays(1));
        selectedDate =LocalDate.now().plusDays(1);
        if(selectedDoctor!=null){
            RepaintSlotButtons();
        }
    }
    @FXML
    void select_900(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 1;

        buttonClick();

    }

    @FXML
    void select_915(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 2;

        buttonClick();

    }

    @FXML
    void select_930(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 3;
        buttonClick();
    }

    @FXML
    void select_945(ActionEvent event) {

        ModelAppointment.getInstance().apptSlot = 4;
        buttonClick();

    }

    @FXML
    void select_1000(ActionEvent event) {

        ModelAppointment.getInstance().apptSlot = 5;
        buttonClick();
    }

    @FXML
    void select_1015(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 6;
        buttonClick();

    }

    @FXML
    void select_1030(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 7;
        buttonClick();

    }

    @FXML
    void select_1045(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 8;
        buttonClick();

    }

    @FXML
    void select_1100(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 9;
        buttonClick();

    }

    @FXML
    void select_1115(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 10;
        buttonClick();

    }

    @FXML
    void select_1130(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 11;
        buttonClick();

    }

    @FXML
    void select_1145(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 12;
        buttonClick();

    }

    @FXML
    void select_1200(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 13;
        buttonClick();

    }
    @FXML
    void select_1215(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 14;
        buttonClick();

    }

    @FXML
    void select_1230(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 15;
        buttonClick();
    }

    @FXML
    void select_1245(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 16;
        buttonClick();

    }

    @FXML
    void select_1300(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 17;
        buttonClick();

    }
    @FXML
    void select_1315(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 18;
        buttonClick();
    }

    @FXML
    void select_1330(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 19;
        buttonClick();

    }

    @FXML
    void select_1345(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 20;
        buttonClick();

    }

    @FXML
    void select_1400(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 21;
        buttonClick();

    }

    @FXML
    void select_1415(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 22;
        buttonClick();

    }

    @FXML
    void select_1430(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 23;
        buttonClick();

    }

    @FXML
    void select_1445(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 24;
        buttonClick();

    }


    @FXML
    void select_1500(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 25;
        buttonClick();

    }
    @FXML
    void select_1515(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 26;
        buttonClick();

    }

    @FXML
    void select_1530(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 27;
        buttonClick();

    }

    @FXML
    void select_1545(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 28;
        buttonClick();

    }

    @FXML
    void select_1600(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 29;
        buttonClick();

    }

    @FXML
    void select_1615(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 30;
        buttonClick();

    }

    @FXML
    void select_1630(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 31;
        buttonClick();

    }

    @FXML
    void select_1645(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 32;
        buttonClick();

    }


    @FXML
    void select_1700(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 33;
        buttonClick();

    }


    @FXML
    void select_1715(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 34;
        buttonClick();

    }

    @FXML
    void select_1730(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 35;
        buttonClick();

    }

    @FXML
    void select_1745(ActionEvent event) {
        ModelAppointment.getInstance().apptSlot = 36;
        buttonClick();

    }
    private void buttonClick(){
        ModelAppointment.getInstance().apptDoctor =selectedDoctor;
        ModelAppointment.getInstance().apptDate =selectedDate;
        new LoadWindow().loadWindow("/UI/manageAppointments/makeAppointment/selectPatient.fxml", "Select Patient");
    }



    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage)btn_cancel.getScene().getWindow();
        stage.close();

    }

    @FXML
    void RefreshlSlots(ActionEvent event) {
        RepaintSlotButtons();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDateTimePicker();
        initializeDoctorListView();
    }

    private void initDateTimePicker(){
        dp_preferedDate.setValue(LocalDate.now());
        selectedDate = LocalDate.now();
        ModelAppointment.getInstance().apptDate = dp_preferedDate.getValue();//.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dp_preferedDate.setDayCellFactory(picker ->new DateCell(){
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                //setDisable(empty|| date.getDayOfWeek()== DayOfWeek.SATURDAY || date.getDayOfWeek()==DayOfWeek.SUNDAY);
                setDisable(empty||date.isBefore(LocalDate.now())||date.isAfter(LocalDate.now().plusDays(14)));
            }
        });


        dp_preferedDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
               if(newValue!=null){
                   selectedDate = newValue;
                   if(selectedDoctor!=null){
                       RepaintSlotButtons();
                   }
               }

            }
        });
    }

    private void initializeDoctorListView() {

        ObservableList<Doctor> observableList = FXCollections.observableArrayList();
        ResultSet rs;
        String sql = "SELECT * FROM DOCTORS";
        try {
            rs = DBHelper.executeQuery(sql, null);
            while (rs.next()) {
                int id = rs.getInt("doctorId");
                String name = rs.getString("name");
                String homephone = rs.getString("homePhone");
                String workphone = rs.getString("workPhone");
                String emergyphone = rs.getString("emergPhone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                observableList.add(new Doctor(id, name, homephone, workphone, emergyphone, email, address));
            }
        } catch (SQLException e) {
            Logger.getLogger(MakeAppointment.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBHelper.close(DBHelper.getRs(), DBHelper.getPs(), DBHelper.getCon());
        }

        lv_doctors.setItems(observableList);
        lv_doctors.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        lv_doctors.setCellFactory(new Callback<ListView<Doctor>, ListCell<Doctor>>() {
            @Override
            public ListCell<Doctor> call(ListView<Doctor> arg0) {
                // TODO Auto-generated method stub
                return new ListCell<Doctor>() {
                    public void updateItem(Doctor item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            this.setText(item.getName());
                        }
                    }
                };
            }
        });

        lv_doctors.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Doctor>() {
            @Override
            public void changed(ObservableValue<? extends Doctor> observable, Doctor oldValue, Doctor newValue) {
                if (newValue != null) {

                    selectedDoctor = newValue;
                    RepaintSlotButtons();

                }
            }
        });
    }
    public void RepaintSlotButtons(){
        btn_900.setVisible(false);
        btn_915.setVisible(false);
        btn_930.setVisible(false);
        btn_945.setVisible(false);
        btn_1000.setVisible(false);
        btn_1015.setVisible(false);
        btn_1030.setVisible(false);
        btn_1045.setVisible(false);
        btn_1100.setVisible(false);
        btn_1115.setVisible(false);
        btn_1130.setVisible(false);
        btn_1145.setVisible(false);
        btn_1200.setVisible(false);
        btn_1215.setVisible(false);
        btn_1230.setVisible(false);
        btn_1245.setVisible(false);
        btn_1300.setVisible(false);
        btn_1315.setVisible(false);
        btn_1330.setVisible(false);
        btn_1345.setVisible(false);
        btn_1400.setVisible(false);
        btn_1415.setVisible(false);
        btn_1430.setVisible(false);
        btn_1445.setVisible(false);
        btn_1500.setVisible(false);
        btn_1515.setVisible(false);
        btn_1530.setVisible(false);
        btn_1545.setVisible(false);
        btn_1600.setVisible(false);
        btn_1615.setVisible(false);
        btn_1630.setVisible(false);
        btn_1645.setVisible(false);
        btn_1700.setVisible(false);
        btn_1715.setVisible(false);
        btn_1730.setVisible(false);
        btn_1745.setVisible(false);

        String sql = "SELECT * FROM SCHEDULES WHERE DOCTORID = ? AND SCHDDATE = ?";
        int doctorId = selectedDoctor.getDoctorId();
        LocalDate apptDate = selectedDate;
        String[] params = {Integer.toString(doctorId),
                apptDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))};
        ResultSet rs = DBHelper.executeQuery(sql, params);
        try{
            while (rs.next()){
                if(rs.getInt("_1")==0){ btn_900.setVisible(true); System.out.println("true");}

                if(rs.getInt("_2")==0) btn_915.setVisible(true);
                if(rs.getInt("_3")==0) btn_930.setVisible(true);
                if(rs.getInt("_4")==0) btn_945.setVisible(true);
                if(rs.getInt("_5")==0) btn_1000.setVisible(true);
                if(rs.getInt("_6")==0) btn_1015.setVisible(true);
                if(rs.getInt("_7")==0) btn_1030.setVisible(true);
                if(rs.getInt("_8")==0) btn_1045.setVisible(true);
                if(rs.getInt("_9")==0) btn_1100.setVisible(true);
                if(rs.getInt("_10")==0) btn_1115.setVisible(true);
                if(rs.getInt("_11")==0) btn_1130.setVisible(true);
                if(rs.getInt("_12")==0) btn_1145.setVisible(true);
                if(rs.getInt("_13")==0) btn_1200.setVisible(true);
                if(rs.getInt("_14")==0) btn_1215.setVisible(true);
                if(rs.getInt("_15")==0) btn_1230.setVisible(true);
                if(rs.getInt("_16")==0) btn_1245.setVisible(true);
                if(rs.getInt("_17")==0) btn_1300.setVisible(true);
                if(rs.getInt("_18")==0) btn_1315.setVisible(true);
                if(rs.getInt("_19")==0) btn_1330.setVisible(true);
                if(rs.getInt("_20")==0) btn_1345.setVisible(true);
                if(rs.getInt("_21")==0) btn_1400.setVisible(true);
                if(rs.getInt("_22")==0) btn_1415.setVisible(true);
                if(rs.getInt("_23")==0) btn_1430.setVisible(true);
                if(rs.getInt("_24")==0) btn_1445.setVisible(true);
                if(rs.getInt("_25")==0) btn_1500.setVisible(true);
                if(rs.getInt("_26")==0) btn_1515.setVisible(true);
                if(rs.getInt("_27")==0) btn_1530.setVisible(true);
                if(rs.getInt("_28")==0) btn_1545.setVisible(true);
                if(rs.getInt("_29")==0) btn_1600.setVisible(true);
                if(rs.getInt("_30")==0) btn_1615.setVisible(true);
                if(rs.getInt("_31")==0) btn_1630.setVisible(true);
                if(rs.getInt("_32")==0) btn_1645.setVisible(true);
                if(rs.getInt("_33")==0) btn_1700.setVisible(true);
                if(rs.getInt("_34")==0) btn_1715.setVisible(true);
                if(rs.getInt("_35")==0) btn_1730.setVisible(true);
                if(rs.getInt("_36")==0) btn_1745.setVisible(true);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DBHelper.close(DBHelper.getRs(), DBHelper.getPs(), DBHelper.getCon());
        }
    }

}
