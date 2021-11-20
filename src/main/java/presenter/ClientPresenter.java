package presenter;

import controller.GameFieldPanelController;
import game.GameCharacter;
import game.GameField;
import game.GameFieldPanel;
import game.exceptions.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
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
        character = gameFieldPanelController.getCharacter();
        /*scrollPane.setPrefSize(GameFieldPanel.getCanvas().getWidth(), GameFieldPanel.getCanvas().getHeight());
        vBox.setPrefSize(GameFieldPanel.getCanvas().getWidth(), GameFieldPanel.getCanvas().getHeight());
        hBox.setPrefSize(GameFieldPanel.getCanvas().getWidth(), GameFieldPanel.getCanvas().getHeight());*/
    }

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
        GameField.setGameField(5, 5);
        for (int i = 5; i < GameField.getGameField().length; i++) {
            for (int j = 5; j < GameField.getGameField()[j].length; j++) {
                GameField.placeObjectsInGameField(i, j, "x");
            }
        }
        GameField.checkIfCharacterExists();
        GameFieldPanel.drawObjectsOnGameField();
    }

    //TODO Aufpassen nicht mehr Objekte zu malen als erlaubt ist

    /**
     * Responsible for handling the interaction with the menu item for placing the character on a tile.
     * <p>
     * When this method is called, it first checks if the click actually happened inside the gamefield and
     * not on the offside of it. Afterwards it saves the x and y coordinates, subtracts the border patting of
     * the gamefield and divides it through tile width and height declared in the GameFieldPanel class. By saving it
     * as an int, it can traverse the gamefield array much more easily, as when it had to worry about potential decimals.
     * Lastly, it calls the method to manually alter objects inside the gamefield array, checks if the character still
     * exists within the array and then draws gamefield new to accommodate for the change that just happened.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 20.11.2021
     */
    public void onPlaceCharaClicked(ActionEvent actionEvent) {
        scrollPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if ((event.getX() > GameFieldPanel.getBorderPatting() && event.getX() < 251) && (event.getY() > GameFieldPanel.getBorderPatting() && event.getY() < 250)) {
                    int xAxis = (int) ((event.getY() - GameFieldPanel.getBorderPatting()) / GameFieldPanel.getTileHeightCalculated());
                    int yAxis = (int) ((event.getX() - GameFieldPanel.getBorderPatting()) / GameFieldPanel.getTileWidthCalculated());
                    GameField.placeObjectsInGameField(xAxis, yAxis, "^");
                    GameField.checkIfCharacterExists();
                    GameFieldPanel.drawObjectsOnGameField();
                }

            }
        });
    }

    /**
     * Responsible for handling the interaction with the menu item for placing a cat on a tile.
     * <p>
     * When this method is called, it first checks if the click actually happened inside the gamefield and
     * not on the offside of it. Afterwards it saves the x and y coordinates, subtracts the border patting of
     * the gamefield and divides it through tile width and height declared in the GameFieldPanel class. By saving it
     * as an int, it can traverse the gamefield array much more easily, as when it had to worry about potential decimals.
     * Lastly, it calls the method to manually alter objects inside the gamefield array, checks if the character still
     * exists within the array and then draws gamefield new to accommodate for the change that just happened.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 20.11.2021
     */
    public void onPlaceCatClicked(ActionEvent actionEvent) {
        scrollPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if ((event.getX() > GameFieldPanel.getBorderPatting() && event.getX() < 251) && (event.getY() > GameFieldPanel.getBorderPatting() && event.getY() < 250)) {
                    int xAxis = (int) ((event.getY() - GameFieldPanel.getBorderPatting()) / GameFieldPanel.getTileHeightCalculated());
                    int yAxis = (int) ((event.getX() - GameFieldPanel.getBorderPatting()) / GameFieldPanel.getTileWidthCalculated());
                    GameField.placeObjectsInGameField(xAxis, yAxis, "C");
                    GameField.checkIfCharacterExists();
                    GameFieldPanel.drawObjectsOnGameField();
                }

            }
        });
    }

    /**
     * Responsible for handling the interaction with the menu item for placing a wall on a tile.
     * <p>
     * When this method is called, it first checks if the click actually happened inside the gamefield and
     * not on the offside of it. Afterwards it saves the x and y coordinates, subtracts the border patting of
     * the gamefield and divides it through tile width and height declared in the GameFieldPanel class. By saving it
     * as an int, it can traverse the gamefield array much more easily, as when it had to worry about potential decimals.
     * Lastly, it calls the method to manually alter objects inside the gamefield array, checks if the character still
     * exists within the array and then draws gamefield new to accommodate for the change that just happened.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 20.11.2021
     */
    public void onPlaceWallClicked(ActionEvent actionEvent) {
        scrollPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if ((event.getX() > GameFieldPanel.getBorderPatting() && event.getX() < 251) && (event.getY() > GameFieldPanel.getBorderPatting() && event.getY() < 250)) {
                    int xAxis = (int) ((event.getY() - GameFieldPanel.getBorderPatting()) / GameFieldPanel.getTileHeightCalculated());
                    int yAxis = (int) ((event.getX() - GameFieldPanel.getBorderPatting()) / GameFieldPanel.getTileWidthCalculated());
                    GameField.placeObjectsInGameField(xAxis, yAxis, "W");
                    System.out.println(xAxis + "" + yAxis);
                    GameField.checkIfCharacterExists();
                    GameFieldPanel.drawObjectsOnGameField();
                }

            }
        });
    }

    /**
     * Responsible for handling the interaction with the menu item for placing a drink on a tile.
     * <p>
     * When this method is called, it first checks if the click actually happened inside the gamefield and
     * not on the offside of it. Afterwards it saves the x and y coordinates, subtracts the border patting of
     * the gamefield and divides it through tile width and height declared in the GameFieldPanel class. By saving it
     * as an int, it can traverse the gamefield array much more easily, as when it had to worry about potential decimals.
     * Lastly, it calls the method to manually alter objects inside the gamefield array, checks if the character still
     * exists within the array and then draws gamefield new to accommodate for the change that just happened.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 20.11.2021
     */
    public void onPlaceDrinkClicked(ActionEvent actionEvent) {
        scrollPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if ((event.getX() > GameFieldPanel.getBorderPatting() && event.getX() < 251) && (event.getY() > GameFieldPanel.getBorderPatting() && event.getY() < 250)) {
                    int xAxis = (int) ((event.getY() - GameFieldPanel.getBorderPatting()) / GameFieldPanel.getTileHeightCalculated());
                    int yAxis = (int) ((event.getX() - GameFieldPanel.getBorderPatting()) / GameFieldPanel.getTileWidthCalculated());
                    GameField.placeObjectsInGameField(xAxis, yAxis, "D");
                    GameField.checkIfCharacterExists();
                    GameFieldPanel.drawObjectsOnGameField();
                }

            }
        });
    }

    /**
     * Responsible for handling the interaction with the menu item for removing content from a tile.
     * <p>
     * When this method is called, it first checks if the click actually happened inside the gamefield and
     * not on the offside of it. Afterwards it saves the x and y coordinates, subtracts the border patting of
     * the gamefield and divides it through tile width and height declared in the GameFieldPanel class. By saving it
     * as an int, it can traverse the gamefield array much more easily, as when it had to worry about potential decimals.
     * Lastly, it calls the method to manually alter objects inside the gamefield array, checks if the character still
     * exists within the array and then draws gamefield new to accommodate for the change that just happened.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 20.11.2021
     */
    public void onDeleteContentClicked(ActionEvent actionEvent) {
        scrollPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if ((event.getX() > GameFieldPanel.getBorderPatting() && event.getX() < 251) && (event.getY() > GameFieldPanel.getBorderPatting() && event.getY() < 250)) {
                    int xAxis = (int) ((event.getY() - GameFieldPanel.getBorderPatting()) / GameFieldPanel.getTileHeightCalculated());
                    int yAxis = (int) ((event.getX() - GameFieldPanel.getBorderPatting()) / GameFieldPanel.getTileWidthCalculated());
                    GameField.placeObjectsInGameField(xAxis, yAxis, "x");
                    GameField.checkIfCharacterExists();
                    GameFieldPanel.drawObjectsOnGameField();
                }

            }
        });
    }

    /**
     * Responsible for handling the interaction with the menu item for moving up.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onMoveUpClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        character.lookHere("up");
        character.moveUp();
    }

    /**
     * Responsible for handling the interaction with the menu item for moving down.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onMoveDownClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        character.lookHere("down");
        character.moveDown();
    }

    /**
     * Responsible for handling the interaction with the menu item for moving left.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onMoveLeftClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        character.lookHere("left");
        character.moveLeft();
    }

    /**
     * Responsible for handling the interaction with the menu item for moving right.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onMoveRightClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        character.lookHere("right");
        character.moveRight();
    }

    /**
     * Responsible for handling the interaction with the menu item for picking the cat up.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onPickCatUpClicked(ActionEvent actionEvent) throws HandsNotEmptyException {
        character.takeCat();
    }

    /**
     * Responsible for handling the interaction with the menu item for picking the drink up.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onPickDrinkUpClicked(ActionEvent actionEvent) throws HandsNotEmptyException {
        character.takeDrink();
    }

    /**
     * Responsible for handling the interaction with the menu item for putting the cat down.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onPutCatDownClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, NoCatInHandException, CatInFrontException {
        character.putCatDown();
    }

    /**
     * Responsible for handling the interaction with the menu item for putting the drink down.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onPutDrinkDownClicked(ActionEvent actionEvent) throws WallInFrontException, NoDrinkInHandException, CatInFrontException {
        character.putDrinkDown();
    }
}
