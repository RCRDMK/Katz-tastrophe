package controller;

import game.GameField;
import game.GameFieldPanel;

/**
 * The controller class for the gameFieldPanel class
 *
 * @since 18.11.2021
 */

public class GameFieldPanelController {

    private GameFieldPanel gameFieldPanel;
    private GameField gameField;

    /**
     * The custom constructor for the class.
     * <p>
     * This constructor is responsible for setting the size of the gamefield array and the gamefield panel but also
     * for initially placing objects in the gamefield.
     *
     * @param rows    the amount of the rows the gamefield array should depict
     * @param columns the amount of the columns the gamefield array should depict
     * @since 18.11.2021
     */
    public GameFieldPanelController(int rows, int columns) {
        this.gameField = new GameField(rows, columns);
        this.gameFieldPanel = new GameFieldPanel(gameField, 250, 250);
        this.gameField.placeObjectsInGameField(5, 5, "C");
        this.gameField.placeObjectsInGameField(3, 1, "W");
        this.gameField.placeObjectsInGameField(3, 2, "C");
        this.gameField.placeObjectsInGameField(4, 1, "D");
        this.gameField.placeObjectsInGameField(3, 3, "^");
        this.gameFieldPanel.drawObjectsOnGameField();
    }

    public GameField getGameField() {
        return gameField;
    }

    public GameFieldPanel getGameFieldPanel() {
        return gameFieldPanel;
    }
}
