package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import pattern.ObserverInterface;


/**
 * This class holds the actual view in which the gamefield gets drawn on a canvas
 * and changed depending on the actions from the user
 * <p>
 *
 * @since 10.11.2021
 */
public class GameFieldPanel extends Region implements ObserverInterface {

    final int BORDER_PATTING = 0;
    private final Image cat = new Image("images/buttons/cat.png");
    private final Image wall = new Image("images/buttons/wall.png");
    private final Image drink = new Image("images/buttons/drink.png");
    private final Image character = new Image("images/buttons/protag.png");
    private GameField gameField;
    private Canvas canvas;
    private GraphicsContext graCon;
    private double tileHeightCalculated;
    private double tileWidthCalculated;

    /**
     * The default constructor of the class
     */
    public GameFieldPanel() {
    }

    /**
     * The custom constructor of the class
     * <p>
     * This constructor is responsible setting up the initial gamefield graphics wise. It sets the canvas on which later is being
     * drawn, calculates the height and width of a singular tile and lastly draws the gamefield it has generated.
     *
     * @param gameField the gamefield which it generates
     * @param height    the total height of the gamefield and thus the canvas
     * @param width     the total width of the gamefield and thus the canvas
     *                  <p>
     * @since 18.11.2021
     */

    //the custom constructor of this class and the drawGameField method were written while being inspired by
    //the following open-source project from tdshi-zz
    //https://github.com/tdshi-zz/cellular/blob/main/src/main/java/de/study/app/view/PopulationPanel.java
    public GameFieldPanel(GameField gameField, int height, int width) {
        this.gameField = gameField;
        this.canvas = new Canvas(height, width);
        this.graCon = canvas.getGraphicsContext2D();
        this.setPrefSize(height, width);
        this.getChildren().add(this.canvas);
        tileHeightCalculated = (this.getPrefHeight() - BORDER_PATTING) / gameField.getGameFieldArray().length;
        tileWidthCalculated = (this.getPrefWidth() - BORDER_PATTING) / gameField.getGameFieldArray()[0].length;
        drawGameField();
    }

    public int getBorderPatting() {
        return BORDER_PATTING;
    }

    public double getTileHeightCalculated() {
        return tileHeightCalculated;
    }

    public double getTileWidthCalculated() {
        return tileWidthCalculated;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }

    public void calculateTileHeightAndWidth() {
        tileHeightCalculated = (getCanvas().getHeight() - BORDER_PATTING) / gameField.getGameFieldArray().length;
        tileWidthCalculated = (getCanvas().getWidth() - BORDER_PATTING) / gameField.getGameFieldArray()[0].length;
    }

    /**
     * Responsible for actually drawing the gamefield.
     * <p>
     * In this method the canvas, on which the gamefield is, first gets cleared and afterwards tiles for the amount of
     * columns and rows of the gamefield array get drawn.
     *
     * @since 18.11.2021
     */
    private void drawGameField() {
        graCon.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < gameField.getGameFieldArray().length; i++) {
            for (int j = 0; j < gameField.getGameFieldArray()[0].length; j++) {
                graCon.setStroke(Color.BLACK);
                graCon.setFill(Color.SLATEGREY);
                graCon.fillRect(BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                graCon.strokeRect(BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
            }
        }
    }

    /**
     * Responsible for drawing objects on the gamefield like walls or the character.
     * <p>
     * In this method the drawGameField method first get called to afterwards draw over it. After it, it searches
     * the gamefield array for the positions of the objects and draws it on the correct tile of the gamefield.
     *
     * @since 18.11.2011
     */
    public void drawObjectsOnGameField() {
        drawGameField();
        for (int i = 0; i < gameField.getGameFieldArray().length; i++) {
            for (int j = 0; j < gameField.getGameFieldArray()[0].length; j++) {
                if (gameField.getGameFieldArray()[i][j].equals("C")) {
                    graCon.drawImage(cat, BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                } else if (gameField.getGameFieldArray()[i][j].equals("W")) {
                    graCon.drawImage(wall, BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                } else if (gameField.getGameFieldArray()[i][j].equals("D")) {
                    graCon.drawImage(drink, BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                }


                if (gameField.getGameFieldArray()[i][j].equals("^") || gameField.getGameFieldArray()[i][j].equals("C^")) {
                    graCon.drawImage(character, BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                } else if (gameField.getGameFieldArray()[i][j].equals("v") || gameField.getGameFieldArray()[i][j].equals("Cv")) {
                    graCon.drawImage(character, BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                } else if (gameField.getGameFieldArray()[i][j].equals(">") || gameField.getGameFieldArray()[i][j].equals("C>")) {
                    graCon.drawImage(character, BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                } else if (gameField.getGameFieldArray()[i][j].equals("<") || gameField.getGameFieldArray()[i][j].equals("C<")) {
                    graCon.drawImage(character, BORDER_PATTING + j * tileWidthCalculated, BORDER_PATTING + i * tileHeightCalculated, tileWidthCalculated, tileHeightCalculated);
                }
            }
        }
    }

    @Override
    public void update(Object object) {
        if (object.getClass() == GameField.class) {
            gameField = (GameField) object;
            calculateTileHeightAndWidth();
        }
        drawObjectsOnGameField();
    }
}
