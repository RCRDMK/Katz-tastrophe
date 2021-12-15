package game;

import game.exceptions.*;

public class CharaWrapper extends GameCharacter {

    public void moveUp() throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        super.moveUp();
    }

    public void moveDown() throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        super.moveDown();
    }


    public void moveRight() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        super.moveRight();
    }


    public void moveLeft() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        super.moveLeft();
    }


    public void takeCat() throws HandsNotEmptyException {
        super.takeCat();
    }


    public void takeDrink() throws HandsNotEmptyException {
        super.takeDrink();
    }


    public void putCatDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException, NoCatInHandException {
        super.putCatDown();
    }


    public void putDrinkDown() throws WallInFrontException, CatInFrontException, NoDrinkInHandException {
        super.putDrinkDown();
    }


    public boolean handsFree() {
        return super.handsFree();
    }


    public boolean catThere() {
        return super.catThere();
    }


    public boolean stepOverCatPossible() {
        return super.stepOverCatPossible();
    }
}
