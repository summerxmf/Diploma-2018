package domain;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Slot {
    private IntegerProperty slotId = new SimpleIntegerProperty();
    private StringProperty slotStartTime = new SimpleStringProperty();
    public Slot(int id, String startTime){
        this.slotId = new SimpleIntegerProperty(id);
        this.slotStartTime = new SimpleStringProperty(startTime);
    }

    public int getSlotId() {
        return slotId.get();
    }

    public IntegerProperty slotIdProperty() {
        return slotId;
    }

    public String getSlotStartTime() {
        return slotStartTime.get();
    }

    public StringProperty slotStartTimeProperty() {
        return slotStartTime;
    }
}
