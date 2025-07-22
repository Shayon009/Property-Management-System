module propertyassetmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    exports propertyassetmanager;
    
    opens propertyassetmanager to javafx.fxml;
}
