package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;

public class GameFieldPanel extends Region {

    //Anleitung zum Arbeiten mit Canvas'. Auch wie man Bilder einfügt oder diese animiert
    //https://edencoding.com/javafx-canvas/

    int test = 20;
    private GameField gameField;
    private Canvas canvas;
    private GraphicsContext graCon;
    private double heightCalculated;
    private double widthCalculated;

    public GameFieldPanel() {
    }

    public GameFieldPanel(GameField gameField, int height, int width) {
        this.gameField = gameField;
        this.canvas = new Canvas(height * 10, width * 10);
        this.graCon = canvas.getGraphicsContext2D();
        this.setPrefSize(canvas.getWidth(), canvas.getHeight());
        this.getChildren().add(this.canvas);
        heightCalculated = this.getPrefHeight() / gameField.getGameField().length;
        widthCalculated = this.getPrefWidth() / gameField.getGameField()[0].length;
        drawGameField();
    }

    void drawGameField() {
        for (int i = 0; i < gameField.getGameField().length; i++) {
            for (int j = 0; j < gameField.getGameField()[0].length; j++) {
                graCon.fillRect(test + j * widthCalculated, test + i * heightCalculated, widthCalculated, heightCalculated);
            }
        }
    }
}
