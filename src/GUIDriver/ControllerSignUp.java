package GUIDriver;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    @FXML
    private TextField licenseIDBar;
    @FXML
    private TextField carlicenseIDBar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void confirmSignUp(ActionEvent event) throws Exception {
        String name =nameBar.getText();
        String surname = surnameBar.getText();
        String account = accountBar.getText();
        String phonenumber = phonenumberBar.getText();
        String licenseID =licenseIDBar.getText();
        String carlicenseIDBarText =carlicenseIDBar.getText();
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
        if (licenseIDBar.getText().isEmpty()) {
            licenseIDBar.setPromptText("Please enter your licenseID!");
        }
        if (carlicenseIDBar.getText().isEmpty()) {
            carlicenseIDBar.setPromptText("Please enter your car's licenseID!");
        }

        if (nameBar.getText().length() > 0 && surnameBar.getText().length() > 0 && accountBar.getText().length() > 0 && phonenumberBar.getText().length() > 0) {
            //Money operations?
            //Szukanie ride i drivera?
            //Ustawienie ride na ordered i rozpoczecie ride?

            //Loading ride gui
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DriverOrder.fxml"));
            Parent root = loader.load();

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Search for a ride");
            window.setScene(new Scene(root, 200, 200));
            window.show();
        }

    }


}
