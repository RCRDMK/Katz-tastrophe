package game;

import java.util.Arrays;

public class GameField {

    int row;
    int column;
    String[][] field;

    String cat = "C";
    String wall = "W";
    String drink = "D";
    String character = "^";


    public void setGameField(int row, int column) {
        this.row = row;
        this.column = column;
        this.field = new String[row][column];

        placesElementsInField();
    }

    void placesElementsInField() {
        field[5][5] = cat;
        field[3][1] = wall;
        field[3][2] = cat;
        field[4][1] = drink;
        field[3][3] = character;

        //responsible for filling indexes which have the value null with an X
        for (int i = 1; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (field[i][j] == null) {
                    field[i][j] = "X";
                }
            }
            //Hier kann auch ein normaler "toString" ausgeführt werden.
            //Da aber evtl später mit Cells gearbeitet wird, bleibt dieser Befehl als spätere Spickhilfe erhalten
            System.out.println(Arrays.deepToString(this.field[i]));
        }
    }

}
