package game;

import java.util.Arrays;

/**
 * This class the gamefield in the form of a multidimensional array and is also responsible for changing said array
 * depending on the action of the user.
 *
 * @since 03.11.2021
 */
public class GameField {

    private static GameFieldPanel gameFieldPanel;

    private int row;
    private String[][] gameField;
    private int column;
    private String cat = "C";
    private String wall = "W";
    private String drink = "D";
    private String character = "^";

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
        gameField = new String[row][column];
        fillUpGameField();
    }

    public static void setGameFieldPanel(GameFieldPanel gameFieldPanel) {
        GameField.gameFieldPanel = gameFieldPanel;
    }

    public int getRow() {
        return row;
    }

    public String[][] getGameField() {
        return gameField;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
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
                if (gameField[i][j] == null) {
                    gameField[i][j] = "x";
                }
            }
        }
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
                if (gameField[i][j].equals("^")) {
                    characterExist = true;
                    break;
                } else if (gameField[i][j].equals("v")) {
                    characterExist = true;
                    break;
                } else if (gameField[i][j].equals("<")) {
                    characterExist = true;
                    break;
                } else if (gameField[i][j].equals(">")) {
                    characterExist = true;
                    break;
                }
            }
        }
        if (!characterExist) {
            gameField[0][0] = "^";
        }
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
        gameField[row][column] = object;
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
        String copy[][] = Arrays.stream(getGameField()).map(String[]::clone).toArray(String[][]::new);
        new GameField(rows, columns);
        if (gameField.length < copy.length) {
            for (int i = 0; i < gameField.length; i++) {
                for (int j = 0; j < gameField[0].length; j++) {
                    gameField[i][j] = copy[i][j];
                }
            }
        } else {
            for (int i = 0; i < copy.length; i++) {
                for (int j = 0; j < copy[0].length; j++) {
                    gameField[i][j] = copy[i][j];
                }
            }
            gameFieldPanel.calculateTileHeightAndWidth();
            fillUpGameField();
        }
    }

    public int getColumn() {
        return column;
    }
}
