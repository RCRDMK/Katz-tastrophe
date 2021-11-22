import game.GameCharacter;
import game.GameField;
import game.exceptions.*;

import java.util.Scanner;

public class TestGame {

    static GameField gameField;
    static GameCharacter character = new GameCharacter();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws WallInFrontException, DrinkInFrontException, CatInFrontException {


        System.out.println("Legende: \nX: ein leeres Feld\nW: eine Wand\nC: eine Katze\nD: Trinken\n^: Der Charakter. Die Pfeilspitze zeigt an, wohin der Charakter gerade schaut");
        System.out.println("Wenn du das Programm schließen möchtest, dann gib 'quit' ein.");
        //setting the gamefield. Can't currently be smaller than 5x5.
        // Otherwise a OutOfBoundsException will occur because of the settings in the GameField class
        //gameField.GameField(7, 7);
        //gameField.printGameField();

        while (true) {
            try {
                String antwort = scan.next().toLowerCase();
                TestGame.commands(antwort);
            } catch (WallInFrontException w) {
                System.out.println("Du kannst nicht auf dem selben Feld wie eine Wand stehen.");
            } catch (EndOfGameFieldException e) {
                System.out.println("Ende des Spielfelds erreicht.");
            } catch (HandsNotEmptyException e) {
                System.out.println("Hände sind nicht leer. Es kann nichts aufgehoben werden.");
            } catch (NoDrinkInHandException e) {
                System.out.println("Kein Trinken in der Hand. Es kann daher auch nicht abgelegt werden.");
            } catch (NoCatInHandException e) {
                System.out.println("Keine Katze in der Hand. Sie kann daher auch nicht abgelegt werden.");
            }

        }


    }

    public static void commands(String command) throws WallInFrontException, DrinkInFrontException, CatInFrontException, EndOfGameFieldException, HandsNotEmptyException, NoCatInHandException, NoDrinkInHandException {
        switch (command) {
            case "hoch":
                character.lookHere("up");
                character.moveUp();
                break;

            case "runter":
                character.lookHere("down");
                character.moveDown();
                break;

            case "links":
                character.lookHere("left");
                character.moveLeft();
                break;

            case "rechts":
                character.lookHere("right");
                character.moveRight();
                break;

            case "katze_nehmen":
                character.takeCat();
                break;

            case "trinken_nehmen":
                character.takeDrink();
                break;

            case "katze_ablegen":
                character.putCatDown();
                break;

            case "trinken_ablegen":
                character.putDrinkDown();
                break;

            case "hilfe":
                System.out.println("Legende: \nX: ein leeres Feld\nW: eine Wand\nC: eine Katze\nD: Trinken\n^: Der Charakter. Die Pfeilspitze zeigt an, wohin der Charakter gerade schaut" +
                        "\nMit hoch, runter, links, rechts steuerst du die Figur und mit Katze (oder Trinken) nehmen oder Katze (oder Trinken) ablegen," +
                        "legst du eine Katze oder Trinken ab,\nsofern du sie in den Händen hälst. Wenn du das Programm schließen möchtest, dann gib 'quit' ein.");
                break;

            case "quit":
                System.out.println("Bis zum nächsten Mal!");
                scan.close();
                System.exit(0);
                break;

            default:
                System.out.println("Bitte nur eine gültige Aktion wie 'hoch', 'runter', 'links', 'rechts' oder 'hilfe' eingeben." +
                        "\nWenn du das Programm schließen möchtest, dann gib 'quit' ein.");
        }
    }
}
