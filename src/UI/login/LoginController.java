package UI.login;

import UI.UIhelper.LoadWindow;
import UI.manageAppointments.makeAppointment.MakeAppointment;
import database.DBHelper;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController  implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        square.setTranslateX(0);
        square.setTranslateY(50);
        aniamtion(square);

    }
    private  void aniamtion(Shape shape) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), square);
        fadeTransition.setFromValue(1.0f);
        fadeTransition.setToValue(0.3f);
        fadeTransition.setCycleCount(2);

        fadeTransition.setAutoReverse(true);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), square);
        translateTransition.setFromX(0);
        translateTransition.setToX(390);
        translateTransition.setFromY(50);
        translateTransition.setToY(0);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);

        RotateTransition rotateTransition =
                new RotateTransition(Duration.millis(3000), square);
        rotateTransition.setByAngle(180f);
        rotateTransition.setCycleCount(4);
        rotateTransition.setAutoReverse(true);

        //ScaleTransition
        ScaleTransition scaleTransition =
                new ScaleTransition(Duration.millis(2000), square);
        scaleTransition.setToX(2f);
        scaleTransition.setToY(2f);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);


        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, rotateTransition,
                translateTransition, scaleTransition);
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();

    }

    @FXML
    private Rectangle square;

    @FXML
    private Button btn_signIn;

    @FXML
    private TextField tf_userName;

    @FXML
    private PasswordField pf_pwd;

    @FXML
    private Text txt_wrong;



    @FXML
    void signIn(ActionEvent event) {
        if(tf_userName.getText().trim().isEmpty()){

            txt_wrong.setText("Please input username!");
            return;
        }
        if(pf_pwd.getText().trim().isEmpty()){

            txt_wrong.setText("Please input password!");
            return;
        }

        ResultSet rs;
        String pwd = null;
        int flag =0;
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        String[] params = {tf_userName.getText().trim()};
        try {
            rs = DBHelper.executeQuery(sql, params);
            while (rs.next()) {
                pwd = rs.getString("password");
                flag = rs.getInt("flag");
            }
        } catch (SQLException e) {
            Logger.getLogger(MakeAppointment.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            DBHelper.close(DBHelper.getRs(), DBHelper.getPs(), DBHelper.getCon());
        }
        if(pwd == null){

            txt_wrong.setText("Incorrect username or password!");


            return;
        }
        else if(!pf_pwd.getText().trim().equals(pwd)){
            txt_wrong.setText("Incorrect username or password!");

            return;
        }
        else{
            Stage stage = (Stage)btn_signIn.getScene().getWindow();
            stage.close();
            if (flag == 1){
                new LoadWindow().loadWindow("/UI/main/main1/main1.fxml", "Medical Center Management System");
            }

            else if(flag == 2){
                new LoadWindow().loadWindow("/UI/main/main2/main2.fxml", "Medical Center Management System");

            }

        }



    }

}
