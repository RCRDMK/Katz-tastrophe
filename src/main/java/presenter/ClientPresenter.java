package presenter;

import game.GameField;
import game.GameFieldPanel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientPresenter extends Application {

    public static final String fxml = "/fxml/ClientView.fxml";
    @FXML
    ScrollPane pane;
    @FXML
    Label infoLabel;
    GameField gameField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GameField.start(primaryStage);
        GameField.GameField(7, 7);
        gameField.printGameField();
    }

    public void onStartButton(ActionEvent actionEvent) {
        Canvas canvas = new Canvas(pane.getWidth(), pane.getHeight());
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);

        //Das funktioniert. Nur die Anzahl der rows werden gemalt
        int achsenWert = 0;
        for (int i = 0; i < gameField.row; i++, achsenWert = achsenWert + 30) {
            GameFieldPanel.color(canvas, Color.GRAY, i + achsenWert, 10);

            //Das nicht mehr. Es wird viel zu lange geladen und das Programm reagiert nicht mehr
            /*for (int j = 0; i < gameField.column; j++, achsenWert = achsenWert - 30) {
                GameFieldPanel.color(canvas, Color.GRAY,  30, j + achsenWert);
            }*/
        }

        //Auch eine Möglichkeit. Einfach nur die Y-Achse anpassen. Gibt aber auch wieder smelly Code
        /*achsenWert = 0;
        for (int i = 0; i < gameField.row; i++, achsenWert = achsenWert + 30) {
            GameFieldPanel.color(canvas, Color.GRAY, i + achsenWert, 30);
        }*/

        //TODO sehr schlechter Code. Muss UNBEDINGT verbessert werden!!
        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            GameFieldPanel.colorColumns(0, achsenWert, canvas);
        }

        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            GameFieldPanel.colorColumns(31, achsenWert, canvas);
        }

        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            GameFieldPanel.colorColumns(62, achsenWert, canvas);
        }

        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            GameFieldPanel.colorColumns(93, achsenWert, canvas);
        }

        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            GameFieldPanel.colorColumns(124, achsenWert, canvas);
        }

        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            GameFieldPanel.colorColumns(155, achsenWert, canvas);
        }

        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            GameFieldPanel.colorColumns(186, achsenWert, canvas);
        }

        //GameFieldPanel.refreshElements(canvas);
        pane.setContent(borderPane);
    }
}
