package game;

import game.exceptions.*;
import pattern.ObservedObject;

/**
 * This class is responsible handling every action the game character can do.
 *
 * @since 03.11.2021
 */

public class GameCharacter extends ObservedObject {

    private GameField gameField;

    private boolean handsFull = false;
    private boolean drinkInHand = false;
    private boolean catInHand = false;

    public GameCharacter() {

    }

    public void setGameCharacter(GameField gameField, GameCharacter gameCharacter) {
        this.gameField = gameField;
        this.handsFull = gameCharacter.handsFull;
        this.drinkInHand = gameCharacter.drinkInHand;
        this.catInHand = gameCharacter.catInHand;
        this.copy(gameCharacter);
        gameCharacter.clear();
    }

    public GameCharacter(GameField gameField) {
        this.gameField = gameField;
    }

    /**
     * Responsible for changing the direction in which the character currently looks
     *
     * @param direction the new direction in which the character ought to look
     * @since 03.11.2021
     */
    public void lookHere(String direction) {
        switch (direction) {
            case "up":
                gameField.setCharacter("^");
                changeCharacterView();
                break;
            case "down":
                gameField.setCharacter("v");
                changeCharacterView();
                break;
            case "left":
                gameField.setCharacter("<");
                changeCharacterView();
                break;
            case "right":
                gameField.setCharacter(">");
                changeCharacterView();
                break;
            default:
                System.out.println("Bitte nur gültige Richtungen wie Up, Down, Left und Right benutzen.");
        }
    }

    /**
     * Responsible for changing the direction the character looks in the gamefield array
     *
     * @since 03.11.2021
     */
    private void changeCharacterView() {
        for (int i = 0; i <= gameField.getRow() - 1; i++) {
            for (int j = 0; j <= gameField.getColumn() - 1; j++) {
                if (gameField.getGameFieldArray()[i][j].equals("^") || gameField.getGameFieldArray()[i][j].equals("v") || gameField.getGameFieldArray()[i][j].equals("<") || gameField.getGameFieldArray()[i][j].equals(">")) {
                    gameField.getGameFieldArray()[i][j] = gameField.getCharacter();
                }
            }
        }
    }

