import game.*;

public class TestGame {

    public static void main(String[] args) throws WallInFrontException, DrinkInFrontException, CatInFrontException {

        GameField gameField = new GameField();
        GameCharacter character = new GameCharacter();


        System.out.println("Legende: \nX: ein leeres Feld\nW: eine Wand\nC: eine Katze\nD: Trinken\n^: Der Charakter. Die Pfeilspitze zeigt an, wohin der Charakter gerade schaut");
        //setting the gamefield. Can't currently be smaller than 5x5.
        // Otherwise a OutOfBoundsException will occur because of the settings in the GameField class
        gameField.setGameField(7, 7);
        gameField.printGameField();

        character.lookHere("right");
        gameField.printGameField();
        character.moveRight();


    }
}
