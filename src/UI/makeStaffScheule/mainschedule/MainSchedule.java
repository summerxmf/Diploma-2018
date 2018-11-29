package UI.makeStaffScheule.mainschedule;


import UI.UIhelper.AlertDialog;
import UI.UIhelper.LoadWindow;
import UI.main.main1.MainController1;
import UI.manageAppointments.makeAppointment.MakeAppointment;
import UI.model.ModelSchedule;
import database.DBHelper;
import domain.Doctor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainSchedule implements Initializable {
    String []monthsArr;


    // Controllers
    private MakeAppointment mainController ;

    //Set main controller
    public void setMainController(MakeAppointment mainController) {
        this.mainController = mainController ;
    }


    int selectedDay;


    @FXML
    private HBox weekdayHeader;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label lb_month;

    @FXML
    private Button btn_cancel;

    @FXML
    private ComboBox<String> cb_selectedYear;



    @FXML
    private ListView<Doctor> lv_selectedDoctor;

    @FXML
    private ComboBox<String> cb_selectedMonth;

    @FXML
    private Label lb_year;

    @FXML
    private Label lb_doctorName;

    @FXML
    private Button btn_refresh;

    @FXML
    private Button btn_exportExcel;

    @FXML
    private Button btn_exportPDF;

    @FXML
    private Button btn_tableView;




    @FXML
    void exportExcel(ActionEvent event) {

    }

    @FXML
    void exportPDF(ActionEvent event) {

    }

    @FXML
    void goToTableView(ActionEvent event) {

    }


    @FXML
    void refreshCalendar(ActionEvent event) {
        repaintLabels();

    }

    @FXML
    void cancel(ActionEvent event) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeYearComboBox();
        initializeMonthListView();

        initializeDoctorListView();

        // Make empty calendar
        calendarGenerate();


    }

    private void initializeDoctorListView() {

        ObservableList<Doctor> observableList = FXCollections.observableArrayList();
        ResultSet rs;
        String sql = "SELECT * FROM DOCTORS";
        try {
            rs = DBHelper.executeQuery(sql, null);
            while (rs.next()) {
                int id = rs.getInt("doctorid");
                String name = rs.getString("name");
                String homephone = rs.getString("homephone");
                String workphone = rs.getString("workphone");
                String emergyphone = rs.getString("emergphone");
                String email = rs.getString("email");
                String address = rs.getString("address");
                observableList.add(new Doctor(id, name, homephone, workphone, emergyphone, email, address));
            }


        } catch (SQLException e) {
            Logger.getLogger(MainSchedule.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBHelper.close(DBHelper.getRs(), DBHelper.getPs(), DBHelper.getCon());
        }

        lv_selectedDoctor.setItems(observableList);
        lv_selectedDoctor.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ModelSchedule.getInstance().schdDoctor=null;

        lv_selectedDoctor.setCellFactory(new Callback<ListView<Doctor>, ListCell<Doctor>>() {

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

        lv_selectedDoctor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Doctor>() {
            @Override
            public void changed(ObservableValue<? extends Doctor> observable, Doctor oldValue, Doctor newValue) {
                if (newValue != null) {
                    lb_doctorName.setText("Doctor: " + newValue.getName());
                   ModelSchedule.getInstance().schdDoctor = newValue;

                    // Update view
                    btn_refresh.setDisable(false);
                    loadCalendarLabels();

                   repaintLabels();

                }
            }
        });

    }
    private void initializeYearComboBox() {
        // Load year selection
        cb_selectedYear.getItems().clear(); // Note: Invokes its change listener
        cb_selectedYear.getItems().add(Integer.toString(ModelSchedule.getInstance().calendar_start));
        cb_selectedYear.getItems().add(Integer.toString(ModelSchedule.getInstance().calendar_end));

        // Select the first YEAR as default
        cb_selectedYear.getSelectionModel().selectFirst();


        // Update the selected year
       ModelSchedule.getInstance().schdYear= Integer.parseInt(cb_selectedYear.getValue());

        //Update the label lb_year
        lb_year.setText("Year: "+cb_selectedYear.getValue());

        // Enable year selection box
        cb_selectedYear.setVisible(true);
        // Add event listener to each year item, allowing user to change years
        cb_selectedYear.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (newValue != null) {
                    lb_year.setText("Year: " + newValue);

                    // Update the VIEWING YEAR
                   ModelSchedule.getInstance().schdYear = Integer.parseInt(newValue);


                    if(ModelSchedule.getInstance().schdDoctor==null){
                        loadCalendarLabels();
                    }else {
                        loadCalendarLabels();
                        repaintLabels();
                    }

                }
            }
        });
    }

     private void initializeMonthListView() {
        monthsArr= ModelSchedule.getInstance().monthsArr;

         // Load month selection
         cb_selectedMonth.getItems().clear();
         cb_selectedMonth.getItems().addAll(monthsArr);

         // Select the first MONTH as default

         cb_selectedMonth.getSelectionModel().select(Calendar.getInstance().get(Calendar.MONTH));


         lb_month.setText("Month: "+cb_selectedMonth.getValue());


         // Update the selectedMonth
         ModelSchedule.getInstance().schdMonth = cb_selectedMonth.getSelectionModel().getSelectedIndex()+1;
         System.out.println(ModelSchedule.getInstance().schdMonth);



         // Add event listener to each month list item, allowing user to change months
         cb_selectedMonth.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
             @Override
             public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                 int index = (int) newValue;
                 if(index !=-1){
                     // Show selected/current month above calendar
                     lb_month.setText("Month: " + monthsArr[index]);
                     // update the selected month
                     ModelSchedule.getInstance().schdMonth= index+1;
                     if(ModelSchedule.getInstance().schdDoctor==null){
                         loadCalendarLabels();
                     }else {
                         loadCalendarLabels();
                         repaintLabels();
                     }
                 }
             }
         });
