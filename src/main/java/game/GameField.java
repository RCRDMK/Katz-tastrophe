package game;

import java.util.Arrays;

public class GameField {

    static public String[][] field;
    static int row;
    static int column;
    String cat = "C";
    String wall = "W";
    String drink = "D";
    String character = "^";
    //GameField gameField = new GameField();

    public void setGameField(int rows, int columns) {
        row = rows;
        column = columns;
        field = new String[row][column];

        placesElementsInField(5, 5, cat);
        placesElementsInField(3, 1, wall);
        placesElementsInField(3, 2, cat);
        placesElementsInField(4, 1, drink);
        placesElementsInField(3, 3, character);
    }

    void placesElementsInField(int row, int column, String element) {
        field[row][column] = element;
    }

    public void printGameField() {
        //responsible for filling indexes which have the value null with an X
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (field[i][j] == null) {
                    field[i][j] = "x";
                }
            }
            //Hier kann auch ein normaler "toString" ausgeführt werden.
            //Da aber evtl später mit Cells gearbeitet wird, bleibt dieser Befehl als spätere Spickhilfe erhalten
            System.out.println(Arrays.deepToString(this.field[i]));
        }
        System.out.println();
    }

/*

    public void lookHere(String direction) {
        switch (direction) {

            case "right":
                character = ">";
                for (int i = 0; i < row; i++) {

                    for (int j = 0; j < row; j++) {

                        if (field[i][j].equals("^")) {
                            field[i][j] = character;
                        }
                    }
                }
                break;
            default:
                System.out.println("Bitte nur gültige Richtungen wie Up, Down, Left und Right benutzen.");
        }
    }

    */
/*public void moveUp() throws WallInFrontException, CatInFrontException, DrinkInFrontException {
        for (int i = 0; i < gameField.row; i++) {
            for (int j = 0; j < gameField.column; j++) {
                if (gameField.field[i][j] == "^") {
                    gameField.field[i][j] = "X";
                    gameField.field[i - 1][j] = "^";
                } else {
                    System.out.println("Der Charakter kann nur in die Richtung gehen, in die er schaut");
                }
            }
        }
    }*//*


     */
/*public void moveDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException {
        for (int i = 0; i < gameField.row; i++) {
            for (int j = 0; j < gameField.column; j++) {
                if (gameField.field[i][j] == "v") {
                    gameField.field[i][j] = "X";
                    gameField.field[i + 1][j] = "v";
                } else {
                    System.out.println("Der Charakter kann nur in die Richtung gehen, in die er schaut");
                }
            }
        }
    }*//*


    public void moveRight() throws WallInFrontException, CatInFrontException, DrinkInFrontException {
        for (int i = 0; i < row - 1; i++) {
            if (i < row - 1) {
                for (int j = 0; j < column - 1; j++) {
                    if (field[i][j].equals(">")) {
                        field[i][j] = "X";
                        placesElementsInField(i, j + 1, ">");
                        break;
                    }
                }
            } else {
                System.out.println("Der Charakter kann nur in die Richtung gehen, in die er schaut");
            }

        }
        //gameField.printGameField();
    }

    */
/*public void moveLeft() throws WallInFrontException, CatInFrontException, DrinkInFrontException {
        for (int i = 0; i < gameField.row; i++) {
            for (int j = 0; j < gameField.column; j++) {
                if (gameField.field[i][j] == "<") {
                    gameField.field[i][j] = "X";
                    gameField.field[i][j - 1] = "<";
                } else {
                    System.out.println("Der Charakter kann nur in die Richtung gehen, in die er schaut");
                }
            }
        }
    }*//*


    void takeCat() throws HandsNotEmptyException {

    }

    void takeDrink() throws HandsNotEmptyException {

    }

    void putCatDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException {

    }

    void putDrinkDown() throws WallInFrontException, CatInFrontException {

    }

    boolean handsFree() throws HandsNotEmptyException {

        return true;
    }

    boolean catThere() throws CatInFrontException {

        return true;
    }

    boolean stepOverCat() throws HandsNotEmptyException, CatInFrontException {

        return true;
    }
*/

}
