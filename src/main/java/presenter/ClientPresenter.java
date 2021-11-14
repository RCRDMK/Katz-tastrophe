package presenter;

import game.GameField;
import game.GameFieldPanel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
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
        System.out.println(gameField.gameField[5][5]);
    }

    public void onStartButton(ActionEvent actionEvent) {
        Canvas canvas = new Canvas(pane.getWidth(), pane.getHeight());
        GridPane gridPane = new GridPane();
        gridPane.add(canvas, 1, 1);

        //Das funktioniert. Nur 7 Reihen werden gemalt
        int achsenWert = 0;
        for (int i = 0; i < gameField.row; i++, achsenWert = achsenWert + 30) {
            GameFieldPanel.color(canvas, Color.GRAY, i + achsenWert, 10);
            for (int j = 0; j < gameField.column; j++) {
                System.out.println(i + " " + j);

            }
            //Das nicht mehr. Es wird viel zu lange geladen und das Programm reagiert nicht mehr
            /*for (int j = 0; i < gameField.column; j++, achsenWert = achsenWert - 30) {
                GameFieldPanel.color(canvas, Color.GRAY,  30, j + achsenWert);
            }*/
        }

        //TODO sehr schlechter Code. Muss UNBEDINGT verbessert werden!!
        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            colorColumns(0, achsenWert, canvas);
        }

        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            colorColumns(31, achsenWert, canvas);
        }

        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            colorColumns(62, achsenWert, canvas);
        }

        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            colorColumns(93, achsenWert, canvas);
        }

        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            colorColumns(124, achsenWert, canvas);
        }

        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            colorColumns(155, achsenWert, canvas);
        }

        achsenWert = 0;
        for (int i = 0; i < gameField.column; i++, achsenWert = achsenWert + 30) {
            colorColumns(186, achsenWert, canvas);
        }

        GameFieldPanel.refreshElements(canvas);
        pane.setContent(gridPane);
    }

    void colorColumns(int count, int achsenWert, Canvas canvas) {
        GameFieldPanel.color(canvas, Color.GRAY, count, achsenWert + 40);
    }
}
