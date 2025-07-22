package propertyassetmanager;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Asset {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty type;
    private final IntegerProperty roomId;
    private final StringProperty roomName;
    private final StringProperty condition;

    public Asset(int id, String name, String type, int roomId, String roomName, String condition) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
        this.roomId = new SimpleIntegerProperty(roomId);
        this.roomName = new SimpleStringProperty(roomName);
        this.condition = new SimpleStringProperty(condition);
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

    public int getRoomId() {
        return roomId.get();
    }

    public String getRoomName() {
        return roomName.get();
    }

    public StringProperty roomNameProperty() {
        return roomName;
    }

    public String getCondition() {
        return condition.get();
    }

    public StringProperty conditionProperty() {
        return condition;
    }
}