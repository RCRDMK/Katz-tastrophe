package game;

import java.util.Arrays;

public class GameField {

    static public String[][] gameField;
    static int row;
    static int column;
    String cat = "C";
    String wall = "W";
    String drink = "D";
    String character = "^";

    public void GameField(int rows, int columns) {
        row = rows;
        column = columns;
        gameField = new String[row][column];

        placesElementsInField(5, 5, cat);
        placesElementsInField(3, 1, wall);
        placesElementsInField(3, 2, cat);
        placesElementsInField(4, 1, drink);
        placesElementsInField(3, 3, character);
    }

    void placesElementsInField(int row, int column, String element) {
        gameField[row][column] = element;
    }

    public void printGameField() {
        //responsible for filling indexes which have the value null with an x
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (gameField[i][j] == null) {
                    gameField[i][j] = "x";
                }
            }
            //Hier kann auch ein normaler "toString" ausgeführt werden.
            //Da aber evtl später mit Cells gearbeitet wird, bleibt dieser Befehl als spätere Spickhilfe erhalten
            System.out.println(Arrays.deepToString(this.gameField[i]));

        }
        System.out.println("Bewege dich mit Richtungsanweisungen wie 'hoch', 'runter', 'links', 'rechts' oder rufe die Hilfe mit 'hilfe' auf.");
        System.out.println();
    }
}
