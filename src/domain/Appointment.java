package domain;

import UI.UIhelper.AlertDialog;
import UI.UIhelper.LoadWindow;
import UI.model.ModelPatient;
import UI.model.ModelSchedule;
import database.DBHelper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {
    private SimpleIntegerProperty apptId;
    private SimpleIntegerProperty doctorId;
    private SimpleStringProperty doctorName;
    private SimpleIntegerProperty patientId;
    private SimpleStringProperty patientName;
    private SimpleStringProperty date;
    private SimpleIntegerProperty slot;
    private SimpleStringProperty startTime;


    private Button btnPatientDetail;
    private Button btnCancel;

    public Appointment(int apptId, int doctorId, int patientId,Date date, int slot){
        this.apptId = new SimpleIntegerProperty(apptId);
        this.doctorId = new SimpleIntegerProperty(doctorId);
        this.patientId = new SimpleIntegerProperty(patientId);
        this.date = new SimpleStringProperty(new SimpleDateFormat("yyyy-MM-dd").format(date));
        this.slot = new SimpleIntegerProperty(slot);
    }


    public Appointment(int apptId, Date date, int doctorId, String doctorName, int patientId, String patientName,int slot,String startTime){
        this.apptId = new SimpleIntegerProperty(apptId);
        this.doctorId = new SimpleIntegerProperty(doctorId);
        this.doctorName = new SimpleStringProperty(doctorName);

        this.patientId = new SimpleIntegerProperty(patientId);
        this.patientName = new SimpleStringProperty(patientName);
        this.date = new SimpleStringProperty(new SimpleDateFormat("yyyy-MM-dd").format(date));
        this.slot = new SimpleIntegerProperty(slot);
        this.startTime = new SimpleStringProperty(startTime);
        this.btnPatientDetail = new Button("Patient Detail");
        btnPatientDetail.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ModelPatient.getInstance().patientId = 1;
                new LoadWindow().loadWindow("/UI/managePatient/viewPatientDetail/viewPatientDetail.fxml", "View patient datails");

            }
        });
        this.btnCancel = new Button("Cancel");
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent event) {

                AlertDialog.info("Warning!", "Make sure you have contacted the patient!");
                deleteAppointment(apptId);
                updateSchedule(doctorId, date,ModelSchedule.getInstance().getSlotInteger(startTime));
            }
        });
    }

    public int getApptId() {
        return apptId.get();
    }

    public int getDoctorId() {
        return this.doctorId.get();
    }

    public int getPatientId() {
        return patientId.get();
    }

    public String getDate() {
        return date.get();
    }


    public String getDoctorName() {
        return doctorName.get();
    }


    public String getPatientName() {
        return patientName.get();
    }

    public int getSlot() {return slot.get();
    }

    public String getStartTime() {
        return startTime.get();
    }


    public Button getBtnPatientDetail() {
        return btnPatientDetail;
    }

    public void setBtnPatientDetail(Button btnPatientDetail) {
        this.btnPatientDetail = btnPatientDetail;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(Button btnCancel) {
        this.btnCancel = btnCancel;
    }

    private void deleteAppointment(int apptId){
        String sql = "DELETE  FROM APPOINTMENTS WHERE apptid = ?" ;
        String[] params = {Integer.toString(apptId)};
        if(DBHelper.executeUpdate(sql, params)){
            AlertDialog.info(null, "You have deleted the appointment. ");
        }
    }
    private void updateSchedule(int doctorId, Date date, int slot){
       String sql = "UPDATE SCHEDULES SET _"+ slot+" = 0  WHERE DOCTORID = ? AND SCHDDATE = ?";
       String[] params = {Integer.toString(doctorId),new SimpleDateFormat("yyyy-MM-dd").format(date)};
       if(DBHelper.executeUpdate(sql, params)){
           System.out.println("Successfully update the table schedules");
       }

    }
}
