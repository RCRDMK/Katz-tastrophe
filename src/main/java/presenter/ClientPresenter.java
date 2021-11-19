package presenter;

import controller.GameFieldPanelController;
import game.GameCharacter;
import game.GameField;
import game.exceptions.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


/**
 * This class holds the entire view of the FXML file with which the user can interact. As such it starts the application
 * and handles the action events coming from the user.
 *
 * @since 03.11.2021
 */
public class ClientPresenter extends Application {

    public static final String fxml = "/fxml/ClientView.fxml";
    @FXML
    ScrollPane scrollPane;
    private GameCharacter character;
    private GameField gameField;
    private GameFieldPanelController gameFieldPanelController;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(GameField.class.getClassLoader().getResource("fxml/ClientView.fxml"));
        primaryStage.setScene(new Scene(root, 1150, 400));

        primaryStage.setTitle("Katz-tastrophe");

        primaryStage.show();

        primaryStage.setMaxHeight(500);
        primaryStage.setMaxWidth(primaryStage.getWidth());

        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(1150);
    }

    public void initialize() {
        gameFieldPanelController = new GameFieldPanelController(7, 7);
        scrollPane.setContent(gameFieldPanelController.getGameFieldPanel());
        character = new GameCharacter(gameFieldPanelController.getGameFieldPanel());
    }

    //Kopiervorlage für Actionevents
    /**
     *
     *
     * @param actionEvent the interaction of the user with the FXML Element
     *
     * @since 19.11.2021
     */

    /**
     * Responsible for handling the interaction with the quit menu item.
     * <p>
     * If the user clicks this menu item, the application in its entirety will get terminated.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onQuitClicked(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void onChangeSizeFieldClicked(ActionEvent actionEvent) {
    }

    public void onPlaceCharaClicked(ActionEvent actionEvent) {
    }

    public void onPlaceCatClicked(ActionEvent actionEvent) {
    }

    public void onPlaceWallClicked(ActionEvent actionEvent) {
    }

    public void onPlaceDrinkClicked(ActionEvent actionEvent) {
    }

    public void onDeleteContentClicked(ActionEvent actionEvent) {
    }

    public void onMoveUpClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        character.lookHere("up");
        character.moveUp();
    }

    public void onMoveDownClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        character.lookHere("down");
        character.moveDown();
    }

    public void onMoveLeftClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        character.lookHere("left");
        character.moveLeft();
    }

    public void onMoveRightClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        character.lookHere("right");
        character.moveRight();
    }

    public void onPickCatUpClicked(ActionEvent actionEvent) throws HandsNotEmptyException {
        character.takeCat();
    }

    public void onPickDrinkUpClicked(ActionEvent actionEvent) throws HandsNotEmptyException {
        character.takeDrink();
    }

    public void onPutCatDownClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, NoCatInHandException, CatInFrontException {
        character.putCatDown();
    }

    public void onPutDrinkDownClicked(ActionEvent actionEvent) throws WallInFrontException, NoDrinkInHandException, CatInFrontException {
        character.putDrinkDown();
    }
}
