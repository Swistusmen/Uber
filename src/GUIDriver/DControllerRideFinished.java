package GUIDriver;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class DControllerRideFinished {

    @FXML
    private TextField ratingBar;

    public void confirmationButton(){

        double rating=Double.parseDouble(ratingBar.getText());
        if(ratingBar.getText().isEmpty())
        {
            ratingBar.setPromptText("Please rate our ride!");

        }else if(rating<1 || rating>5){
            ratingBar.setPromptText("Wrong value, please enter score from 1-5");
        }else{
            //rating operations
            //moeny operations
            //set ride to finished
            System.exit(1);
        }

    }

}
