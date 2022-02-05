package model;

import model.exceptions.*;

/**
 * This class is purely being used to forward method calls to its parent class and to abstract the relationship between
 * the files which are being created in the FileController class and the GameCharacter class. The goal of this abstraction is
 * to make it harder for a user to overwrite important values or methods in the GameCharacter class. For documentation
 * of the methods in this class, please refer to the documentation of the parent class GameCharacter. All methods declared
 * here have their origin there.
 *
 * @since 15.12.2021
 */

public class CharaWrapper extends GameCharacter {

    GameField gameField;

    public CharaWrapper(GameField gameField) {
        this.gameField = gameField;
    }

    public CharaWrapper() {

    }

    @Override
    public void moveUp() {
        try {
            super.lookHere("up");
            super.moveUp();
        } catch (WallInFrontException wall) {
            System.out.println("Wall in front");
        } catch (DrinkInFrontException drink) {
            System.out.println("Drink in front");
        } catch (EndOfGameFieldException eogf) {
            System.out.println("End of gamefield reached");
        } catch (CatInFrontException cat) {
            System.out.println("can not step over cat");
        }
    }

    @Override
    public void moveDown() {
        try {
            super.lookHere("down");
            super.moveDown();
        } catch (WallInFrontException wall) {
            System.out.println("Wall in front");
        } catch (DrinkInFrontException drink) {
            System.out.println("Drink in front");
        } catch (EndOfGameFieldException eogf) {
            System.out.println("End of gamefield reached");
        } catch (CatInFrontException cat) {
            System.out.println("can not step over cat");
        }
    }


    @Override
    public void moveRight() {
        try {
            super.lookHere("right");
            super.moveRight();
        } catch (WallInFrontException wall) {
            System.out.println("Wall in front");
        } catch (DrinkInFrontException drink) {
            System.out.println("Drink in front");
        } catch (EndOfGameFieldException eogf) {
            System.out.println("End of gamefield reached");
        } catch (CatInFrontException cat) {
            System.out.println("can not step over cat");
        }
    }


    @Override
    public void moveLeft() {
        try {
            super.lookHere("left");
            super.moveLeft();
        } catch (WallInFrontException wall) {
            System.out.println("Wall in front");
        } catch (DrinkInFrontException drink) {
            System.out.println("Drink in front");
        } catch (EndOfGameFieldException eogf) {
            System.out.println("End of gamefield reached");
        } catch (CatInFrontException cat) {
            System.out.println("can not step over cat");
        }
    }


    @Override
    public void takeCat() {
        try {
            super.takeCat();
        } catch (HandsNotEmptyException hne) {
            System.out.println("Hands are full");
        } catch (CatInFrontException cife) {
            System.out.println("There is no cat to pick up");
        } catch (EndOfGameFieldException eogfe) {
            System.out.println("End of gamefield reached");
        }

    }


    @Override
    public void takeDrink() {
        try {
            super.takeDrink();
        } catch (HandsNotEmptyException hne) {
            System.out.println("Hands are full");
        } catch (DrinkInFrontException dife) {
            System.out.println("There is no drink in front to pick up");
        } catch (EndOfGameFieldException eogfe) {
            System.out.println("End of gamefield reached");
        }
    }


    @Override
    public void putCatDown() {
        try {
            super.putCatDown();
        } catch (WallInFrontException wall) {
            System.out.println("Wall in front");
        } catch (DrinkInFrontException drink) {
            System.out.println("Drink in front");
        } catch (NoCatInHandException ncih) {
            System.out.println("No cat in hand");
        } catch (CatInFrontException cat) {
            System.out.println("can not step over cat");
        } catch (EndOfGameFieldException eogfe) {
            System.out.println("Can't put Cat down outside the gamefield");
        }
    }


    @Override
    public void putDrinkDown() {
        try {
            super.putDrinkDown();
        } catch (WallInFrontException wall) {
            System.out.println("Wall in front");
        } catch (NoDrinkInHandException ndih) {
            System.out.println("No drink in hand");
        } catch (CatInFrontException cat) {
            System.out.println("can not step over cat");
        } catch (EndOfGameFieldException eogfe) {
            System.out.println("can not put drink outside");
        }
    }


    @Override
    public boolean handsFree() {
        return super.handsFree();
    }


    @Override
    public boolean catThere() {
        return super.catThere();
    }


    @Override
    public boolean stepOverCatPossible() {
        return super.stepOverCatPossible();
    }
}
