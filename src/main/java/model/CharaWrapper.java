package model;

import controller.AlertController;
import javafx.scene.control.Alert;
import model.exceptions.*;

import java.io.StringWriter;

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

    private GameField gameField;
    private StringWriter errorMessage = new StringWriter();
    private AlertController alertController = new AlertController();

    public CharaWrapper(GameField gameField) {
        this.gameField = gameField;
    }

    public CharaWrapper() {

    }

    @Override
    public void lookHere(String direction) {
        try {
            super.lookHere(direction);
        } catch (InvalidDirectionException directionException) {
            alertController.userAlert(Alert.AlertType.ERROR, "Invalid direction to look to", "Invalid direction for the character to look to. Please use only 'up', 'down', 'right' and 'left'.");
        }
    }

    @Override
    public void moveUp() {
        try {
            lookHere("up");
            super.moveUp();

        /*} catch (Throwable t) {
            //Uncomment these lines if you want to give the user a more comprehensive alert about the error that has just occurred
            t.printStackTrace(new PrintWriter(errorMessage));
            alertController.userAlert(Alert.AlertType.ERROR, "An error has occurred", String.valueOf(errorMessage));*/

        } catch (WallInFrontException wall) {
            alertController.userAlert(Alert.AlertType.ERROR, "Wall in front", "Can't move further, there is a wall in front.");
        } catch (DrinkInFrontException drink) {
            alertController.userAlert(Alert.AlertType.ERROR, "Drink in front", "Can't move further, there is a drink in front. Either pick it up or walk around it.");
        } catch (EndOfGameFieldException endOfGamefield) {
            alertController.userAlert(Alert.AlertType.ERROR, "Reached end of gamefield", "Can't move further, already reached the end of the gamefield.");
        } catch (CatInFrontException cat) {
            alertController.userAlert(Alert.AlertType.ERROR, "Can't step over cat", "Can't step over the cat in front. Either put things out of your hands or walk around the cat.");
        }
    }

    @Override
    public void moveDown() {
        try {
            lookHere("down");
            super.moveDown();

            /*} catch (Throwable t) {
            //Uncomment these lines if you want to give the user a more comprehensive alert about the error that has just occurred
            t.printStackTrace(new PrintWriter(errorMessage));
            alertController.userAlert(Alert.AlertType.ERROR, "An error has occurred", String.valueOf(errorMessage));*/

        } catch (WallInFrontException wall) {
            alertController.userAlert(Alert.AlertType.ERROR, "Wall in front", "Can't move further, there is a wall in front.");
        } catch (DrinkInFrontException drink) {
            alertController.userAlert(Alert.AlertType.ERROR, "Drink in front", "Can't move further, there is a drink in front. Either pick it up or walk around it.");
        } catch (EndOfGameFieldException endOfGamefield) {
            alertController.userAlert(Alert.AlertType.ERROR, "Reached end of gamefield", "Can't move further, already reached the end of the gamefield.");
        } catch (CatInFrontException cat) {
            alertController.userAlert(Alert.AlertType.ERROR, "Can't step over cat", "Can't step over the cat in front. Either put things out of your hands or walk around the cat.");
        }
    }


    @Override
    public void moveRight() {
        try {
            lookHere("right");
            super.moveRight();

            /*} catch (Throwable t) {
            //Uncomment these lines if you want to give the user a more comprehensive alert about the error that has just occurred
            t.printStackTrace(new PrintWriter(errorMessage));
            alertController.userAlert(Alert.AlertType.ERROR, "An error has occurred", String.valueOf(errorMessage));*/

        } catch (WallInFrontException wall) {
            alertController.userAlert(Alert.AlertType.ERROR, "Wall in front", "Can't move further, there is a wall in front.");
        } catch (DrinkInFrontException drink) {
            alertController.userAlert(Alert.AlertType.ERROR, "Drink in front", "Can't move further, there is a drink in front. Either pick it up or walk around it.");
        } catch (EndOfGameFieldException endOfGamefield) {
            alertController.userAlert(Alert.AlertType.ERROR, "Reached end of gamefield", "Can't move further, already reached the end of the gamefield.");
        } catch (CatInFrontException cat) {
            alertController.userAlert(Alert.AlertType.ERROR, "Can't step over cat", "Can't step over the cat in front. Either put things out of your hands or walk around the cat.");
        }
    }


    @Override
    public void moveLeft() {
        try {
            lookHere("left");
            super.moveLeft();

            /*} catch (Throwable t) {
            //Uncomment these lines if you want to give the user a more comprehensive alert about the error that has just occurred
            t.printStackTrace(new PrintWriter(errorMessage));
            alertController.userAlert(Alert.AlertType.ERROR, "An error has occurred", String.valueOf(errorMessage));*/

        } catch (WallInFrontException wall) {
            alertController.userAlert(Alert.AlertType.ERROR, "Wall in front", "Can't move further, there is a wall in front.");
        } catch (DrinkInFrontException drink) {
            alertController.userAlert(Alert.AlertType.ERROR, "Drink in front", "Can't move further, there is a drink in front. Either pick it up or walk around it.");
        } catch (EndOfGameFieldException endOfGamefield) {
            alertController.userAlert(Alert.AlertType.ERROR, "Reached end of gamefield", "Can't move further, already reached the end of the gamefield.");
        } catch (CatInFrontException cat) {
            alertController.userAlert(Alert.AlertType.ERROR, "Can't step over cat", "Can't step over the cat in front. Either put things out of your hands or walk around the cat.");
        }
    }


    @Override
    public void takeCat() {
        try {
            super.takeCat();

            /*} catch (Throwable t) {
            //Uncomment these lines if you want to give the user a more comprehensive alert about the error that has just occurred
            t.printStackTrace(new PrintWriter(errorMessage));
            alertController.userAlert(Alert.AlertType.ERROR, "An error has occurred", String.valueOf(errorMessage));*/

        } catch (HandsNotEmptyException hne) {
            alertController.userAlert(Alert.AlertType.ERROR, "Hands are full", "Can't pick cat up, hands are already full. Please put something down before picking cat up.");
        } catch (CatInFrontException cife) {
            alertController.userAlert(Alert.AlertType.ERROR, "Not cat to pick up", "There is no cat to pick up in front");
        } catch (EndOfGameFieldException endOfGamefield) {
            alertController.userAlert(Alert.AlertType.ERROR, "Reached end of gamefield", "Can't move further, already reached the end of the gamefield.");
        }

    }


    @Override
    public void takeDrink() {
        try {
            super.takeDrink();

            /*} catch (Throwable t) {
            //Uncomment these lines if you want to give the user a more comprehensive alert about the error that has just occurred
            t.printStackTrace(new PrintWriter(errorMessage));
            alertController.userAlert(Alert.AlertType.ERROR, "An error has occurred", String.valueOf(errorMessage));*/

        } catch (HandsNotEmptyException hne) {
            alertController.userAlert(Alert.AlertType.ERROR, "Hands are full", "Can't pick the drink up, hands are already full. Please put something down before picking the drink up.");
        } catch (DrinkInFrontException dife) {
            alertController.userAlert(Alert.AlertType.ERROR, "No drink in front", "There is no drink in front to pick up.");
        } catch (EndOfGameFieldException endOfGamefield) {
            alertController.userAlert(Alert.AlertType.ERROR, "Reached end of gamefield", "Can't move further, already reached the end of the gamefield.");
        }
    }


    @Override
    public void putCatDown() {
        try {
            super.putCatDown();

            /*} catch (Throwable t) {
            //Uncomment these lines if you want to give the user a more comprehensive alert about the error that has just occurred
            t.printStackTrace(new PrintWriter(errorMessage));
            alertController.userAlert(Alert.AlertType.ERROR, "An error has occurred", String.valueOf(errorMessage));*/

        } catch (WallInFrontException wall) {
            alertController.userAlert(Alert.AlertType.ERROR, "Wall in front", "Can't put cat on a wall.");
        } catch (DrinkInFrontException drink) {
            alertController.userAlert(Alert.AlertType.ERROR, "Drink in front", "Can't put cat down here, there is a drink in front.");
        } catch (NoCatInHandException ncih) {
            alertController.userAlert(Alert.AlertType.ERROR, "No cat in hand", "Can't put cat down, because no cat was being hold.");
        } catch (CatInFrontException cat) {
            alertController.userAlert(Alert.AlertType.ERROR, "There's a cat in front", "There's already a cat. Can't put a cat on another cat.");
        } catch (EndOfGameFieldException endOfGamefield) {
            alertController.userAlert(Alert.AlertType.ERROR, "Reached end of gamefield", "Can't move further, already reached the end of the gamefield.");
        }
    }


    @Override
    public void putDrinkDown() {
        try {
            super.putDrinkDown();

            /*} catch (Throwable t) {
            //Uncomment these lines if you want to give the user a more comprehensive alert about the error that has just occurred
            t.printStackTrace(new PrintWriter(errorMessage));
            alertController.userAlert(Alert.AlertType.ERROR, "An error has occurred", String.valueOf(errorMessage));*/

        } catch (WallInFrontException wall) {
            alertController.userAlert(Alert.AlertType.ERROR, "There's a wall in front", "Can't put drink on a wall.");
        } catch (NoDrinkInHandException ndih) {
            alertController.userAlert(Alert.AlertType.ERROR, "No drink in hand", "Can't put drink down, because no drink was being hold.");
        } catch (CatInFrontException cat) {
            alertController.userAlert(Alert.AlertType.ERROR, "There's a cat in front", "Can't put your drink here because there's already a cat there.");
        } catch (EndOfGameFieldException endOfGamefield) {
            alertController.userAlert(Alert.AlertType.ERROR, "Reached end of gamefield", "Can't move further, already reached the end of the gamefield.");
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
