package GUIClient;

import WebServerApplication.CostCalculator;
import WebServerApplication.DistanceCalculator;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerApp implements Initializable {

    @FXML private TextArea InputAdressLabel;
    @FXML private TextArea OutputAdressLabel;
    @FXML private TextArea CarNumberLabel;
    @FXML private TextArea PriceLabel;
    @FXML private TextArea PhoneLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*InputAdressLabel.setText(A.ride.inputAddress.);
        OutputAdressLabel.setText(A.ride.outputAddress);
        CarNumberLabel.setText(A.ride.carNumber);
        PriceLabel.setText("Price");
        PhoneLabel.setText(A.ride.phoneDriver);*/
        String input="AAA";

        InputAdressLabel.setText(input);
        OutputAdressLabel.setText("A");
        CarNumberLabel.setText("A");
        PriceLabel.setText("Price");
        PhoneLabel.setText("A");

    }

     public void confirmRideButton(ActionEvent event) throws IOException, InterruptedException {
         //Loading ride gui
         FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientAppRideFinished.fxml"));
         Parent root = loader.load();

         Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
         window.setTitle("Destination reached!");
         window.setScene(new Scene(root, 300, 200));
         window.show();

    }

}
