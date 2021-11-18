package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class GameFieldPanel extends Region {

    //Anleitung zum Arbeiten mit Canvas'. Auch wie man Bilder einfügt oder diese animiert
    //https://edencoding.com/javafx-canvas/

    final int BORDER = 20;
    private GameField gameField;
    private Canvas canvas;
    private GraphicsContext graCon;
    private double heightCalculated;
    private double widthCalculated;

    public GameFieldPanel() {
    }

    public GameFieldPanel(GameField gameField, int height, int width) {
        this.gameField = gameField;
        this.canvas = new Canvas(height, width);
        this.graCon = canvas.getGraphicsContext2D();
        this.setPrefSize(height, width);
        this.getChildren().add(this.canvas);
        heightCalculated = (this.getPrefHeight() - BORDER) / gameField.getGameField().length;
        widthCalculated = (this.getPrefWidth() - BORDER) / gameField.getGameField()[0].length;
        drawGameField();
    }

    void drawGameField() {
        for (int i = 0; i < gameField.getGameField().length; i++) {
            for (int j = 0; j < gameField.getGameField()[0].length; j++) {
                graCon.setStroke(Color.BLACK);
                graCon.setFill(Color.RED);
                graCon.fillRect(BORDER + j * widthCalculated, BORDER + i * heightCalculated, widthCalculated, heightCalculated);
                graCon.strokeRect(BORDER + j * widthCalculated, BORDER + i * heightCalculated, widthCalculated, heightCalculated);
            }
        }
    }

    public void checkGameField() {
        for (int i = 0; i < gameField.getGameField().length; i++) {
            for (int j = 0; j < gameField.getGameField()[0].length; j++) {
                graCon.setFill(Color.BLACK);
                if (gameField.gameField[i][j].equals("^")) {
                    graCon.fillRect(BORDER + j * widthCalculated, BORDER + i * heightCalculated, widthCalculated, heightCalculated);
                }
            }
        }
    }
}
