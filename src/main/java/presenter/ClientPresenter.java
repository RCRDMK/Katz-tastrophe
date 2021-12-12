package presenter;

import controller.FileController;
import controller.GameFieldPanelController;
import game.GameCharacter;
import game.GameField;
import game.exceptions.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * This class holds the entire view of the FXML file with which the user can interact. As such it starts the application
 * and handles the action events coming from the user.
 *
 * @since 03.11.2021
 */
public class ClientPresenter {

    public static final String fxml = "/fxml/ClientView.fxml";

    @FXML
    SplitPane splitPane;

    @FXML
    ScrollPane scrollPane;

    @FXML
    TextArea textInput;


    private GameField gameField;
    private GameCharacter character;
    private GameFieldPanelController gameFieldPanelController;
    private FileController fileController = new FileController();

    public ClientPresenter() {
    }


    public void initialize() throws IOException {
        gameFieldPanelController = new GameFieldPanelController(7, 7);
        scrollPane.setContent(gameFieldPanelController.getGameFieldPanel());
        character = gameFieldPanelController.getCharacter();
        gameField = gameFieldPanelController.getGameField();
        gameField.addObserver(gameFieldPanelController.getGameFieldPanel());


        if (Files.notExists(Path.of("programs"))) {
            Files.createDirectory(Path.of("programs"));
        }

        contextClick();
    }

