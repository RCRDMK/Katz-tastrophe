package game;

import game.exceptions.*;

public class GameCharacter {

    GameField gameField = new GameField();

    boolean handsFull = false;
    boolean drinkInHand = false;
    boolean catInHand = false;

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
                if (gameField.gameField[i][j].equals("^") || gameField.gameField[i][j].equals("v") || gameField.gameField[i][j].equals("<") || gameField.gameField[i][j].equals(">")) {
                    gameField.gameField[i][j] = gameField.character;
                }
            }
        }
    }

    //TODO Permanenz der Objekte in den Indices beim Bewegen wahren. Es darf keine Katze oder Trinken überschrieben, wenn der Spieler auf dem selben Feld ist

    public void moveUp() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        for (int i = 0; i < gameField.row; i++) {
            for (int j = 0; j < gameField.column; j++) {
                if (gameField.gameField[i][j].equals("^")) {
                    if (i == gameField.row / gameField.row - 1) {
                        throw new EndOfGameFieldException();
                    } else if (gameField.gameField[i - 1][j].equals("W")) {
                        throw new WallInFrontException();
                    } else {
                        gameField.gameField[i][j] = "x";
                        gameField.placesElementsInField(i - 1, j, "^");
                        break;
                    }
                }
            }
        }
        gameField.printGameField();
    }

    public void moveDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        for (int i = 0; i < gameField.row; i++) {
            for (int j = 0; j < gameField.column; j++) {
                if (gameField.gameField[i][j].equals("v")) {
                    if (i + 1 == gameField.row) {
                        throw new EndOfGameFieldException();
                    } else if (gameField.gameField[i + 1][j].equals("W")) {
                        throw new WallInFrontException();
                    } else {
                        gameField.gameField[i][j] = "x";
                        gameField.placesElementsInField(i + 1, j, "v");
                        i = gameField.row - 1;
                        break;
                    }
                }
            }
        }
        gameField.printGameField();
    }

    public void moveRight() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        for (int i = 0; i < gameField.row; i++) {
            for (int j = 0; j < gameField.column; j++) {
                if (gameField.gameField[i][j].equals(">")) {
                    if (j + 1 == gameField.column) {
                        throw new EndOfGameFieldException();
                    } else if (gameField.gameField[i][j + 1].equals("W")) {
                        throw new WallInFrontException();
                    } else {
                        gameField.gameField[i][j] = "x";
                        gameField.placesElementsInField(i, j + 1, ">");
                        break;
                    }
                }
            }
        }

        gameField.printGameField();
    }

    public void moveLeft() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        for (int i = 0; i < gameField.row; i++) {
            for (int j = 0; j < gameField.column; j++) {
                if (gameField.gameField[i][j].equals("<")) {
                    if (j == gameField.column / gameField.column - 1) {
                        throw new EndOfGameFieldException();
                    } else if (gameField.gameField[i][j - 1].equals("W")) {
                        throw new WallInFrontException();
                    } else {
                        gameField.gameField[i][j] = "x";
                        gameField.placesElementsInField(i, j - 1, "<");
                        break;
                    }
                }
            }
        }
        gameField.printGameField();
    }

    //TODO Nehmen und Ablegen auf EndOfGameFieldException überprüfen
    public void takeCat() throws HandsNotEmptyException {
        if (!handsFull) {
            handsFull = true;
            catInHand = true;


            switch (gameField.character) {
                case "^":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals("^")) {
                                gameField.gameField[i - 1][j] = "x";
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
                case "v":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals("v")) {
                                gameField.gameField[i + 1][j] = "x";
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
                case ">":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals(">")) {
                                gameField.gameField[i][j + 1] = "x";
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
                case "<":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals("<")) {
                                gameField.gameField[i][j - 1] = "x";
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
            }
        } else {
            throw new HandsNotEmptyException();
        }

    }

    public void takeDrink() throws HandsNotEmptyException {
        if (!handsFull) {
            handsFull = true;
            drinkInHand = true;

            switch (gameField.character) {
                case "^":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals("^")) {
                                gameField.gameField[i - 1][j] = "x";
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
                case "v":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals("v")) {
                                gameField.gameField[i + 1][j] = "x";
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
                case ">":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals(">")) {
                                gameField.gameField[i][j + 1] = "x";
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
                case "<":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals("<")) {
                                gameField.gameField[i][j - 1] = "x";
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
            }
        } else {
            throw new HandsNotEmptyException();
        }

    }

    public void putCatDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException, NoCatInHandException {
        if (catInHand) {
            catInHand = false;
            handsFull = false;

            switch (gameField.character) {
                case "^":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals("^")) {
                                switch (gameField.gameField[i - 1][j]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                    case "D":
                                        throw new DrinkInFrontException();
                                }
                                gameField.placesElementsInField(i - 1, j, "C");
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
                case "v":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals("v")) {
                                switch (gameField.gameField[i + 1][j]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                    case "D":
                                        throw new DrinkInFrontException();
                                }
                                gameField.placesElementsInField(i + 1, j, "C");
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
                case ">":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals(">")) {
                                switch (gameField.gameField[i][j + 1]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                    case "D":
                                        throw new DrinkInFrontException();
                                }
                                gameField.placesElementsInField(i, j + 1, "C");
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
                case "<":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals("<")) {
                                switch (gameField.gameField[i][j - 1]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                    case "D":
                                        throw new DrinkInFrontException();
                                }
                                gameField.placesElementsInField(i, j - 1, "C");
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
            }
        } else {
            throw new NoCatInHandException();
        }
    }

    public void putDrinkDown() throws WallInFrontException, CatInFrontException, NoDrinkInHandException {
        if (drinkInHand) {
            drinkInHand = false;
            handsFull = false;

            switch (gameField.character) {
                case "^":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals("^")) {
                                switch (gameField.gameField[i - 1][j]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                }
                                gameField.placesElementsInField(i - 1, j, "D");
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
                case "v":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals("v")) {
                                switch (gameField.gameField[i + 1][j]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                }
                                gameField.placesElementsInField(i + 1, j, "D");
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
                case ">":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals(">")) {
                                switch (gameField.gameField[i][j + 1]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                }
                                gameField.placesElementsInField(i, j + 1, "D");
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
                case "<":
                    for (int i = 0; i < gameField.row - 1; i++) {
                        for (int j = 0; j < gameField.column - 1; j++) {
                            if (gameField.gameField[i][j].equals("<")) {
                                switch (gameField.gameField[i][j - 1]) {
                                    case "W":
                                        throw new WallInFrontException();
                                    case "C":
                                        throw new CatInFrontException();
                                }
                                gameField.placesElementsInField(i, j - 1, "D");
                                break;
                            }
                        }
                    }
                    gameField.printGameField();
                    break;
            }
        } else {
            throw new NoDrinkInHandException();
        }
    }

    public boolean handsFree() {
        if (!handsFull) {
            return true;
        }
        return false;
    }

    public boolean catThere() {

        switch (gameField.character) {
            case "^":
                for (int i = 0; i < gameField.row - 1; i++) {
                    for (int j = 0; j < gameField.column - 1; j++) {
                        if (gameField.gameField[i - 1][j].equals("C")) {
                            return true;
                        }
                    }
                }
                break;
            case "v":
                for (int i = 0; i < gameField.row - 1; i++) {
                    for (int j = 0; j < gameField.column - 1; j++) {
                        if (gameField.gameField[i + 1][j].equals("C")) {
                            return true;
                        }
                    }
                }
                break;
            case ">":
                for (int i = 0; i < gameField.row - 1; i++) {
                    for (int j = 0; j < gameField.column - 1; j++) {
                        if (gameField.gameField[i][j + 1].equals("C")) {
                            return true;
                        }
                    }
                }
                break;
            case "<":
                for (int i = 0; i < gameField.row - 1; i++) {
                    for (int j = 0; j < gameField.column - 1; j++) {
                        if (gameField.gameField[i][j - 1].equals("C")) {
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }

    public boolean stepOverCatPossible() {
        if (handsFull) {
            return false;
        }
        return true;
    }

}
