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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSignUp implements Initializable {

    @FXML
    private TextField nameBar;
    @FXML
    private TextField surnameBar;
    @FXML
    private TextField accountBar;
    @FXML
    private TextField phonenumberBar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void confirmSignUp(ActionEvent event) throws Exception {
        String name =nameBar.getText();
        String surname = surnameBar.getText();
        String account = accountBar.getText();
        String phonenumber = phonenumberBar.getText();
        if (nameBar.getText().isEmpty()) {
            nameBar.setPromptText("Please enter your name!");
        }
        if (surnameBar.getText().isEmpty()) {
            surnameBar.setPromptText("Please enter your surname!");
        }
        if (accountBar.getText().isEmpty()) {
            accountBar.setPromptText("Please enter your account number!");
        }
        if (phonenumberBar.getText().isEmpty()) {
            phonenumberBar.setPromptText("Please enter your phone number!");
        }

        if (nameBar.getText().length() > 0 && surnameBar.getText().length() > 0 && accountBar.getText().length() > 0 && phonenumberBar.getText().length() > 0) {
            //Money operations?
            //Szukanie ride i drivera?
            //Ustawienie ride na ordered i rozpoczecie ride?

            //Loading ride gui
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientOrder.fxml"));
            Parent root = loader.load();

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Order a Ride");
            window.setScene(new Scene(root, 200, 200));
            window.show();
        }

    }


    //public void confirmSignUp(ActionEvent event) throws IOException, InterruptedException {
      //  if (driverData.getText().length() > 0 && driverRating.getText().length() > 0 && ridePrice.getText().length() > 0) {




      //  }

   // }
}
