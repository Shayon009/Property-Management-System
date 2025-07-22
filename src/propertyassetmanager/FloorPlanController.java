package propertyassetmanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class FloorPlanController {

    @FXML private Button room1Btn, room2Btn, room3Btn;
    @FXML private Text roomDetails;

    private UnifiedDatabaseHandler dbHandler;

    public void initialize() {
        dbHandler = new UnifiedDatabaseHandler();
    }

    @FXML
    private void showRoomDetails(ActionEvent event) {
        Button source = (Button) event.getSource();
        String roomName = source.getText();
        try {
            Room room = dbHandler.getRoomByName(roomName);
            if (room != null) {
                String details = "Room: " + room.getName() + "\nType: " + room.getType() + "\nBuilding: " + room.getBuildingName() + "\nAssets: ";
                for (Asset asset : dbHandler.getAssetsInRoom(room.getId())) {
                    details += "\n- " + asset.getName() + " (" + asset.getType() + ", " + asset.getCondition() + ")";
                }
                roomDetails.setText(details);
            } else {
                roomDetails.setText("Room not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            roomDetails.setText("Error fetching room details!");
        }
    }
}