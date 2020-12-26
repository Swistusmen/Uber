package GUIDriver;

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

public class DController implements Initializable {

    @FXML private TextField destinationBar;
    @FXML private TextField locationBar;
    @FXML private javafx.scene.control.Label driverData;
    @FXML private javafx.scene.control.Label driverRating;
    @FXML private javafx.scene.control.Label licenseID;
    @FXML private javafx.scene.control.Label carLicenseID;
    @FXML private javafx.scene.control.Label clientData;
    @FXML private javafx.scene.control.Label clientRating;
    @FXML private javafx.scene.control.Label ridePrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        driverData.setText("Driver name:");
        driverRating.setText("Driver rating:");
        licenseID.setText("License ID");
        carLicenseID.setText("Car License Number");
    }

    public void searchRide(){   //będzie otrzymywał wtedy dane od serwera?
        destinationBar.setText("client.destination");
        locationBar.setText("client.location");
        clientData.setText("client.data");
        clientRating.setText("client.rating");
        ridePrice.setText("ridePrice");

    }



     public void confirmRideButton(ActionEvent event) throws IOException, InterruptedException {
         //if(clientData.getText().length()>0 && clientRating.getText().length()>0 && clientPrice.getText().length()>0){
         //Money operations?
         //Szukanie ride i drivera?
         //Ustawienie ride na ordered i rozpoczecie ride?

         //Loading ride gui
         FXMLLoader loader = new FXMLLoader(getClass().getResource("DriverAppRide.fxml"));
         Parent root = loader.load();

         Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
         window.setTitle("Drive...");
         window.setScene(new Scene(root, 200, 200));
         window.show();


         PauseTransition wait = new PauseTransition(Duration.seconds(5));   //Ilosc sekund jakie zajmuje ride
         wait.setOnFinished((e) -> {
             Stage stage = new Stage();
             window.close();
             try {
                 finishedRide(stage);
             } catch (IOException ioException) {
                 ioException.printStackTrace();
             }
         });

         wait.play();
     /*}else{
     System.out.println("First you must search for a client!")
     }
      */

    }

    public void finishedRide(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("DriverAppRideFinished.fxml"));
        primaryStage.setTitle("Destination reached!");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }
}