    private void contextClick() {
        scrollPane.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                ContextMenu contextMenu = new ContextMenu();
                MenuItem menuItem = new MenuItem("test");
                contextMenu.getItems().add(menuItem);
                scrollPane.setContextMenu(contextMenu);

                menuItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(menuItem.getText() + " clicked");
                    }
                });
            }
        });
    }

    private void loadWindows() {

    }

    public void onNewFileClicked(ActionEvent actionEvent) throws Exception {

        try {
            Parent root = FXMLLoader.load(GameField.class.getClassLoader().getResource("fxml/NewFileView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Neue Datei erstellen");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        fileController.compileTest();
        //fileController.loadTest();
    }

    public void onLoadFileClicked(ActionEvent actionEvent) throws Exception {

        Stage primaryStage = new Stage();

        Parent root = FXMLLoader.load(GameField.class.getClassLoader().getResource("fxml/ClientView.fxml"));
        primaryStage.setScene(new Scene(root, 1150, 400));

        primaryStage.setTitle("Katz-tastrophe");
        //Panel und Textfeld speichern, Kompilieren und über den Classloader sich die Methoden holen(?), über die Methoden Panel und Textfeld setzen
        scrollPane.setContent(gameFieldPanelController.te());
        textInput.setText("das ist ein Text");

        primaryStage.show();

        primaryStage.setMaxHeight(500);
        primaryStage.setMaxWidth(primaryStage.getWidth());

        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(1150);

    }

    public void onSaveXmlClicked(ActionEvent actionEvent) {

    }

    /**
     * Responsible for handling the interaction with the quit menu item and button.
     * <p>
     * If the user clicks this menu item, the application in its entirety will get terminated.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onQuitClicked(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Responsible for handling the interaction with the change gamefield size menu item and button.
     * <p>
     * When this method is called, a new instance of the ChangeGameFieldView gets created.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @throws IOException
     * @since 21.11.2021
     */
    public void onChangeSizeFieldClicked(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(GameField.class.getClassLoader().getResource("fxml/ChangeGameFieldView.fxml"));
            Parent root = loader.load();
            ChangeGameFieldPresenter changeGameFieldPresenter = (ChangeGameFieldPresenter) loader.getController();
            changeGameFieldPresenter.init(gameField);
            Stage stage = new Stage();
            stage.setTitle("Größe des Spielfeldes ändern");
            stage.setScene(new Scene(root));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Responsible for handling the interaction with the menu item and button for placing the character on a tile.
     * <p>
     * When this method is called, it first checks if the click actually happened inside the gamefield and
     * not on the offside of it. Afterwards it searches the array for the current position of the character in the
     * gamefield array and overwrites its position. Then it saves the x and y coordinates, subtracts the border patting of
     * the gamefield and divides it through tile width and height declared in the GameFieldPanel class. By saving it
     * as an int, it can traverse the gamefield array much more easily, as when it had to worry about potential decimals.
     * Lastly, it calls the method to manually alter objects inside the gamefield array, checks if the character still
     * exists within the array and then draws gamefield new to accommodate for the change that just happened.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 20.11.2021
     */
    public void onPlaceCharaClicked(ActionEvent actionEvent) {//TODO Chara per Drag und Drop bewegen
        scrollPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if ((event.getX() > gameFieldPanelController.getGameFieldPanel().getBorderPatting() && event.getX() < 251) && (event.getY() > gameFieldPanelController.getGameFieldPanel().getBorderPatting() && event.getY() < 250)) {
                    for (int i = 0; i < gameField.getGameFieldArray().length; i++) {
                        for (int j = 0; j < gameField.getGameFieldArray()[0].length; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals("^") || gameField.getGameFieldArray()[i][j].equals("v") || gameField.getGameFieldArray()[i][j].equals(">") || gameField.getGameFieldArray()[i][j].equals("<")) {
                                gameField.getGameFieldArray()[i][j] = "x";
                            }
                        }
                    }
                    int xAxis = (int) ((event.getY() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileHeightCalculated());
                    int yAxis = (int) ((event.getX() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileWidthCalculated());
                    gameField.placeObjectsInGameField(xAxis, yAxis, "^");
                    gameFieldPanelController.getGameFieldPanel().drawObjectsOnGameField();
                }

            }
        });
    }

    public void dragChara() {

        scrollPane.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = scrollPane.startDragAndDrop(TransferMode.MOVE);
                int xAxis = (int) ((event.getY() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileHeightCalculated());
                int yAxis = (int) ((event.getX() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileWidthCalculated());
                System.out.println("drag " + xAxis + " " + yAxis);
                event.consume();
            }
        });

        scrollPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() == scrollPane) {
                    event.acceptTransferModes(TransferMode.MOVE);
                    System.out.println("over");
                }

            }
        });

        scrollPane.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                System.out.println("entered " + event.getX() + event.getY());
            }
        });
    }

    /**
     * Responsible for handling the interaction with the menu item and button for placing a cat on a tile.
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
                if ((event.getX() > gameFieldPanelController.getGameFieldPanel().getBorderPatting() && event.getX() < 251) && (event.getY() > gameFieldPanelController.getGameFieldPanel().getBorderPatting() && event.getY() < 250)) {
                    int xAxis = (int) ((event.getY() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileHeightCalculated());
                    int yAxis = (int) ((event.getX() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileWidthCalculated());
                    gameField.placeObjectsInGameField(xAxis, yAxis, "C");
                    gameField.checkIfCharacterExists();
                    gameFieldPanelController.getGameFieldPanel().drawObjectsOnGameField();
                }

            }
        });
    }

    /**
     * Responsible for handling the interaction with the menu item and button for placing a wall on a tile.
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
                if ((event.getX() > gameFieldPanelController.getGameFieldPanel().getBorderPatting() && event.getX() < 251) && (event.getY() > gameFieldPanelController.getGameFieldPanel().getBorderPatting() && event.getY() < 250)) {
                    int xAxis = (int) ((event.getY() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileHeightCalculated());
                    int yAxis = (int) ((event.getX() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileWidthCalculated());
                    gameField.placeObjectsInGameField(xAxis, yAxis, "W");
                    gameField.checkIfCharacterExists();
                }

            }
        });
    }

    /**
     * Responsible for handling the interaction with the menu item and button for placing a drink on a tile.
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
                if ((event.getX() > gameFieldPanelController.getGameFieldPanel().getBorderPatting() && event.getX() < 251) && (event.getY() > gameFieldPanelController.getGameFieldPanel().getBorderPatting() && event.getY() < 250)) {
                    for (int i = 0; i < gameField.getGameFieldArray().length; i++) {
                        for (int j = 0; j < gameField.getGameFieldArray()[0].length; j++) {
                            if (gameField.getGameFieldArray()[i][j].equals("D")) {
                                gameField.getGameFieldArray()[i][j] = "x";
                            }
                        }
                    }
                    int xAxis = (int) ((event.getY() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileHeightCalculated());
                    int yAxis = (int) ((event.getX() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileWidthCalculated());
                    gameField.placeObjectsInGameField(xAxis, yAxis, "D");
                    gameField.checkIfCharacterExists();
                }

            }
        });
    }

    /**
     * Responsible for handling the interaction with the menu item and button for removing content from a tile.
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
                if ((event.getX() > gameFieldPanelController.getGameFieldPanel().getBorderPatting() && event.getX() < 251) && (event.getY() > gameFieldPanelController.getGameFieldPanel().getBorderPatting() && event.getY() < 250)) {
                    int xAxis = (int) ((event.getY() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileHeightCalculated());
                    int yAxis = (int) ((event.getX() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileWidthCalculated());
                    gameField.placeObjectsInGameField(xAxis, yAxis, "x");
                    gameField.checkIfCharacterExists();
                }

            }
        });
    }

    /**
     * Responsible for handling the interaction with the menu item and button for moving up.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onMoveUpClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        character.lookHere("up");
        character.moveUp();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for moving down.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onMoveDownClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        character.lookHere("down");
        character.moveDown();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for moving left.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onMoveLeftClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        character.lookHere("left");
        character.moveLeft();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for moving right.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onMoveRightClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException {
        character.lookHere("right");
        character.moveRight();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for picking the cat up.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onPickCatUpClicked(ActionEvent actionEvent) throws HandsNotEmptyException {
        character.takeCat();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for picking the drink up.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onPickDrinkUpClicked(ActionEvent actionEvent) throws HandsNotEmptyException {
        character.takeDrink();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for putting the cat down.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onPutCatDownClicked(ActionEvent actionEvent) throws WallInFrontException, DrinkInFrontException, NoCatInHandException, CatInFrontException {
        character.putCatDown();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for putting the drink down.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onPutDrinkDownClicked(ActionEvent actionEvent) throws WallInFrontException, NoDrinkInHandException, CatInFrontException {
        character.putDrinkDown();
    }

    //TODO Drop endlich ma hinbekommen
    public void onDragClick(MouseEvent mouseEvent) {
        System.out.println("drag");
    }

    public void onDragClickDone(DragEvent dragEvent) {
        System.out.println("done");
    }
}