//         new ChangeListener<String>() {
//
//             @Override
//             public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//
//                 // Necessary to check for null because change listener will
//                 // also detect clear() calls
//                 if (newValue != null) {
//
//                     // Show selected/current month above calendar
//                     lb_month.setText("Month: " + newValue);
//
//                     // Update the selected month
//                     ModelSchedule.getInstance().schdMonth = cb_selectedMonth.getSelectionModel().getSelectedIndex();
//
//                     System.out.println(ModelSchedule.getInstance().schdMonth);
//
//                     if(lv_selectedDoctor.getSelectionModel().getSelectedItem()==null){
//                         loadCalendarLabels();
//                     }else {
//                         repaintLabels();
//
//                     }
//                 }
//
//             }
//         });
     }

    public void calendarGenerate(){
        initializeCalendarWeekdayHeader();
        initializeCalendarGrid();
        loadCalendarLabels();


    }

    public void initializeCalendarWeekdayHeader(){

        // 7 days in a week
        int weekdays = 7;

        // Weekday names
        String[] weekAbbr = {"Sun","Mon","Tue", "Wed", "Thu", "Fri", "Sat"};

        for (int i = 0; i < weekdays; i++){

            // Make new pane and label of weekday
            StackPane pane = new StackPane();
//            pane.getStyleClass().add("weekday-header");

            // Make panes take up equal space
            HBox.setHgrow(pane, Priority.ALWAYS);
            pane.setMaxWidth(Double.MAX_VALUE);

            // Note: After adding a label to this, it tries to resize itself..
            // So I'm setting a minimum width.
            pane.setPrefWidth(weekdayHeader.getPrefWidth()/7);

            // Add it to the header
            weekdayHeader.getChildren().add(pane);

            // Add weekday name
            pane.getChildren().add(new Label(weekAbbr[i]));
        }
    }

    private void initializeCalendarGrid(){

        // Go through each calendar grid location, or each "day" (7x6)
        int rows = 5;
        int cols = 7;
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){

                // Add VBox and style it
                VBox vPane = new VBox();
                vPane.setPrefWidth(weekdayHeader.getPrefWidth()/cols);
                vPane.setAlignment(Pos.CENTER);


                vPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    addEvent(vPane);
                });


                GridPane.setVgrow(vPane, Priority.ALWAYS);

                // Add it to the grid
                calendarGrid.add(vPane, j, i);
            }
        }

        // Set up Row Constraints
        for (int i = 0; i < 7; i++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(scrollPane.getHeight()/7);
            calendarGrid.getRowConstraints().add(row);
        }
    }
    // Events
    private void addEvent(VBox day) {
        if(lv_selectedDoctor.getSelectionModel().getSelectedItem() == null){
            AlertDialog.error(null, "Please select a doctor!");
            return;
        }

        // Purpose - Add event to a day

        // Do not add events to days without labels
        if(!day.getChildren().isEmpty()) {

            // Get the day number
            Label lbl = (Label) day.getChildren().get(0);
            //System.out.println(lbl.getText());

            // Store event day and month in data singleton
            ModelSchedule.getInstance().schdDay = Integer.parseInt(lbl.getText());

            // Open add event view
            new LoadWindow().loadWindow("/UI/makeStaffScheule/addWorkTime/addWorkTime.fxml" , "Add Schedule");
        }
    }

    public void loadCalendarLabels(){

        // Note: Java's Gregorian Calendar class gives us the right
        // "first day of the month" for a given calendar & month
        // This accounts for Leap Year
        int year = ModelSchedule.getInstance().schdYear;
        int month = ModelSchedule.getInstance().schdMonth-1;


        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int firstDay = gc.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);

        // We are "offsetting" our start depending on what the
        // first day of the month is.
        // For example: Sunday start, Monday start, Wednesday start.. etc
        int offset = firstDay;
        int gridCount = 1;
        int lblCount = 1;

        // Go through calendar grid
        for(Node node : calendarGrid.getChildren()){

            VBox day = (VBox) node;

            day.getChildren().clear();
            day.setStyle("-fx-backgroud-color: white");
            day.setStyle("-fx-font: 14px \"System\" ");

            // Start placing labels on the first day for the month
            if (gridCount < offset) {
                gridCount++;
                // Darken color of the offset days
                day.setStyle("-fx-background-color: #E9F2F5");
            } else {

                // Don't place a label if we've reached maximum label for the month
                if (lblCount > daysInMonth) {
                    // Instead, darken day color
                    day.setStyle("-fx-background-color: #E9F2F5");
                } else {

                    // Make a new day label
                    Label lbl = new Label(Integer.toString(lblCount));

                    lbl.setPadding(new Insets(5));

                    day.getChildren().add(lbl);

                    selectedDay = lblCount;

                    CheckBox checkBox = new CheckBox();
                    checkBox.setDisable(true);
                    checkBox.setVisible(false);
                    day.getChildren().add(checkBox);
                }

                lblCount++;
            }
        }
    }

       public void repaintLabels() {
           int year = ModelSchedule.getInstance().schdYear;
           int month = ModelSchedule.getInstance().schdMonth-1;


           GregorianCalendar gc = new GregorianCalendar(year, month, 1);
           int firstDay = gc.get(Calendar.DAY_OF_WEEK);
           int daysInMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);

           // We are "offsetting" our start depending on what the
           // first day of the month is.
           // For example: Sunday start, Monday start, Wednesday start.. etc
           int offset = firstDay;
           int gridCount = 1;
           int lblCount = 1;


           // Go through calendar grid
           for (Node node : calendarGrid.getChildren()) {

               VBox day = (VBox) node;

               // Start placing labels on the first day for the month
               if (gridCount < offset) {
                   gridCount++;
                   // Darken color of the offset days
                   day.setStyle("-fx-background-color: #E9F2F5");
               } else {

                   // Don't place a label if we've reached maximum label for the month
                   if (lblCount > daysInMonth) {
                       // Instead, darken day color
                       day.setStyle("-fx-background-color: #E9F2F5");
                   } else {

                       if (HasMadeSchedule(lblCount)) {

                           CheckBox checkBox = (CheckBox) day.getChildren().get(1);
                           checkBox.setVisible(true);
                           checkBox.setSelected(true);
                       }
                   }
                   lblCount++;
               }
           }
       }

    private  boolean HasMadeSchedule(int schdDay){
        int year = ModelSchedule.getInstance().schdYear;
        int month = ModelSchedule.getInstance().schdMonth;
        String schdDate = Integer.toString(year)+ "-"+ Integer.toString(month) + "-" + schdDay;

        int schdDoctorId =ModelSchedule.getInstance().schdDoctor.getDoctorId();


        String sql = "SELECT * FROM SCHEDULES WHERE DOCTORID= ? AND SCHDDATE = ?";
        String []params = {Integer.toString(schdDoctorId), schdDate };

        try {
            ResultSet rs = DBHelper.executeQuery(sql, params);
            if (rs.next()){
                return true;
            }


        }catch (Exception e){
            Logger.getLogger(MainController1.class.getName()).log(Level.SEVERE, null, e);


        }finally {
            DBHelper.close(DBHelper.getRs(),DBHelper.getPs(), DBHelper.getCon());
        }
        return false;

    }
}







//
//    private void showWorkTime(int dayOfDate, String startTime, String endTime){
//        for(Node node: calendarGrid.getChildren()){
//            VBox day = (VBox)node;
//            if(!day.getChildren().isEmpty()){
//                day.getChildren().clear();
//
//                Label lbWorkTime = new Label();
//                lbWorkTime.setText(Integer.toString(dayOfDate)+"\n"+
//                        startTime +" - " +endTime
//                );
//                day.getChildren().add(lbWorkTime);
//            }
//        }
//
//    }




