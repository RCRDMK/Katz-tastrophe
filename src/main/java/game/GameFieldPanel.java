package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import presenter.ClientPresenter;

public class GameFieldPanel extends Region {

    //https://edencoding.com/javafx-canvas/

    static GameField gameField;
    public Canvas canvas;
    GraphicsContext graCon;

    GameFieldPanel(GameField gameField, int rows, int columns) {
        this.gameField = gameField;
        canvas = new Canvas(rows, columns);
        graCon = canvas.getGraphicsContext2D();
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
        ClientPresenter.test.getChildren().addAll(canvas);
        //canvas.setStyle("-fx-background-color: red");
        canvas.getStyleClass().add("test");
        draw();
    }

    public void draw() {
        graCon.clearRect(0, 0, canvas.getHeight(), canvas.getWidth());

        for (int i = 0; i < gameField.row; i++) {
            for (int j = 0; j < gameField.column; j++) {
                graCon.setFill(Paint.valueOf(GameField.gameField[i][j]));
            }
        }
    }

    void clear() {
        graCon.clearRect(0, 0, canvas.getHeight(), canvas.getWidth());
    }
}
