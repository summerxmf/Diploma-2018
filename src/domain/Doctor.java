package domain;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.Period;

public class Doctor implements Person {
    private SimpleIntegerProperty doctorId;
    private SimpleStringProperty name;
    private SimpleStringProperty homePhone;
    private SimpleStringProperty workPhone;
    private SimpleStringProperty emergyPhone;
    private SimpleStringProperty email;
    private SimpleStringProperty address;
    public Doctor(){}

    public Doctor(int id, String name, String homeP, String workP, String emgencyP, String email, String address){
        this.doctorId = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.homePhone = new SimpleStringProperty(homeP);
        this.workPhone = new SimpleStringProperty(workP);
        this.emergyPhone = new SimpleStringProperty(emgencyP);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getDoctorId() {
        return doctorId.get();
    }


    public String getName() {
        return name.get();
    }

    public String getHomePhone() {
        return homePhone.get();
    }

    public String getWorkPhone() {
        return workPhone.get();
    }

    public String getEmergPhone() {
        return emergyPhone.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getAddress() {
        return address.get();
    }
}
