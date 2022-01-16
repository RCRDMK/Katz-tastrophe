package game;

import pattern.ObservedObject;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This class the gamefield in the form of a multidimensional array and is also responsible for changing said array
 * depending on the action of the user.
 *
 * @since 03.11.2021
 */
public class GameField extends ObservedObject implements Serializable {

    private volatile int row;
    private volatile String[][] gameFieldArray;
    private volatile int column;
    private volatile String cat = "C";
    private volatile String wall = "W";
    private volatile String drink = "D";
    private volatile String character = "^";

    // tip points to where the character is looking. Initially the character looks up (^) but depending on the
    // interaction with the user, the character can also look down (v),look to the right (>) and look the left (<)

    /**
     * The default constructor of the class.
     *
     * @since 03.11.2021
     */
    GameField() {
    }

    /**
     * The custom constructor of the class.
     * <p>
     * It sets the row and columns and creates a new multidimensional array with it.
     *
     * @param rows    the given amount of rows the array should depict
     * @param columns the given amount of columns the array should depict
     * @since 03.11.2021
     */
    public GameField(int rows, int columns) {
        row = rows;
        column = columns;
        gameFieldArray = new String[row][column];
        fillUpGameField();
        notifyRegisteredObservers(this);
    }


    public int getRow() {
        return row;
    }

    public String[][] getGameFieldArray() {
        return gameFieldArray;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
        notifyRegisteredObservers(this);
    }

    public void setRow(int row) {
        this.row = row;
        notifyRegisteredObservers(this);
        checkIfCharacterExists();
    }

    public void setColumn(int column) {
        this.column = column;
        notifyRegisteredObservers(this);
        checkIfCharacterExists();
    }

    /**
     * Responsible for giving every index of the value null the value x. In doing that possible NullPointerExceptions
     * can be circumvented when traversing through the array.
     *
     * @since 03.11.2021
     */
    public void fillUpGameField() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (gameFieldArray[i][j] == null) {
                    gameFieldArray[i][j] = "x";
                }
            }
        }
        notifyRegisteredObservers(this);
    }

    /**
     * Responsible for checking if there still exist a character in the gamefield array. If not, the character is
     * added to the index of [0][0].
     *
     * @since 10.11.2021
     */
    public void checkIfCharacterExists() {
        boolean characterExist = false;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (gameFieldArray[i][j].equals("^")) {
                    characterExist = true;
                    break;
                } else if (gameFieldArray[i][j].equals("v")) {
                    characterExist = true;
                    break;
                } else if (gameFieldArray[i][j].equals("<")) {
                    characterExist = true;
                    break;
                } else if (gameFieldArray[i][j].equals(">")) {
                    characterExist = true;
                    break;
                }
            }
        }
        if (!characterExist) {
            gameFieldArray[0][0] = "^";
        }
        notifyRegisteredObservers(this);
    }

    /**
     * Responsible for placing objects manually in the gamefield array.
     *
     * @param row    the row in which an object should be placed in
     * @param column the column in which an object should be placed in
     * @param object the object which should be placed. Like the character, a cat, etc.
     * @since 03.11.2011
     */
    public void placeObjectsInGameField(int row, int column, String object) {
        gameFieldArray[row][column] = object;
        notifyRegisteredObservers(this);
    }

    /**
     * Responsible for changing the size of the gamefield array.
     * <p>
     * When this method is called, it first clones the current gamefield with all of its values. Afterwards it creates
     * a new gamefield array with the requested amount of column and rows. Lastly, it fills the newly created array
     * with the values of the old array.
     *
     * @param rows    amount of the requested rows the array should depict.
     * @param columns amount of the requested columns the array should depict.
     * @since 21.11.2021
     */
    public void resizeGameFieldSize(int rows, int columns) {
        String copy[][] = Arrays.stream(getGameFieldArray()).map(String[]::clone).toArray(String[][]::new);
        gameFieldArray = new String[rows][columns];
        //TODO rows und columns überdenken
        row = rows;
        column = columns;
        //new GameField(rows, columns);
        if (gameFieldArray.length < copy.length) {
            for (int i = 0; i < gameFieldArray.length; i++) {
                for (int j = 0; j < gameFieldArray[0].length; j++) {
                    gameFieldArray[i][j] = copy[i][j];
                }
            }
        } else {
            for (int i = 0; i < copy.length; i++) {
                for (int j = 0; j < copy[0].length; j++) {
                    gameFieldArray[i][j] = copy[i][j];
                }
            }
            notifyRegisteredObservers(this);
            fillUpGameField();
        }
        notifyRegisteredObservers(this);
    }

    public int getColumn() {
        return column;
    }
}
