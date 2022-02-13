package controller;

import javafx.scene.control.Alert;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;

/**
 * This class manages to display the different exceptions for the user, which may occur during the runtime
 *
 * @since 10.02.2022
 */
public class AlertController {

    String[] possibleAlertSound = {"/soundfiles/Error_sound.mp4", "/soundfiles/Not_correct.mp4", "/soundfiles/Think_about_it.mp4", "/soundfiles/Try_again.mp4"};

    /**
     * This method is responsible for showing the exception to the user who he himself is responsible for.
     * <p>
     * When this method is called it sets a new alert, to depict which exception was thrown during the
     * execution of the code from the user. It also randomly chooses an error sound to accompany the alert.
     *
     * @param alertType    The type of alert which should be depicted
     * @param alertTitle   The title of the alert
     * @param alertMessage The actual message of the alert, letting the user know what just happened.
     * @since 10.02.2022
     */
    public void userAlert(Alert.AlertType alertType, String alertTitle, String alertMessage) {
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

    /**
     * This method is responsible for showing the user why their file could not be compiled.
     *
     * @param alertType    The type of alert which should be depicted
     * @param alertTitle   The title of the alert
     * @param alertMessage The actual message of the alert, letting the user know what just happened.
     * @since 10.02.2022
     */
    public void compileAlert(Alert.AlertType alertType, String alertTitle, String alertMessage) {
        Alert alert = new Alert(alertType);
        alert.getDialogPane().setMinWidth(600);
        alert.setTitle(alertTitle);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    /**
     * This method is responsible for showing the user any exception which may occur during runtime and could not
     * be expected during writing of this applications code.
     *
     * @param alertType    The type of alert which should be depicted
     * @param alertTitle   The title of the alert
     * @param alertMessage The actual message of the alert, letting the user know what just happened.
     * @since 10.02.2022
     */
    public void exceptionAlert(Alert.AlertType alertType, String alertTitle, String alertMessage) {
        Alert alert = new Alert(alertType);
        alert.getDialogPane().setMinWidth(600);
        alert.setTitle(alertTitle);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }
}
