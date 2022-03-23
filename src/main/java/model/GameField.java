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

package model;

import model.pattern.ObservedObject;

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
    private volatile String character = ">";

    // tip points to where the character is looking. Initially the character looks up (^) but depending on the
    // interaction with the user, the character can also look down (v),look to the right (>) and look the left (<)


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


    public synchronized int getRow() {
        return row;
    }

    public synchronized String[][] getGameFieldArray() {
        return gameFieldArray;
    }

    public synchronized String getCharacter() {
        return character;
    }

    public synchronized void setCharacter(String character) {
        this.character = character;
        notifyRegisteredObservers(this);
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
            gameFieldArray[0][0] = ">";
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
     * with the values of the old array. If the newly created array is bigger than the old one, it overwrites all null
     * values with the value "x", showing that there is no object on this field.
     *
     * @param rows    amount of the requested rows the array should depict.
     * @param columns amount of the requested columns the array should depict.
     * @since 21.11.2021
     */
    public void resizeGameFieldSize(int rows, int columns) {
        String[][] copy = Arrays.stream(getGameFieldArray()).map(String[]::clone).toArray(String[][]::new);
        gameFieldArray = new String[rows][columns];
        row = rows;
        column = columns;
        //copying the values from the old array into the new one
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

            //checks if there are any null values in the new array
            for (int i = 0; i < gameFieldArray.length; i++) {
                for (int j = 0; j < gameFieldArray[0].length; j++) {
                    if (gameFieldArray[i][j] == null) {
                        gameFieldArray[i][j] = "x";
                    }
                }
            }
            notifyRegisteredObservers(this);
            fillUpGameField();
        }
        notifyRegisteredObservers(this);
    }

    public synchronized int getColumn() {
        return column;
    }
}
