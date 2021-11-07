package game;

public class GameCharacter {

    GameField gameField = new GameField();


    public void lookHere(String direction) {
        switch (direction) {
            case "up":
                gameField.character = "^";
                changeCharacterView();
                break;
            case "down":
                gameField.character = "v";
                changeCharacterView();
                break;
            case "left":
                gameField.character = "<";
                changeCharacterView();
                break;
            case "right":
                gameField.character = ">";
                changeCharacterView();
                break;
            default:
                System.out.println("Bitte nur gültige Richtungen wie Up, Down, Left und Right benutzen.");
        }
    }

    private void changeCharacterView() {
        for (int i = 0; i <= GameField.row - 1; i++) {
            for (int j = 0; j <= gameField.column - 1; j++) {
                if (gameField.field[i][j].equals("^") || gameField.field[i][j].equals("v") || gameField.field[i][j].equals("<") || gameField.field[i][j].equals(">")) {
                    gameField.field[i][j] = gameField.character;
                }
            }
        }
    }

    public void moveUp() throws WallInFrontException, CatInFrontException, DrinkInFrontException {
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
        gameField.printGameField();
    }

    public void moveDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException {
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
        gameField.printGameField();
    }

    public void moveRight() throws WallInFrontException, CatInFrontException, DrinkInFrontException {
        for (int i = 0; i < gameField.row - 1; i++) {
            for (int j = 0; j < gameField.column - 1; j++) {
                if (gameField.field[i][j].equals(">")) {
                    gameField.field[i][j] = "x";
                    gameField.placesElementsInField(i, j + 1, ">");
                    break;
                }
            }
        }
        gameField.printGameField();
    }

    public void moveLeft() throws WallInFrontException, CatInFrontException, DrinkInFrontException {
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
        gameField.printGameField();
    }

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
