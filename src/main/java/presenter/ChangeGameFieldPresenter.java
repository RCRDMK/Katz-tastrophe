package presenter;

import game.GameField;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ChangeGameFieldPresenter extends Application {

    @FXML
    Button changeViewCancel;

    @FXML
    Label changeViewRowCurrently;

    @FXML
    Label changeViewColumnCurrently;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(GameField.class.getClassLoader().getResource("fxml/ChangeGameFieldView.fxml"));
        primaryStage.setScene(new Scene(root));

        primaryStage.setTitle("Größe des Spielfelds verändern");

        changeViewRowCurrently.setText("niofkd");

        changeViewColumnCurrently.setText("fjf");

        primaryStage.show();
    }

    public void onChangeViewCancelClicked(ActionEvent actionEvent) {
        System.out.println("Close");
        //primaryStage.close();
    }

    public void onChangeViewAcceptClicked(ActionEvent actionEvent) {
        System.out.println("Accept");
        /*int rows = Integer.valueOf(rowText.getText());
        int columns = Integer.valueOf(columnText.getText());
        GameField.resizeGameFieldSize(rows, columns);
        GameField.checkIfCharacterExists();
        GameFieldPanel.drawObjectsOnGameField();
        primaryStage.close();*/
    }
}
