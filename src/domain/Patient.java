package domain;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.DatePicker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Patient implements Person {
    private SimpleIntegerProperty patientId;
    private SimpleStringProperty title;
    private SimpleStringProperty name;
    private SimpleStringProperty dateOfBirth;
    private SimpleStringProperty phone;
    private SimpleStringProperty emergyPhone;
    private SimpleStringProperty email;
    private SimpleStringProperty address;
    private SimpleStringProperty medicalHistory;

    public Patient(){}

    public Patient(int id, String title, String name, String dateOfBirth, String phone, String emergencyP, String email, String address, String medicalHistory){

        this.patientId = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.name = new SimpleStringProperty(name);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);

        this.phone = new SimpleStringProperty(phone);
        this.emergyPhone = new SimpleStringProperty(emergencyP);
        this.email = new SimpleStringProperty(email);
        this.address = new SimpleStringProperty(address);

        this.medicalHistory = new SimpleStringProperty(medicalHistory);
    }

    public int getPatientId() {
        return patientId.get();
    }


    public String getTitle() {
        return title.get();
    }


    public String getName() {
        return name.get();
    }



    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public String getPhone() {
        return phone.get();
    }


    public String getEmergyPhone() {
        return emergyPhone.get();
    }


    public String getEmail() {
        return email.get();
    }


    public String getAddress() {
        return address.get();
    }



    public String getMedicalHistory() {
        return medicalHistory.get();
    }

}