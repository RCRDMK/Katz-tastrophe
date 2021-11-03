package game;

public class GameCharacter {

    /*void lookHere(){

    }*/

    void moveUp() throws WallInFrontException, CatInFrontException, DrinkInFrontException{

    }

    void moveDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException{

    }

    void moveRight() throws WallInFrontException, CatInFrontException, DrinkInFrontException{

    }

    void moveLeft() throws WallInFrontException, CatInFrontException, DrinkInFrontException{

    }

    void takeCat() throws HandsNotEmptyException{

    }

    void takeDrink() throws HandsNotEmptyException{

    }

    void putCatDown() throws WallInFrontException, CatInFrontException, DrinkInFrontException{

    }

    void putDrinkDown() throws WallInFrontException, CatInFrontException{

    }

    boolean handsFree() throws HandsNotEmptyException{

        return true;
    }

    boolean catThere() throws CatInFrontException{

        return true;
    }

    boolean stepOverCat() throws HandsNotEmptyException, CatInFrontException{

        return true;
    }

}
