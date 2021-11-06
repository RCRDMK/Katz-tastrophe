package game;

public class GameCharacter {

    GameField gameField = new GameField();


    public void lookHere(String direction) {
        switch (direction) {
            case "up":
                gameField.character = "^";
            case "down":
                gameField.character = "v";
            case "left":
                gameField.character = "<";
            case "right":
                gameField.character = ">";
                for (int i = 0; i <= gameField.row; i++) {
                    System.out.println(gameField.row);
                    for (int j = 0; j <= gameField.row; j++) {
                        System.out.println(gameField.column);
                        if (gameField.field[i][j].equals("^")) {
                            gameField.field[i][j] = gameField.character;
                        }
                    }
                }
                break;
            default:
                System.out.println("Bitte nur gültige Richtungen wie Up, Down, Left und Right benutzen.");
        }
    }

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
    }*/

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
    }*/

    public void moveRight() throws WallInFrontException, CatInFrontException, DrinkInFrontException {
        for (int i = 0; i < gameField.column; i++) {
            for (int j = 0; j < gameField.column; j++) {
                if (gameField.field[i][j].equals(">")) {
                    gameField.field[i][j] = "x";
                    gameField.placesElementsInField(i, j + 1, ">");
                }
            }
        }
        //gameField.printGameField();
    }

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
    }*/

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

}
