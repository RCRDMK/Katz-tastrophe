package model;

import model.exceptions.*;
import model.pattern.ObservedObject;

import java.io.Serializable;

/**
 * This class is responsible handling every action the game character can do.
 *
 * @since 03.11.2021
 */

public class GameCharacter extends ObservedObject implements Serializable {

    private volatile GameField gameField;

    private volatile boolean handsFull = false;
    private volatile boolean drinkInHand = false;
    private volatile boolean catInHand = false;


    public GameCharacter() {

    }

    /**
     * Main method of the GameCharacter class. This method will be overwritten, once a class will be compiled. Inside of
     * it will be the code, written by the user, which will be then executed through the run method in the Simulator class.
     *
     * @see controller.Simulation
     * @since 15.01.2022
     */
    public void main() {

    }

    public synchronized void setGameCharacter(GameField gameField, GameCharacter gameCharacter) {
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
    public synchronized void lookHere(String direction) throws InvalidDirectionException {
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
                throw new InvalidDirectionException();
        }
    }

    /**
     * Responsible for changing the direction the character looks in the gamefield array
     *
     * @since 03.11.2021
     */
    private synchronized void changeCharacterView() {
        for (int i = 0; i <= gameField.getRow() - 1; i++) {
            for (int j = 0; j <= gameField.getColumn() - 1; j++) {
                if (gameField.getGameFieldArray()[i][j].equals("^") || gameField.getGameFieldArray()[i][j].equals("v") || gameField.getGameFieldArray()[i][j].equals("<") || gameField.getGameFieldArray()[i][j].equals(">")) {
                    gameField.getGameFieldArray()[i][j] = gameField.getCharacter();
                }
            }
        }
        notifyRegisteredObservers(this);
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

    public synchronized void moveUp() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        for (int i = 0; i < gameField.getRow(); i++) {
            for (int j = 0; j < gameField.getColumn(); j++) {
                if (gameField.getGameFieldArray()[i][j].equals("C^") || gameField.getGameFieldArray()[i][j].equals("Cv") || gameField.getGameFieldArray()[i][j].equals("C>") || gameField.getGameFieldArray()[i][j].equals("C<")) {
                    if (gameField.getGameFieldArray()[i - 1][j].equals("W")) {
                        throw new WallInFrontException();
                    } else if (gameField.getGameFieldArray()[i - 1][j].equals("D")) {
                        throw new DrinkInFrontException();
                    }
                    gameField.getGameFieldArray()[i][j] = "C";
                    gameField.placeObjectsInGameField(i - 1, j, "^");
                    break;
                }

                if (gameField.getGameFieldArray()[i][j].equals("^")) {//checks what is above the character to determine his action
                    if (i == gameField.getRow() / gameField.getRow() - 1) {
                        throw new EndOfGameFieldException();
                    } else if (gameField.getGameFieldArray()[i - 1][j].equals("W")) {
                        throw new WallInFrontException();
                    } else if (gameField.getGameFieldArray()[i - 1][j].equals("D")) {
                        throw new DrinkInFrontException();
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
    public synchronized void moveDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        for (int i = 0; i < gameField.getRow(); i++) {
            for (int j = 0; j < gameField.getColumn(); j++) {
                if (gameField.getGameFieldArray()[i][j].equals("C^") || gameField.getGameFieldArray()[i][j].equals("Cv") || gameField.getGameFieldArray()[i][j].equals("C>") || gameField.getGameFieldArray()[i][j].equals("C<")) {
                    if (gameField.getGameFieldArray()[i + 1][j].equals("W")) {
                        throw new WallInFrontException();
                    } else if (gameField.getGameFieldArray()[i + 1][j].equals("D")) {
                        throw new DrinkInFrontException();
                    }
                    gameField.getGameFieldArray()[i][j] = "C";
                    gameField.placeObjectsInGameField(i + 1, j, "v");
                    i = gameField.getRow() - 1;
                    break;
                }

                if (gameField.getGameFieldArray()[i][j].equals("v")) {//checks what is below the character to determine his action
                    if (i + 1 == gameField.getRow()) {
                        throw new EndOfGameFieldException();
                    } else if (gameField.getGameFieldArray()[i + 1][j].equals("W")) {
                        throw new WallInFrontException();
                    } else if (gameField.getGameFieldArray()[i + 1][j].equals("D")) {
                        throw new DrinkInFrontException();
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
    public synchronized void moveRight() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        for (int i = 0; i < gameField.getRow(); i++) {
            for (int j = 0; j < gameField.getColumn(); j++) {
                if (gameField.getGameFieldArray()[i][j].equals("C^") || gameField.getGameFieldArray()[i][j].equals("Cv") || gameField.getGameFieldArray()[i][j].equals("C>") || gameField.getGameFieldArray()[i][j].equals("C<")) {
                    if (gameField.getGameFieldArray()[i][j + 1].equals("W")) {
                        throw new WallInFrontException();
                    } else if (gameField.getGameFieldArray()[i][j + 1].equals("D")) {
                        throw new DrinkInFrontException();
                    }
                    gameField.getGameFieldArray()[i][j] = "C";
                    gameField.placeObjectsInGameField(i, j + 1, ">");
                    break;
                }

                if (gameField.getGameFieldArray()[i][j].equals(">")) {//checks what is to the right of the character to determine his action
                    if (j + 1 == gameField.getColumn()) {
                        throw new EndOfGameFieldException();
                    } else if (gameField.getGameFieldArray()[i][j + 1].equals("W")) {
                        throw new WallInFrontException();
                    } else if (gameField.getGameFieldArray()[i][j + 1].equals("D")) {
                        throw new DrinkInFrontException();
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
    public synchronized void moveLeft() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        for (int i = 0; i < gameField.getRow(); i++) {
            for (int j = 0; j < gameField.getColumn(); j++) {
                if (gameField.getGameFieldArray()[i][j].equals("C^") || gameField.getGameFieldArray()[i][j].equals("Cv") || gameField.getGameFieldArray()[i][j].equals("C>") || gameField.getGameFieldArray()[i][j].equals("C<")) {
                    if (gameField.getGameFieldArray()[i][j - 1].equals("W")) {
                        throw new WallInFrontException();
                    } else if (gameField.getGameFieldArray()[i][j - 1].equals("D")) {
                        throw new DrinkInFrontException();
                    }
                    gameField.getGameFieldArray()[i][j] = "C";
                    gameField.placeObjectsInGameField(i, j - 1, "<");
                    break;
                }

                if (gameField.getGameFieldArray()[i][j].equals("<")) {//checks what is to the left of the character to determine his action
                    if (j == gameField.getColumn() / gameField.getColumn() - 1) {
                        throw new EndOfGameFieldException();
                    } else if (gameField.getGameFieldArray()[i][j - 1].equals("W")) {
                        throw new WallInFrontException();
                    } else if (gameField.getGameFieldArray()[i][j - 1].equals("D")) {
                        throw new DrinkInFrontException();
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
    }

    /**
     * Responsible for taking a cat from a tile and communicating this change to the gamefield array.
     * <p>
     * In this method the array gets checked in which direction the character looks, if the character is even able
     * to pick up a cat at the moment and if he is, if there's a cat in front of him to pick up.
     *
     * @throws HandsNotEmptyException  If the user wants to pick up a cat even though the hands of the character are not empty at the moment.
     * @throws CatInFrontException     If the user wants to pick up a cat even though there is no cat in front of him to pick up.
     * @throws EndOfGameFieldException If the user wants to pick up a cat even though he already reached the end of the game field.
     * @since 05.11.2011
     */
    public synchronized void takeCat() throws HandsNotEmptyException, CatInFrontException, EndOfGameFieldException {
        if (!handsFull) {
            boolean catWasInFront = false;

            try {
                switch (gameField.getCharacter()) {
                    case "^":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
                                if (gameField.getGameFieldArray()[i][j].equals("^") && gameField.getGameFieldArray()[i - 1][j].equals("C")) {
                                    gameField.getGameFieldArray()[i - 1][j] = "x";
                                    handsFull = true;
                                    catInHand = true;
                                    catWasInFront = true;
                                    break;
                                }
                            }
                        }
                        if (!catWasInFront) {
                            throw new CatInFrontException();
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case "v":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
                                if (gameField.getGameFieldArray()[i][j].equals("v") && gameField.getGameFieldArray()[i + 1][j].equals("C")) {
                                    gameField.getGameFieldArray()[i + 1][j] = "x";
                                    handsFull = true;
                                    catInHand = true;
                                    catWasInFront = true;
                                    break;
                                }
                            }
                        }
                        if (!catWasInFront) {
                            throw new CatInFrontException();
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case ">":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
                                if (gameField.getGameFieldArray()[i][j].equals(">") && gameField.getGameFieldArray()[i][j + 1].equals("C")) {
                                    gameField.getGameFieldArray()[i][j + 1] = "x";
                                    handsFull = true;
                                    catInHand = true;
                                    catWasInFront = true;
                                    break;
                                }
                            }
                        }
                        if (!catWasInFront) {
                            throw new CatInFrontException();
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case "<":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
                                if (gameField.getGameFieldArray()[i][j].equals("<") && gameField.getGameFieldArray()[i][j - 1].equals("C")) {
                                    gameField.getGameFieldArray()[i][j - 1] = "x";
                                    handsFull = true;
                                    catInHand = true;
                                    catWasInFront = true;
                                    break;
                                }
                            }
                        }

                        if (!catWasInFront) {
                            throw new CatInFrontException();
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new EndOfGameFieldException();
            }
        } else {
            throw new HandsNotEmptyException();
        }
    }

    /**
     * Responsible for taking a drink from a tile and communicating this change to the gamefield array.
     * <p>
     * In this method the array gets checked in which direction the character looks and if the character is even able
     * to pick up a drink at the moment.
     *
     * @throws HandsNotEmptyException  If the user wants to pick up a drink even though the hands of the character are not empty at the moment.
     * @throws DrinkInFrontException   If the user wants to pick up a drink even though there is no drink in front of him to pick up.
     * @throws EndOfGameFieldException If the user wants to pick up a drink even though he already reached the end of the game field.
     * @since 05.11.2011
     */
    public synchronized void takeDrink() throws HandsNotEmptyException, DrinkInFrontException, EndOfGameFieldException {
        if (!handsFull) {
            boolean drinkWasInFront = false;

            try {
                switch (gameField.getCharacter()) {
                    case "^":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
                                if (gameField.getGameFieldArray()[i][j].equals("^") && gameField.getGameFieldArray()[i - 1][j].equals("D")) {
                                    gameField.getGameFieldArray()[i - 1][j] = "x";
                                    handsFull = true;
                                    drinkInHand = true;
                                    drinkWasInFront = true;
                                    break;
                                }
                            }
                        }
                        if (!drinkWasInFront) {
                            throw new DrinkInFrontException();
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case "v":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
                                if (gameField.getGameFieldArray()[i][j].equals("v") && gameField.getGameFieldArray()[i + 1][j].equals("D")) {
                                    gameField.getGameFieldArray()[i + 1][j] = "x";
                                    handsFull = true;
                                    drinkInHand = true;
                                    drinkWasInFront = true;
                                    break;
                                }
                            }
                        }
                        if (!drinkWasInFront) {
                            throw new DrinkInFrontException();
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case ">":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
                                if (gameField.getGameFieldArray()[i][j].equals(">") && gameField.getGameFieldArray()[i][j + 1].equals("D")) {
                                    gameField.getGameFieldArray()[i][j + 1] = "x";
                                    handsFull = true;
                                    drinkInHand = true;
                                    drinkWasInFront = true;
                                    break;
                                }
                            }
                        }
                        if (!drinkWasInFront) {
                            throw new DrinkInFrontException();
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case "<":
                        for (int i = 0; i < gameField.getRow() - 1; i++) {
                            for (int j = 0; j < gameField.getColumn() - 1; j++) {
                                if (gameField.getGameFieldArray()[i][j].equals("<") && gameField.getGameFieldArray()[i][j - 1].equals("D")) {
                                    gameField.getGameFieldArray()[i][j - 1] = "x";
                                    handsFull = true;
                                    drinkInHand = true;
                                    drinkWasInFront = true;
                                    break;
                                }
                            }
                        }
                        if (!drinkWasInFront) {
                            throw new DrinkInFrontException();
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new EndOfGameFieldException();
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
    public synchronized void putCatDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException, NoCatInHandException, EndOfGameFieldException {
        try {
            if (catInHand) {
                switch (gameField.getCharacter()) {
                    case "^":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
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
                                    catInHand = false;
                                    handsFull = false;
                                    break;
                                }
                            }
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case "v":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
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
                                    catInHand = false;
                                    handsFull = false;
                                    break;
                                }
                            }
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case ">":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
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
                                    catInHand = false;
                                    handsFull = false;
                                    break;
                                }
                            }
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case "<":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
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
                                    catInHand = false;
                                    handsFull = false;
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
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new EndOfGameFieldException();
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
    public synchronized void putDrinkDown() throws WallInFrontException, CatInFrontException, NoDrinkInHandException, EndOfGameFieldException {
        try {
            if (drinkInHand) {

                switch (gameField.getCharacter()) {
                    case "^":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
                                if (gameField.getGameFieldArray()[i][j].equals("^")) {
                                    switch (gameField.getGameFieldArray()[i - 1][j]) {
                                        case "W":
                                            throw new WallInFrontException();
                                        case "C":
                                            throw new CatInFrontException();
                                    }
                                    gameField.placeObjectsInGameField(i - 1, j, "D");
                                    drinkInHand = false;
                                    handsFull = false;
                                    break;
                                }
                            }
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case "v":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
                                if (gameField.getGameFieldArray()[i][j].equals("v")) {
                                    switch (gameField.getGameFieldArray()[i + 1][j]) {
                                        case "W":
                                            throw new WallInFrontException();
                                        case "C":
                                            throw new CatInFrontException();
                                    }
                                    gameField.placeObjectsInGameField(i + 1, j, "D");
                                    drinkInHand = false;
                                    handsFull = false;
                                    break;
                                }
                            }
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case ">":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
                                if (gameField.getGameFieldArray()[i][j].equals(">")) {
                                    switch (gameField.getGameFieldArray()[i][j + 1]) {
                                        case "W":
                                            throw new WallInFrontException();
                                        case "C":
                                            throw new CatInFrontException();
                                    }
                                    gameField.placeObjectsInGameField(i, j + 1, "D");
                                    drinkInHand = false;
                                    handsFull = false;
                                    break;
                                }
                            }
                        }
                        gameField.fillUpGameField();
                        notifyRegisteredObservers(this);
                        break;
                    case "<":
                        for (int i = 0; i < gameField.getRow(); i++) {
                            for (int j = 0; j < gameField.getColumn(); j++) {
                                if (gameField.getGameFieldArray()[i][j].equals("<")) {
                                    switch (gameField.getGameFieldArray()[i][j - 1]) {
                                        case "W":
                                            throw new WallInFrontException();
                                        case "C":
                                            throw new CatInFrontException();
                                    }
                                    gameField.placeObjectsInGameField(i, j - 1, "D");
                                    drinkInHand = false;
                                    handsFull = false;
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
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new EndOfGameFieldException();
        }
    }

    /**
     * checks if the hands of the character are currently free or full
     *
     * @return true if hands are free, false if the hands are full
     * @since 05.11.2021
     */
    public synchronized boolean handsFree() {
        if (!handsFull) {
            return true;
        }
        return false;
    }

    /**
     * checks if a cat is in front of the direction in which the character currently is looking
     *
     * @return true if there IS a cat, false if there is not
     * @since 05.11.2021
     */
    public synchronized boolean catThere() {

        switch (gameField.getCharacter()) {
            case "^":
                for (int i = 0; i < gameField.getRow() - 1; i++) {
                    for (int j = 0; j < gameField.getColumn() - 1; j++) {
                        if (gameField.getGameFieldArray()[i][j].equals("^")) {
                            if (gameField.getGameFieldArray()[i - 1][j].equals("C")) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case "v":
                for (int i = 0; i < gameField.getRow() - 1; i++) {
                    for (int j = 0; j < gameField.getColumn() - 1; j++) {
                        if (gameField.getGameFieldArray()[i][j].equals("v")) {
                            if (gameField.getGameFieldArray()[i + 1][j].equals("C")) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case ">":
                for (int i = 0; i < gameField.getRow() - 1; i++) {
                    for (int j = 0; j < gameField.getColumn() - 1; j++) {
                        if (gameField.getGameFieldArray()[i][j].equals(">")) {
                            if (gameField.getGameFieldArray()[i][j + 1].equals("C")) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case "<":
                for (int i = 0; i < gameField.getRow() - 1; i++) {
                    for (int j = 0; j < gameField.getColumn() - 1; j++) {
                        if (gameField.getGameFieldArray()[i][j].equals("<")) {
                            if (gameField.getGameFieldArray()[i][j].equals("C")) {
                                return true;
                            }
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
     * @return true if he can step over a cat, because his hands are free, false if he can't, because his hands are full
     * @since 05.11.2021
     */
    public synchronized boolean stepOverCatPossible() {
        if (handsFull) {
            return false;
        }
        return true;
    }

    public synchronized boolean isDrinkInHand() {
        return drinkInHand;
    }

    public synchronized boolean isCatInHand() {
        return catInHand;
    }

    public synchronized void setHandsFull(boolean handsFull) {
        this.handsFull = handsFull;
    }

    public synchronized void setDrinkInHand(boolean drinkInHand) {
        this.drinkInHand = drinkInHand;
    }

    public synchronized void setCatInHand(boolean catInHand) {
        this.catInHand = catInHand;
    }
}
