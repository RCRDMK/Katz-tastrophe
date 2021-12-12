package controller;

import game.GameCharacter;
import game.GameField;
import game.GameFieldPanel;
import pattern.ObservedObject;

/**
 * The controller class for the gameFieldPanel class
 *
 * @since 18.11.2021
 */

public class GameFieldPanelController extends ObservedObject {

    private GameFieldPanel gameFieldPanel;
    private GameField gameField;
    private GameCharacter character;


    /**
     * The custom constructor for the class.
     * <p>
     * This constructor is responsible for setting the size of the gamefield array and the gamefield panel but also
     * for initially placing objects in the gamefield.
     *
     * @param rows    the amount of the rows the gamefield array should initially depict
     * @param columns the amount of the columns the gamefield array should initially depict
     * @since 18.11.2021
     */
    public GameFieldPanelController(int rows, int columns) {
        this.gameField = new GameField(rows, columns);
        this.gameFieldPanel = new GameFieldPanel(gameField, 250, 250);
        this.character = new GameCharacter(gameField);
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

    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }

    public GameFieldPanel getGameFieldPanel() {
        return gameFieldPanel;
    }

    public GameCharacter getCharacter() {
        return character;
    }

    public GameFieldPanel te() {
        GameFieldPanel gfp = new GameFieldPanel(gameField, 200, 200);
        notifyRegisteredObservers(this);
        return gfp;
    }
}
