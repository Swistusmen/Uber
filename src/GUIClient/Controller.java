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

public class Controller implements Initializable {

    @FXML private TextField destinationBar;
    @FXML private TextField locationBar;
    @FXML private javafx.scene.control.Label userData;
    @FXML private javafx.scene.control.Label userRating;
    @FXML private javafx.scene.control.Label driverData;
    @FXML private javafx.scene.control.Label driverRating;
    @FXML private javafx.scene.control.Label ridePrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userData.setText("User name:");
        userRating.setText("User rating:");
    }

    public void searchDriver() throws Exception {
        String location=locationBar.getText();
        String destination =destinationBar.getText();
        if(locationBar.getText().isEmpty())
        {
            locationBar.setPromptText("Please choose your location!");
        }
        if(destinationBar.getText().isEmpty())
        {
            destinationBar.setPromptText("Please choose your destination!");
        }

        if(locationBar.getText().length()>0 && destinationBar.getText().length()>0)
        {
            DistanceCalculator distanceCalculator=new DistanceCalculator();
            CostCalculator costCalculator=new CostCalculator(10,3);
            double cost=costCalculator.getCost(distanceCalculator.getDistance(location,destination));

            driverData.setText("Driver name:");
            driverRating.setText("Driver rating:");
            ridePrice.setText(Double.toString(cost));
        }

    }



     public void confirmRideButton(ActionEvent event) throws IOException, InterruptedException {
        if(driverData.getText().length()>0 && driverRating.getText().length()>0 && ridePrice.getText().length()>0){

            //Money operations?
            //Szukanie ride i drivera?
            //Ustawienie ride na ordered i rozpoczecie ride?

            //Loading ride gui
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientAppRide.fxml"));
            Parent root = loader.load();

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setTitle("Drive...");
            window.setScene(new Scene(root, 350, 200));
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

        }else{
            System.out.println("First you must search for a driver!");
        }


    }

    public void finishedRide(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ClientAppRideFinished.fxml"));
        primaryStage.setTitle("Destination reached!");
        primaryStage.setScene(new Scene(root, 300, 200));
        primaryStage.show();
    }
}
