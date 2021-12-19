package game;

import game.exceptions.*;

public class CharaWrapper extends GameCharacter {

    @Override
    public void moveUp() {
        try {
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
        }

    }


    @Override
    public void takeDrink() {
        try {
            super.takeDrink();
        } catch (HandsNotEmptyException hne) {
            System.out.println("Hands are full");
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
