package controller;

import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

public class AlertController {

    String[] possibleAlertSound = {"/soundfiles/Error_sound.mp4", "/soundfiles/Not_correct.mp4", "/soundfiles/Think_about_it.mp4", "/soundfiles/Try_again.mp4"};

    public void alert(Alert.AlertType alertType, String alertTitle, String alertMessage) {
        Alert alert = new Alert(alertType);//sets the alert
        alert.setTitle(alertTitle);
        alert.setContentText(alertMessage);

        try {//sets the sound that accompanies the alert
            int randomNumber = (int) (Math.random() * 4);
            Media alertSound = new Media(getClass().getResource(possibleAlertSound[randomNumber]).toURI().toString());
            MediaPlayer player = new MediaPlayer(alertSound);
            player.play();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        
        alert.showAndWait();
    }

}
