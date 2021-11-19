package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class GameFieldPanel extends Region {

    //Anleitung zum Arbeiten mit Canvas'. Auch wie man Bilder einfügt oder diese animiert
    //https://edencoding.com/javafx-canvas/

    final int BORDER_PATTING = 20;
    private final Image cat = new Image("images/buttons/cat.png");
    private final Image wall = new Image("images/buttons/wall.png");
    private final Image drink = new Image("images/buttons/drink.png");
    private final Image character = new Image("images/buttons/protag.png");
    private GameField gameField;
    private Canvas canvas;
    private GraphicsContext graCon;
    private double tileHeightCalculated;
    private double tileWidthCalculated;

    public GameFieldPanel() {
    }

    //the custom constructor of this class and the drawGameField method were written while being inspired by
    //the following open-source project from tdshi-zz
    //https://github.com/tdshi-zz/cellular/blob/main/src/main/java/de/study/app/view/PopulationPanel.java

    public GameFieldPanel(GameField gameField, int height, int width) {
        this.gameField = gameField;
        this.canvas = new Canvas(height, width);
        this.graCon = canvas.getGraphicsContext2D();
        this.setPrefSize(height, width);
        this.getChildren().add(this.canvas);
        tileHeightCalculated = (this.getPrefHeight() - BORDER_PATTING) / gameField.getGameField().length;
        tileWidthCalculated = (this.getPrefWidth() - BORDER_PATTING) / gameField.getGameField()[0].length;
        drawGameField();
    }

    void drawGameField() {
        graCon.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < gameField.getGameField().length; i++) {
            for (int j = 0; j < gameField.getGameField()[0].length; j++) {
                graCon.setStroke(Color.BLACK);
                graCon.setFill(Color.SLATEGREY);
                graCon.fillRect(BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                graCon.strokeRect(BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
            }
        }
    }

    public void drawObjectsOnGameField() {

        for (int i = 0; i < gameField.getGameField().length; i++) {
            for (int j = 0; j < gameField.getGameField()[0].length; j++) {
                if (gameField.gameField[i][j].equals("C")) {
                    graCon.drawImage(cat, BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                } else if (gameField.gameField[i][j].equals("W")) {
                    graCon.drawImage(wall, BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                } else if (gameField.gameField[i][j].equals("D")) {
                    graCon.drawImage(drink, BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                } else if (gameField.gameField[i][j].equals("^")) {
                    graCon.drawImage(character, BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                }
            }
        }
    }
}
