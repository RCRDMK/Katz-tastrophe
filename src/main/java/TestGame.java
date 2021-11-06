import game.GameCharacter;
import game.GameField;

public class TestGame {

    public static void main(String[] args) {

        GameField gameField = new GameField();
        GameCharacter character;

        //setting the gamefield. Can't currently be smaller than 5x5.
        // Otherwise a OutOfBoundsException will occur because of the settings in the GameField class
        gameField.setGameField(7, 7);


    }
}