    /**
     * Responsible for moving the character one tile up. Though the character can only move if he looks into this direction.
     *
     * @throws WallInFrontException    If a wall is in front of the character, the character can't move up
     * @throws CatInFrontException     If a cat is in front of the character and the character isn't able to step over the cat
     * @throws DrinkInFrontException   If a drink is in front of the character and the user wants to put something down on that tile
     * @throws EndOfGameFieldException If the character has reached the edge of the gamefield and should still move upwards
     * @since 05.11.2021
     */
    //TODO Spieler darf nicht auf das selbe Feld wie Trinken
    public void moveUp() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        try {
            for (int i = 0; i < gameField.getRow(); i++) {
                for (int j = 0; j < gameField.getColumn(); j++) {
                    if (gameField.getGameFieldArray()[i][j].equals("C^")) {
                        gameField.getGameFieldArray()[i][j] = "C";
                        gameField.placeObjectsInGameField(i - 1, j, "^");
                        break;
                    }
                    if (gameField.getGameFieldArray()[i][j].equals("^")) {
                        if (i == gameField.getRow() / gameField.getRow() - 1) {
                            throw new EndOfGameFieldException();
                        } else if (gameField.getGameFieldArray()[i - 1][j].equals("W")) {
                            throw new WallInFrontException();
                        } else if (gameField.getGameFieldArray()[i - 1][j].equals("C")) {
                            gameField.getGameFieldArray()[i][j] = "x";
                            gameField.placeObjectsInGameField(i - 1, j, "C^");
                            break;
                        } else {
                            gameField.getGameFieldArray()[i][j] = "x";
                            gameField.placeObjectsInGameField(i - 1, j, "^");
                            break;
                        }
                    }
                }
            }
            gameField.fillUpGameField();
            notifyRegisteredObservers(this);
        } catch (EndOfGameFieldException eogfe) {
            System.out.println("Edge of gamefield is reached");
        } catch (WallInFrontException wife) {
            System.out.println("Can't move in this direction. Wall in front");
        }
    }

    /**
     * Responsible for moving the character one tile down. Though the character can only move if he looks into this direction.
     *
     * @throws WallInFrontException    If a wall is in front of the character, the character can't move down
     * @throws CatInFrontException     If a cat is in front of the character and the character isn't able to step over the cat
     * @throws DrinkInFrontException   If a drink is in front of the character and the user wants to put something down on that tile
     * @throws EndOfGameFieldException If the character has reached the edge of the gamefield and should still move downwards
     * @since 05.11.2021
     */
    public void moveDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        try {
            for (int i = 0; i < gameField.getRow(); i++) {
                for (int j = 0; j < gameField.getColumn(); j++) {
                    if (gameField.getGameFieldArray()[i][j].equals("Cv")) {
                        gameField.getGameFieldArray()[i][j] = "C";
                        gameField.placeObjectsInGameField(i + 1, j, "v");
                        i = gameField.getRow() - 1;
                        break;
                    }
                    if (gameField.getGameFieldArray()[i][j].equals("v")) {
                        if (i + 1 == gameField.getRow()) {
                            throw new EndOfGameFieldException();
                        } else if (gameField.getGameFieldArray()[i + 1][j].equals("W")) {
                            throw new WallInFrontException();
                        } else if (gameField.getGameFieldArray()[i + 1][j].equals("C")) {
                            gameField.getGameFieldArray()[i][j] = "x";
                            gameField.placeObjectsInGameField(i + 1, j, "Cv");
                            i = gameField.getRow() - 1;
                            break;
                        } else {
                            gameField.getGameFieldArray()[i][j] = "x";
                            gameField.placeObjectsInGameField(i + 1, j, "v");
                            i = gameField.getRow() - 1;
                            break;
                        }
                    }
                }
            }
            gameField.fillUpGameField();
            notifyRegisteredObservers(this);
        } catch (EndOfGameFieldException eogfe) {
            System.out.println("Edge of gamefield is reached");
        } catch (WallInFrontException wife) {
            System.out.println("Can't move in this direction. Wall in front");
        }
    }

    /**
     * Responsible for moving the character one tile to the right. Though the character can only move if he looks into this direction.
     *
     * @throws WallInFrontException    If a wall is in front of the character, the character can't move to the right
     * @throws CatInFrontException     If a cat is in front of the character and the character isn't able to step over the cat
     * @throws DrinkInFrontException   If a drink is in front of the character and the user wants to put something down on that tile
     * @throws EndOfGameFieldException If the character has reached the edge of the gamefield and should still move to the right
     * @since 05.11.2021
     */
    public void moveRight() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        try {
            for (int i = 0; i < gameField.getRow(); i++) {
                for (int j = 0; j < gameField.getColumn(); j++) {
                    if (gameField.getGameFieldArray()[i][j].equals("C>")) {
                        gameField.getGameFieldArray()[i][j] = "C";
                        gameField.placeObjectsInGameField(i, j + 1, ">");
                        break;
                    }
                    if (gameField.getGameFieldArray()[i][j].equals(">")) {
                        if (j + 1 == gameField.getColumn()) {
                            throw new EndOfGameFieldException();
                        } else if (gameField.getGameFieldArray()[i][j + 1].equals("W")) {
                            throw new WallInFrontException();
                        } else if (gameField.getGameFieldArray()[i][j + 1].equals("C")) {
                            gameField.getGameFieldArray()[i][j] = "x";
                            gameField.placeObjectsInGameField(i, j + 1, "C>");
                            break;
                        } else {
                            gameField.getGameFieldArray()[i][j] = "x";
                            gameField.placeObjectsInGameField(i, j + 1, ">");
                            break;
                        }
                    }
                }
            }
            gameField.fillUpGameField();
            notifyRegisteredObservers(this);
        } catch (EndOfGameFieldException eogfe) {
            System.out.println("Edge of gamefield is reached");
        } catch (WallInFrontException wife) {
            System.out.println("Can't move in this direction. Wall in front");
        }
    }

    /**
     * Responsible for moving the character one tile to the left. Though the character can only move if he looks into this direction.
     *
     * @throws WallInFrontException    If a wall is in front of the character, the character can't move to the left
     * @throws CatInFrontException     If a cat is in front of the character and the character isn't able to step over the cat
     * @throws DrinkInFrontException   If a drink is in front of the character and the user wants to put something down on that tile
     * @throws EndOfGameFieldException If the character has reached the edge of the gamefield and should still move to the left
     * @since 05.11.2021
     */
    public void moveLeft() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        try {
            for (int i = 0; i < gameField.getRow(); i++) {
                for (int j = 0; j < gameField.getColumn(); j++) {
                    if (gameField.getGameFieldArray()[i][j].equals("C<")) {
                        gameField.getGameFieldArray()[i][j] = "C";
                        gameField.placeObjectsInGameField(i, j - 1, "<");
                        break;
                    }
                    if (gameField.getGameFieldArray()[i][j].equals("<")) {
                        if (j == gameField.getColumn() / gameField.getColumn() - 1) {
                            throw new EndOfGameFieldException();
                        } else if (gameField.getGameFieldArray()[i][j - 1].equals("W")) {
                            throw new WallInFrontException();
                        } else if (gameField.getGameFieldArray()[i][j - 1].equals("C")) {
                            gameField.getGameFieldArray()[i][j] = "x";
                            gameField.placeObjectsInGameField(i, j - 1, "C<");
                            break;
                        } else {
                            gameField.getGameFieldArray()[i][j] = "x";
                            gameField.placeObjectsInGameField(i, j - 1, "<");
                            break;
                        }
                    }
                }
            }
            gameField.fillUpGameField();
            notifyRegisteredObservers(this);
        } catch (EndOfGameFieldException eogfe) {
            System.out.println("Edge of gamefield is reached");
        } catch (WallInFrontException wife) {
            System.out.println("Can't move in this direction. Wall in front");
        }
    }

    /**
     * Responsible for taking a cat from a tile and communicating this change to the gamefield array.
     * <p>
     * In this method the array gets checked in which direction the character looks and if the character is even able
     * to pick up a cat at the moment.
     *
     * @throws HandsNotEmptyException If the user wants to pick up a cat even though the hands of the character are not empty at the moment.
     * @since 05.11.2011
     */
    //TODO Nehmen und Ablegen auf EndOfGameFieldException überprüfen sowie was passiert wenn nicht das gewünschte Objekt vor dem Charakter liegt
    public void takeCat() throws HandsNotEmptyException {
        try {
            if (!handsFull) {
                handsFull = true;
                catInHand = true;

                switch (gameField.getCharacter()) {
                    case "^":
                        for (int i = 0; i < gameField.getRow() - 1; i++) {
                            for (int j = 0; j < gameField.getColumn() - 1; j++) {
                                if (gameField.getGameFieldArray()[i][j].equals("^")) {
                                    gameField.getGameFieldArray()[i - 1][j] = "x";
                                    break;
                                }
                            }
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case "v":
                        for (int i = 0; i < gameField.getRow() - 1; i++) {
                            for (int j = 0; j < gameField.getColumn() - 1; j++) {
                                if (gameField.getGameFieldArray()[i][j].equals("v")) {
                                    gameField.getGameFieldArray()[i + 1][j] = "x";
                                    break;
                                }
                            }
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case ">":
                        for (int i = 0; i < gameField.getRow() - 1; i++) {
                            for (int j = 0; j < gameField.getColumn() - 1; j++) {
                                if (gameField.getGameFieldArray()[i][j].equals(">")) {
                                    gameField.getGameFieldArray()[i][j + 1] = "x";
                                    break;
                                }
                            }
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case "<":
                        for (int i = 0; i < gameField.getRow() - 1; i++) {
                            for (int j = 0; j < gameField.getColumn() - 1; j++) {
                                if (gameField.getGameFieldArray()[i][j].equals("<")) {
                                    gameField.getGameFieldArray()[i][j - 1] = "x";
                                    break;
                                }
                            }
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                }
            } else {
                throw new HandsNotEmptyException();
            }
        } catch (HandsNotEmptyException hnee) {
            System.out.println("Hands are not empty");
        }
    }

    /**
     * Responsible for taking a drink from a tile and communicating this change to the gamefield array.
     * <p>
     * In this method the array gets checked in which direction the character looks and if the character is even able
     * to pick up a drink at the moment.
     *
     * @throws HandsNotEmptyException If the user wants to pick up a drink even though the hands of the character are not empty at the moment.
     * @since 05.11.2011
     */
    public void takeDrink() throws HandsNotEmptyException {
        if (!handsFull) {
            handsFull = true;
            drinkInHand = true;

            switch (gameField.getCharacter()) {
                case "^":
                    for (int i = 0; i < gameField.getRow() - 1; i++) {
                        for (int j = 0; j < gameField.getColumn() - 1; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals("^")) {
                                gameField.getGameFieldArray()[i - 1][j] = "x";
                                break;
                            }
                        }
                    }
                    gameField.fillUpGameField();
                    notifyRegisteredObservers(this);
                    break;
                case "v":
                    for (int i = 0; i < gameField.getRow() - 1; i++) {
                        for (int j = 0; j < gameField.getColumn() - 1; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals("v")) {
                                gameField.getGameFieldArray()[i + 1][j] = "x";
                                break;
                            }
                        }
                    }
                    gameField.fillUpGameField();
                    notifyRegisteredObservers(this);
                    break;
                case ">":
                    for (int i = 0; i < gameField.getRow() - 1; i++) {
                        for (int j = 0; j < gameField.getColumn() - 1; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals(">")) {
                                gameField.getGameFieldArray()[i][j + 1] = "x";
                                break;
                            }
                        }
                    }
                    gameField.fillUpGameField();
                    notifyRegisteredObservers(this);
                    break;
                case "<":
                    for (int i = 0; i < gameField.getRow() - 1; i++) {
                        for (int j = 0; j < gameField.getColumn() - 1; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals("<")) {
                                gameField.getGameFieldArray()[i][j - 1] = "x";
                                break;
                            }
                        }
                    }
                    gameField.fillUpGameField();
                    notifyRegisteredObservers(this);
                    break;
            }
        } else {
            throw new HandsNotEmptyException();
        }

    }

    /**
     * Responsible for putting down a cat and communicating this change to the gamefield array.
     * <p>
     * In this method the array gets checked in which direction the character looks and if it possible to put down
     * the cat on the tile or if the character even holds a cat in the first place.
     *
     * @throws WallInFrontException  if the user wants to put down the cat on a tile with a wall on it
     * @throws CatInFrontException   if the user wants to put down the cat on a tile with a cat already on it
     * @throws DrinkInFrontException if the user wants to put down the cat on a tile with a drink on it
     * @throws NoCatInHandException  if the user wants to put down the cat without even having picked up a cat first
     * @since 05.11.2021
     */
    public void putCatDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException, NoCatInHandException {
        if (catInHand) {
            catInHand = false;
            handsFull = false;

            switch (gameField.getCharacter()) {
                case "^":
                    for (int i = 0; i < gameField.getRow() - 1; i++) {
                        for (int j = 0; j < gameField.getColumn() - 1; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals("^")) {
                                switch (gameField.getGameFieldArray()[i - 1][j]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                    case "D":
                                        throw new DrinkInFrontException();
                                }
                                gameField.placeObjectsInGameField(i - 1, j, "C");
                                break;
                            }
                        }
                    }
                    gameField.fillUpGameField();
                    notifyRegisteredObservers(this);
                    break;
                case "v":
                    for (int i = 0; i < gameField.getRow() - 1; i++) {
                        for (int j = 0; j < gameField.getColumn() - 1; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals("v")) {
                                switch (gameField.getGameFieldArray()[i + 1][j]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                    case "D":
                                        throw new DrinkInFrontException();
                                }
                                gameField.placeObjectsInGameField(i + 1, j, "C");
                                break;
                            }
                        }
                    }
                    gameField.fillUpGameField();
                    notifyRegisteredObservers(this);
                    break;
                case ">":
                    for (int i = 0; i < gameField.getRow() - 1; i++) {
                        for (int j = 0; j < gameField.getColumn() - 1; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals(">")) {
                                switch (gameField.getGameFieldArray()[i][j + 1]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                    case "D":
                                        throw new DrinkInFrontException();
                                }
                                gameField.placeObjectsInGameField(i, j + 1, "C");
                                break;
                            }
                        }
                    }
                    gameField.fillUpGameField();
                    notifyRegisteredObservers(this);
                    break;
                case "<":
                    for (int i = 0; i < gameField.getRow() - 1; i++) {
                        for (int j = 0; j < gameField.getColumn() - 1; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals("<")) {
                                switch (gameField.getGameFieldArray()[i][j - 1]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                    case "D":
                                        throw new DrinkInFrontException();
                                }
                                gameField.placeObjectsInGameField(i, j - 1, "C");
                                break;
                            }
                        }
                    }
                    gameField.fillUpGameField();
                    notifyRegisteredObservers(this);
                    break;
            }
        } else {
            throw new NoCatInHandException();
        }
    }

    /**
     * Responsible for putting down a drink and communicating this change to the gamefield array.
     * <p>
     * In this method the array gets checked in which direction the character looks and if it possible to put down
     * the drink on the tile or if the character even holds a drink in the first place.
     *
     * @throws WallInFrontException   if the user wants to put down the drink on a tile with a wall on it
     * @throws CatInFrontException    if the user wants to put down the drink on a tile with a cat on it
     * @throws DrinkInFrontException  if the user wants to put down the drink on a tile with a drink already on it
     * @throws NoDrinkInHandException if the user wants to put down the drink without even having picked up a drink first
     * @since 05.11.2021
     */
    public void putDrinkDown() throws WallInFrontException, CatInFrontException, NoDrinkInHandException {
        if (drinkInHand) {
            drinkInHand = false;
            handsFull = false;

            switch (gameField.getCharacter()) {
                case "^":
                    for (int i = 0; i < gameField.getRow() - 1; i++) {
                        for (int j = 0; j < gameField.getColumn() - 1; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals("^")) {
                                switch (gameField.getGameFieldArray()[i - 1][j]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                }
                                gameField.placeObjectsInGameField(i - 1, j, "D");
                                break;
                            }
                        }
                    }
                    gameField.fillUpGameField();
                    notifyRegisteredObservers(this);
                    break;
                case "v":
                    for (int i = 0; i < gameField.getRow() - 1; i++) {
                        for (int j = 0; j < gameField.getColumn() - 1; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals("v")) {
                                switch (gameField.getGameFieldArray()[i + 1][j]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                }
                                gameField.placeObjectsInGameField(i + 1, j, "D");
                                break;
                            }
                        }
                    }
                    gameField.fillUpGameField();
                    notifyRegisteredObservers(this);
                    break;
                case ">":
                    for (int i = 0; i < gameField.getRow() - 1; i++) {
                        for (int j = 0; j < gameField.getColumn() - 1; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals(">")) {
                                switch (gameField.getGameFieldArray()[i][j + 1]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                }
                                gameField.placeObjectsInGameField(i, j + 1, "D");
                                break;
                            }
                        }
                    }
                    gameField.fillUpGameField();
                    notifyRegisteredObservers(this);
                    break;
                case "<":
                    for (int i = 0; i < gameField.getRow() - 1; i++) {
                        for (int j = 0; j < gameField.getColumn() - 1; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals("<")) {
                                switch (gameField.getGameFieldArray()[i][j - 1]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                }
                                gameField.placeObjectsInGameField(i, j - 1, "D");
                                break;
                            }
                        }
                    }
                    gameField.fillUpGameField();
                    notifyRegisteredObservers(this);
                    break;
            }
        } else {
            throw new NoDrinkInHandException();
        }
    }

    /**
     * checks if the hands the character are currently free or full
     *
     * @return true if hands are free, false if the hands are full
     * @since 05.11.2021
     */
    public boolean handsFree() {
        if (!handsFull) {
            return true;
        }
        return false;
    }

    /**
     * checks if a cat is in front of the direction in which the character currently looks
     *
     * @return true if there IS a cat, false if there is not
     * @since 05.11.2021
     */
    public boolean catThere() {

        switch (gameField.getCharacter()) {
            case "^":
                for (int i = 0; i < gameField.getRow() - 1; i++) {
                    for (int j = 0; j < gameField.getColumn() - 1; j++) {
                        if (gameField.getGameFieldArray()[i - 1][j].equals("C")) {
                            return true;
                        }
                    }
                }
                break;
            case "v":
                for (int i = 0; i < gameField.getRow() - 1; i++) {
                    for (int j = 0; j < gameField.getColumn() - 1; j++) {
                        if (gameField.getGameFieldArray()[i + 1][j].equals("C")) {
                            return true;
                        }
                    }
                }
                break;
            case ">":
                for (int i = 0; i < gameField.getRow() - 1; i++) {
                    for (int j = 0; j < gameField.getColumn() - 1; j++) {
                        if (gameField.getGameFieldArray()[i][j + 1].equals("C")) {
                            return true;
                        }
                    }
                }
                break;
            case "<":
                for (int i = 0; i < gameField.getRow() - 1; i++) {
                    for (int j = 0; j < gameField.getColumn() - 1; j++) {
                        if (gameField.getGameFieldArray()[i][j - 1].equals("C")) {
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }

    /**
     * checks if it's currently possible for the character to step over a cat in front of him
     *
     * @return true if he can step over a cat, because his hands are free, false if he can't because his hands are full
     * @since 05.11.2021
     */
    public boolean stepOverCatPossible() {
        if (handsFull) {
            return false;
        }
        return true;
    }

}
