package controller;

import game.GameField;
import game.GameFieldPanel;

public class GameFieldPanelController {

    private GameFieldPanel gameFieldPanel;
    private GameField gameField;

    public GameFieldPanelController(int row, int column) {
        this.gameField = new GameField(row, column);
        this.gameFieldPanel = new GameFieldPanel(gameField, 250, 250);
        this.gameField.placeElementsInField(5, 5, "C");
        this.gameField.placeElementsInField(3, 1, "W");
        this.gameField.placeElementsInField(3, 2, "C");
        this.gameField.placeElementsInField(4, 1, "D");
        this.gameField.placeElementsInField(3, 3, "^");
        this.gameFieldPanel.checkGameField();
    }

    public GameFieldPanel getGameFieldPanel() {
        return gameFieldPanel;
    }
}
