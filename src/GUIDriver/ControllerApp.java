package GUIDriver;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

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
        InputAdressLabel.setText("InAdress");
        OutputAdressLabel.setText("OutAdress");
        CarNumberLabel.setText("CarNumber");
        PriceLabel.setText("Price");
        PhoneLabel.setText("ClientPhone");
    }


     public void confirmRideButton(ActionEvent event) throws IOException, InterruptedException {
         //Loading ride gui
         FXMLLoader loader = new FXMLLoader(getClass().getResource("DriverAppRideFinished.fxml"));
         Parent root = loader.load();

         Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
         window.setTitle("Destination reached!");
         window.setScene(new Scene(root, 300, 200));
         window.show();

    }

}
