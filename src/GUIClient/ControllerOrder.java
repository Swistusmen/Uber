package GUIClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOrder implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void orderRide(ActionEvent event) throws IOException{
        //Loading new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientLocDes.fxml"));
        Parent root = loader.load();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Choose your Location and Destination");
        window.setScene(new Scene(root, 300, 370));
        window.show();
    }

    public void exitButton() throws Exception {
        System.exit(1);
    }



}


