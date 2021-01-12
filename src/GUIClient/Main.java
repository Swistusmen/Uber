package GUIClient;

import ClientMobileApplication.ClientMobileApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ClientLogin.fxml"));

        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(new Scene(root, 200, 200));
        primaryStage.show();
    }
    //ClientMobileApplication mobileApplication;
    public String mojaZmienna="Michal";
    public static void main(String[] args) {
        //ClientMobileApplication mobileApplication=new ClientMobileApplication("192.168.0.143",45600);


        launch(args);
    }
}
