package UI.model;

import domain.Doctor;
import domain.Patient;

import java.time.LocalDate;

public class ModelAppointment {
    private final static ModelAppointment instance = new ModelAppointment();

    public static ModelAppointment getInstance() {
        return instance;
    }

    public LocalDate apptDate;
    public Doctor apptDoctor;
    public Patient apptPatient;
    public int apptSlot;



}
