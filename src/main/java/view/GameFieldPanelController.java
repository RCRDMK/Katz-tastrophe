/*
 * Katz-tastrophe - a miniature programming learn environment
 * Copyright (C) 2022 RCRDMK
 *
 * This program (Katz-tastrophe) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program (Katz-tastrophe) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See LICENSE File in the main directory. If not, see <https://www.gnu.org/licenses/>.
 */

package view;

import model.GameCharacter;
import model.GameField;
import model.GameFieldPanel;
import model.pattern.ObservedObject;

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
        this.gameField.placeObjectsInGameField(3, 3, ">");
        this.gameFieldPanel.drawObjectsOnGameField();
    }

    public GameField getGameField() {
        return gameField;
    }

    public GameFieldPanel getGameFieldPanel() {
        return gameFieldPanel;
    }

    public GameCharacter getCharacter() {
        return character;
    }

    public void setCharacter(GameCharacter character) {
        this.character = character;
    }
}
