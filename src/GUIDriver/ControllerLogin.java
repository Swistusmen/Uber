package GUIDriver;

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

public class ControllerLogin implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void exitButton() throws Exception {
        System.exit(1);
    }



     public void signUp(ActionEvent event) throws IOException{
        //Loading new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DriverSignUp.fxml"));
        Parent root = loader.load();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Sign Up");
        window.setScene(new Scene(root, 300, 380));
         window.show();
    }

    public void signIn(ActionEvent event) throws IOException{
        //Loading new scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DriverOrder.fxml"));
        Parent root = loader.load();

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setTitle("Sign In..");
        window.setScene(new Scene(root, 200, 200));
        window.show();
    }

}
