package presenter;

import game.GameField;
import game.GameFieldPanel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ClientPresenter extends Application {

    public static final String fxml = "/fxml/ClientView.fxml";
    public ScrollPane pane;
    @FXML
    Label infoLabel;
    GameFieldPanel gameFieldPanel;
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

        for (int i = 0; i < pane.getWidth(); i = i + 30) {
            for (int j = 0; j < pane.getWidth(); j = j + 30) {
                GameFieldPanel.color(canvas, Color.GRAY, i, j);
            }
        }
        GameFieldPanel.refreshElements(canvas);
        chara();
        pane.setContent(canvas);
    }


    public void chara() {
        for (int i = 0; i < GameField.row; i++) {
            for (int j = 0; j < GameField.column; j++) {
                //String s = gameField.gamefield[i][j;
                if (GameField.gameField[i][j].equals("^")) {
                    System.out.println("Hey");
                } else {
                    System.out.println("Anders");
                }
            }
        }
    }
}
