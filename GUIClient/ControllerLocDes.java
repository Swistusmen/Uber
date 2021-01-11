package GUIClient;

import WebServerApplication.CostCalculator;
import WebServerApplication.DistanceCalculator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLocDes implements Initializable {

    @FXML
    private TextField destinationBar;
    @FXML
    private TextField locationBar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void confirmLocDes(ActionEvent event) throws Exception {
        String location = locationBar.getText();
        String destination = destinationBar.getText();
        if (locationBar.getText().isEmpty()) {
            locationBar.setPromptText("Please choose your location!");
        }
        if (destinationBar.getText().isEmpty()) {
            destinationBar.setPromptText("Please choose your destination!");
        }

        if (locationBar.getText().length() > 0 && destinationBar.getText().length() > 0) {
            DistanceCalculator distanceCalculator = new DistanceCalculator();
            CostCalculator costCalculator = new CostCalculator(10, 3);
            double cost = costCalculator.getCost(distanceCalculator.getDistance(location, destination));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientApp.fxml"));
            Parent root = loader.load();

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Confirm Ride");
            window.setScene(new Scene(root, 310, 400));
            window.show();
        }
    }



}
