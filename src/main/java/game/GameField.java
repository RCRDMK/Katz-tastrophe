package game;

/**
 * This class the gamefield in the form of a multidimensional array and is also responsible for changing said array
 * depending on the action of the user.
 *
 * @since 03.11.2021
 */
public class GameField {

    static public String[][] gameField;
    public static int row;
    public static int column;
    static String cat = "C";
    static String wall = "W";
    static String drink = "D";
    static String character = "^";

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

    public static String[][] getGameField() {
        return gameField;
    }

    /**
     * Responsible for giving every index of the value null the value x. In doing that possible NullPointerExceptions
     * can be circumvented when traversing through the array.
     *
     * @since 03.11.2021
     */
    public static void fillUpGameField() {
        //responsible for filling indexes which have the value null with an x
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (gameField[i][j] == null) {
                    gameField[i][j] = "x";
                }
            }
            //Hier kann auch ein normaler "toString" ausgeführt werden.
            //Da aber evtl später mit Cells gearbeitet wird, bleibt dieser Befehl als spätere Spickhilfe erhalten
            //System.out.println(Arrays.deepToString(this.gameField[i]));

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

    //should be called when the gamefield sizes changes
    public void checkIfCharacterOutOfBounds() {
        for (int i = 0; i <= row; i++) {
            for (int j = 0; j <= column; j++) {
                if (!gameField[i][j].equals("^")) {
                    gameField[0][0] = "^";
                } else if (!gameField[i][j].equals("v")) {
                    gameField[0][0] = "^";
                } else if (!gameField[i][j].equals("<")) {
                    gameField[0][0] = "^";
                } else if (!gameField[i][j].equals(">")) {
                    gameField[0][0] = "^";
                }
            }
        }
    }
}
