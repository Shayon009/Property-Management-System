package propertyassetmanager;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Room {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty type;
    private final IntegerProperty buildingId;
    private final StringProperty buildingName;
    private final BooleanProperty available;

    public Room(int id, String name, String type, int buildingId, String buildingName, boolean available) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.buildingId = new SimpleIntegerProperty(buildingId);
        this.buildingName = new SimpleStringProperty(buildingName);
        this.available = new SimpleBooleanProperty(available);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public int getBuildingId() {
        return buildingId.get();
    }

    public String getBuildingName() {
        return buildingName.get();
    }

    public StringProperty buildingNameProperty() {
        return buildingName;
    }

    public boolean isAvailable() {
        return available.get();
    }

    public BooleanProperty availableProperty() {
        return available;
    }
}