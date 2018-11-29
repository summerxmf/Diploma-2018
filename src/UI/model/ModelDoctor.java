package UI.model;

public class ModelDoctor {
    private final static ModelDoctor instance = new ModelDoctor();

    public static ModelDoctor getInstance() {
        return instance;
    }

    public int doctorId;
}
