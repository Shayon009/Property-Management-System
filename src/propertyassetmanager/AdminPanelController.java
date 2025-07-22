package propertyassetmanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminPanelController {
    @FXML
    private Label welcomeLabel;

    @FXML
    private void initialize() {
        // This will be updated when initData is called
    }

    public void initData(UserInfo userInfo) {
        if (userInfo != null) {
            welcomeLabel.setText("Welcome, " + userInfo.fullName + " (Admin)!");
        } else {
            welcomeLabel.setText("Welcome, Admin!");
        }
    }
} 