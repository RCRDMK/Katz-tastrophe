package game;

public class GameField {

    static public String[][] gameField;
    public static int row;
    public static int column;
    static String cat = "C";
    static String wall = "W";
    static String drink = "D";
    static String character = "^";

    public GameField(int rows, int columns) {
        row = rows;
        column = columns;
        gameField = new String[row][column];
        printGameField();
    }

    public static void printGameField() {
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

    public static String[][] getGameField() {
        return gameField;
    }

    public void placeElementsInField(int row, int column, String element) {
        gameField[row][column] = element;
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
