package UI.model;

import java.time.LocalDate;

public class ModelPatient {
    private final static ModelPatient instance = new ModelPatient();

    public static ModelPatient getInstance() {
        return instance;
    }

    public int patientId;
}
