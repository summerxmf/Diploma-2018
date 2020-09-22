package UI.makeStaffScheule.addWorkTime;


import UI.UIhelper.AlertDialog;
import UI.model.ModelSchedule;
import database.DBHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddWorkTime implements Initializable {
    String[] allTimeSlots;

    private int schdStartTimeIndex ;
    private int schdEndTimeIndex;

    @FXML
    private Label lb_showDate;

    @FXML
    private Button btn_cancel1;

    @FXML
    private ToggleGroup whole_partially;

    @FXML
    private ComboBox<String> cb_endTime;

    @FXML
    private RadioButton rb_partially;

    @FXML
    private Label lb_showDoctor;

    @FXML
    private RadioButton rb_whole;

    @FXML
    private ComboBox<String> cb_startTime;

    @FXML
    private Button btn_save;

    @FXML
    void selectFullTime(ActionEvent event) {
        cb_startTime.setDisable(true);
        cb_endTime.setDisable(true);

    }

    @FXML
    void selectPartially(ActionEvent event) {

        cb_startTime.setDisable(false);
        cb_endTime.setDisable(false);

        initializeStartTimeComboBox(35);
        initializeEndTimeComboBox(0);

    }

    @FXML
    void save(ActionEvent event) {
        if(rb_whole.isSelected()) {
           schdStartTimeIndex = 0;
           schdEndTimeIndex = 35;
        }else {
            if(cb_startTime.getSelectionModel().getSelectedIndex()==-1 ||
            cb_endTime.getSelectionModel().getSelectedIndex()==-1) {
                AlertDialog.error("Error", "Please selct start time and end time! ");
            }else {
                schdStartTimeIndex = cb_startTime.getSelectionModel().getSelectedIndex();
                schdEndTimeIndex = cb_endTime.getSelectionModel().getSelectedIndex()-1;
            }
        }

            String sql = "INSERT INTO SCHEDULES VALUES " +
                    " (?, ? ,?, ?,?, ? ,?, ?,?, ? ,?, ?,?, ? ,?, ?,?, ? ,?, ?,?, ? ,?, ?,?, ? ,?, ?,?, ? ,?, ?," +
                    "?, ? ,?, ?, ?,?)";

             String []params = new String[38];

             params[0]= ModelSchedule.getInstance().schdYear+ "-"+
                    ModelSchedule.getInstance().schdMonth + "-" +
                   ModelSchedule.getInstance().schdDay;
            params[1] = Integer.toString(ModelSchedule.getInstance().schdDoctor.getDoctorId());

            for(int i = 0; i<36; i++){
                params[i+2] =Integer.toString(-1);
            }
            for(int j = schdStartTimeIndex; j<= schdEndTimeIndex; j++) {
                params[j + 2] = Integer.toString(0);
            }

            if(DBHelper.executeUpdate(sql, params)){
                AlertDialog.info("Schedule was added successfully",
                        ModelSchedule.getInstance().schdDoctor.getName() + ","+
                                ModelSchedule.getInstance().schdYear+ "-"+
                                ModelSchedule.getInstance().schdMonth + "-" +
                                ModelSchedule.getInstance().schdDay);

                ModelSchedule.getInstance().schdAdded =true;

                Stage stage = (Stage) btn_save.getScene().getWindow();
                stage.close();
                return;
            }
            else {
                AlertDialog.error(null, "Failed!" );
            }
    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) btn_cancel1.getScene().getWindow();
        stage.close();

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lb_showDoctor.setText("Doctor: " + ModelSchedule.getInstance().schdDoctor.getName());
        lb_showDate.setText("Date: " + ModelSchedule.getInstance().schdMonth+ "-"+
                ModelSchedule.getInstance().schdDay + "-" +
                ModelSchedule.getInstance().schdMonth);
    }

    private void initializeStartTimeComboBox(int indexEnd) {
        allTimeSlots = ModelSchedule.getInstance().allTimeSlots;

        // Load startTime Combobox
        cb_startTime.getItems().clear();
        for (int i = 0; i <= indexEnd; i++) {
            cb_startTime.getItems().add(allTimeSlots[i]);
        }

        // Make sure cb_end time will only show time slots after start time
        cb_startTime.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (cb_startTime.getSelectionModel().getSelectedIndex() != -1) {
                    schdStartTimeIndex = (int) newValue;
                    initializeEndTimeComboBox((int) newValue + 1);
                }
            }
        });
    }

    private void initializeEndTimeComboBox(int indexStart) {

        // Load startTime Combobox
        cb_endTime.getItems().clear();
        for (int i = indexStart; i <= 35; i++) {
            cb_endTime.getItems().add(allTimeSlots[i]);
        }
//        cb_endTime.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                if(cb_endTime.getSelectionModel().getSelectedIndex()!=-1){
//                    schdEndTimeIndex = (int)newValue;
//                    initializeStartTimeComboBox((int)newValue -1);
//                }
//            }
//        });
    }
}





