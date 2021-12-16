package game;

import game.exceptions.*;

public class CharaWrapper extends GameCharacter {

    @Override
    public void moveUp() throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        super.moveUp();
    }

    @Override
    public void moveDown() throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        super.moveDown();
    }


    @Override
    public void moveRight() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        super.moveRight();
    }


    @Override
    public void moveLeft() throws WallInFrontException, CatInFrontException, DrinkInFrontException, EndOfGameFieldException {
        super.moveLeft();
    }


    @Override
    public void takeCat() throws HandsNotEmptyException {
        super.takeCat();
    }


    @Override
    public void takeDrink() throws HandsNotEmptyException {
        super.takeDrink();
    }


    @Override
    public void putCatDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException, NoCatInHandException {
        super.putCatDown();
    }


    @Override
    public void putDrinkDown() throws WallInFrontException, CatInFrontException, NoDrinkInHandException {
        super.putDrinkDown();
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
