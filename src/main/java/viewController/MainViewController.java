package viewController;

import controller.FileController;
import controller.GameFieldPanelController;
import controller.SimulationController;
import controller.XMLController;
import game.CharaWrapper;
import game.GameCharacter;
import game.GameField;
import game.exceptions.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pattern.ObserverInterface;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * This class holds the entire view of the FXML file with which the user can interact. As such it starts the application
 * and handles the action events coming from the user.
 *
 * @since 03.11.2021
 */
public class MainViewController implements ObserverInterface {

    //TODO Es darf eine Instanz nur einmal offen sein und nicht fünf Fenster mit dem selben Namen

    public static final String fxml = "/fxml/MainView.fxml";

    @FXML
    ScrollPane scrollPane;

    @FXML
    TextArea textInput;


    private GameField gameField;
    private GameCharacter character;
    private GameFieldPanelController gameFieldPanelController;

    private FileController fileController = new FileController();
    private XMLController xmlController = new XMLController();

    public MainViewController() {
    }


    public void initialize() throws IOException {
        gameFieldPanelController = new GameFieldPanelController(7, 7);
        scrollPane.setContent(gameFieldPanelController.getGameFieldPanel());
        character = gameFieldPanelController.getCharacter();
        gameField = gameFieldPanelController.getGameField();
        gameField.addObserver(gameFieldPanelController.getGameFieldPanel());
        gameField.addObserver(this);
        //stage = (Stage) textInput.getScene().getWindow();


        if (Files.notExists(Path.of("programs"))) {
            Files.createDirectory(Path.of("programs"));

        }
        fileController.fileWhenFirstOpened();
        textInput.setText(fileController.loadTextForEditor(fileController.getProgramName()));
        //TODO Checken, ob beim Speichern der Standard Name verwendet wird oder nicht. Wenn ja,
        // dann muss ein Fenster sich öffnen und die Datei muss benannt werden. Das darf aber nur beim Anlicken des
        // Speicher Buttons passieren. Das Kompilieren, wo das Speichern schon mit inbegriffen ist, ist davon ausgenommen.
        // Es kann ohne umbenennen eine Datei mit dem Standard Namen kompiliert werden.

        contextClick();
    }

    /**
     * Responsible for handling request for a context menu made by the user.
     * <p>
     * When this method is being called, it compiles the current fileController and writes all of its declared methods into an
     * array. It then iterates through the array and creates for each method a new menu item. Afterwards it does the same
     * for the parent class of the file, so that in the context menu are menu items from both the file itself and its
     * parent class.
     *
     * @since 18.12.2021
     */
    private void contextClick() {
        scrollPane.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {//TODO Context-Menü im richtigen Fenster anzeigen und nicht im Fenster, dass das Laden eines neues Fensters auslöst
                ContextMenu contextMenu = new ContextMenu();
                Class c = fileController.compiledMethods(fileController.getProgramName());
                Method[] m = c.getDeclaredMethods();
                for (Method met : m//TODO Besseren Code schreiben
                ) {//Context menu for the main method and methods written by the user at runtime
                    MenuItem menuItem = new MenuItem(met.toString());
                    contextMenu.getItems().add(menuItem);
                    menuItem.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                Method metExc = c.getMethod(met.getName());
                                metExc.invoke(c.getDeclaredConstructor().newInstance());
                            } catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
                CharaWrapper charaWrapper = new CharaWrapper();
                m = charaWrapper.getClass().getDeclaredMethods();

                for (Method met : m
                ) {//Context menu for methods in the GameCharacter class
                    MenuItem menuItem = new MenuItem(met.toString().replace("game.CharaWrapper.", ""));
                    contextMenu.getItems().add(menuItem);
                    menuItem.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                Method metExc = c.getMethod(met.getName());
                                switch (metExc.getName()) {//TODO Besseren Code schreiben
                                    case "moveUp":
                                        onMoveUpClicked(event);
                                        break;
                                    case "moveDown":
                                        onMoveDownClicked(event);
                                        break;
                                    case "moveLeft":
                                        onMoveLeftClicked(event);
                                        break;
                                    case "moveRight":
                                        onMoveRightClicked(event);
                                        break;
                                    case "takeCat":
                                        onPickCatUpClicked(event);
                                        break;
                                    case "takeDrink":
                                        onPickDrinkUpClicked(event);
                                        break;
                                    case "putCatDown":
                                        onPutCatDownClicked(event);
                                        break;
                                    case "putDrinkDown":
                                        onPutDrinkDownClicked(event);
                                        break;
                                    case "handsFree":
                                        onMoveUpClicked(event);
                                        break;
                                    case "catThere":
                                        onMoveUpClicked(event);
                                        break;
                                    case "stepOverCatPossible":
                                        onMoveUpClicked(event);
                                        break;
                                }
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                    });
                }

                scrollPane.setContextMenu(contextMenu);
            }
        });
    }

    public void drag(MouseEvent mouseEvent) {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED) && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
        }
    }

    public void drop(MouseEvent mouseEvent) {//TODO Bug beseitigen, dass beim Setzen von anderen Objekten der Charakter auf 0,0 gesetzt wird
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED) && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if ((mouseEvent.getX() > gameFieldPanelController.getGameFieldPanel().getBorderPatting() && mouseEvent.getX() < 251) && (mouseEvent.getY() > gameFieldPanelController.getGameFieldPanel().getBorderPatting() && mouseEvent.getY() < 250)) {
                for (int i = 0; i < gameField.getGameFieldArray().length; i++) {
                    for (int j = 0; j < gameField.getGameFieldArray()[0].length; j++) {
                        if (gameField.getGameFieldArray()[i][j].equals("^") || gameField.getGameFieldArray()[i][j].equals("v") || gameField.getGameFieldArray()[i][j].equals(">") || gameField.getGameFieldArray()[i][j].equals("<")) {
                            gameField.getGameFieldArray()[i][j] = "x";
                        }
                    }
                }
                int xAxis = (int) ((mouseEvent.getY() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileHeightCalculated());
                int yAxis = (int) ((mouseEvent.getX() - gameFieldPanelController.getGameFieldPanel().getBorderPatting()) / gameFieldPanelController.getGameFieldPanel().getTileWidthCalculated());
                gameField.placeObjectsInGameField(xAxis, yAxis, "^");
                gameFieldPanelController.getGameFieldPanel().drawObjectsOnGameField();
            }
        }

    }

    /**
     * Responsible for presenting the user his/her file explorer in which he/she can choose which file to load.
     *
     * @param stage stage to represent the fileChooser
     * @return boolean value if the user has chosen a file (true) or cancelled out of the window (false)
     * @since 17.12.2021
     */
    //https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
    private boolean loadWindows(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/programs/"));
        fileChooser.setTitle("Programm öffnen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".java", "*.java"));
        File selectedFile = fileChooser.showOpenDialog(stage);


        if (selectedFile != null) {
            if (selectedFile.getName().replace(".java", "") == fileController.getProgramName()) {
                System.out.println("hi");
                return false;
            }
            fileController.setProgramName(selectedFile.getName().replace(".java", ""));
            System.out.println("Load Win " + fileController.getProgramName());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Responsible saving the scrollPane as a .png or .gif file.
     *
     * @since 23.12.2021
     */
    //https://stackoverflow.com/questions/38028825/javafx-save-view-of-pane-to-image
    public void createImage() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".png", "*.png"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".gif", "*.gif"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null && file.getName().endsWith(".png")) {
            try {
                WritableImage writableImage = new WritableImage((int) scrollPane.getWidth(),
                        (int) scrollPane.getHeight());
                scrollPane.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (file != null && file.getName().endsWith(".gif")) {
            try {
                WritableImage writableImage = new WritableImage((int) scrollPane.getWidth(),
                        (int) scrollPane.getHeight());
                scrollPane.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "gif", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Responsible creating a printer job to print the textarea.
     *
     * @param stage stage on which the dialog window should appear.
     * @since 26.12.2021
     */
    //https://stackoverflow.com/questions/54789373/how-to-print-pane-in-javafx
    public void printGameFieldAndUserCode(Stage stage) {
        PrinterJob pj = PrinterJob.createPrinterJob();
        pj.showPrintDialog(stage);
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, 0, 0, 0, 0);
        JobSettings jobSettings = pj.getJobSettings();
        jobSettings.setPageLayout(pageLayout);
        boolean wasPrinted = pj.printPage(textInput);
        if (wasPrinted) {
            pj.endJob();
        }
    }

    /**
     * Responsible handling the request of the user to compile the current file.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 16.12.2021
     */
    public void onCompileFileClicked(ActionEvent actionEvent) {
        character = fileController.compileFileAndSetNewCharacter(fileController.getProgramName(), gameField, character, gameFieldPanelController);
    }

    /**
     * Responsible for handling the request of the user to create a new file.
     * <p>
     * When this method is called, a window will pop up, asking to input the name for the new file.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 03.12.2021
     */
    public void onNewFileClicked(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(GameField.class.getClassLoader().getResource("fxml/NewFileView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Neue Datei erstellen");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Responsible for handling the action event to load a file requested by the user.
     * <p>
     * When this method is called, it first asks the user which file to load. If the user has chosen a file, it will
     * then proceed to load a new ClientView instance and filling it with the values of the chosen file.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @throws Exception possible exception which can be thrown when loading a new fxml view
     * @since 10.12.2021
     */
    public void onLoadFileClicked(ActionEvent actionEvent) throws Exception {
        Stage primaryStage = new Stage();
        if (!loadWindows(primaryStage)) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(GameField.class.getClassLoader().getResource("fxml/LoadedView.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 1150, 400));

        primaryStage.setTitle(fileController.getProgramTitleName());
        System.out.println("Load " + fileController.getProgramName());

        primaryStage.show();

        primaryStage.setMaxHeight(500);
        primaryStage.setMaxWidth(primaryStage.getWidth());

        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(1150);
        LoadedFileViewController lfp = loader.getController();
        lfp.setTextInput(fileController.loadTextForEditor(fileController.getProgramName()), fileController.getProgramName(), fileController);


    }

    /**
     * Responsible for handling the request made by the user to save the current file as a .java file.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 15.12.2021
     */
    //TODO Fenster dürfen nur ihre Datei speichern und nicht in denen, welche sie laden
    public void onSaveFileClicked(ActionEvent actionEvent) {
        fileController.setProgramName(fileController.getProgramName());
        fileController.saveFile(fileController.getProgramName(), textInput.getText());
        System.out.println("Save " + fileController.getProgramName());
    }

    /**
     * Responsible for handling the request made by the user to save the current file as a .xml file.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 16.01.2022
     */
    public void onSaveAsXmlClicked(ActionEvent actionEvent) {
        xmlController.saveAsXML(gameField, (Stage) scrollPane.getScene().getWindow());
    }

    /**
     * Responsible for handling the request made by the user to load a .xml file.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 18.01.2022
     */
    public void onLoadXmlClicked(ActionEvent actionEvent) {
        xmlController.loadXML(gameField, (Stage) scrollPane.getScene().getWindow());
    }

    /**
     * Responsible for handling the request made by the user to save the current file as an image.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 23.12.2021
     */
    public void onSaveAsImageClicked(ActionEvent actionEvent) {
        createImage();
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
        Stage stage = (Stage) textInput.getScene().getWindow();
        stage.hide();
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
            ChangeGameFieldViewController changeGameFieldViewController = (ChangeGameFieldViewController) loader.getController();
            changeGameFieldViewController.init(gameField);
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
                drop(event);
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
     * Responsible for handling the request made by the user to print the current file.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 26.12.2021
     */
    public void onPrintClicked(ActionEvent actionEvent) {
        printGameFieldAndUserCode((Stage) scrollPane.getScene().getWindow());
    }

    /**
     * Responsible for handling the interaction with the menu item and button for moving up.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 19.11.2021
     */
    public void onMoveUpClicked(ActionEvent actionEvent) {
        try {
            character.lookHere("up");
            character.moveUp();
        } catch (Throwable t) {
            System.out.println("Fehler");
        }

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

    @Override //TODO Update muss etwas setzen
    public void update(Object object) {

    }

    /**
     * Responsible for handling the request made by the user to execute the contents of the main method in the
     * currently compiled class.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 15.01.2022
     */
    public void start(ActionEvent actionEvent) {
        SimulationController sc = new SimulationController(gameFieldPanelController);
        sc.start();

    }

    /**
     * Responsible for handling the user request to save the current file as a .rsm file.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 11.01.2022
     */
    public void onSaveAsSerializeClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".rsm", "*.rsm"));
        File file = fileChooser.showSaveDialog(scrollPane.getScene().getWindow());

        if (file != null && file.getName().endsWith(".rsm")) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
                objectOutputStream.writeObject(gameField);
                objectOutputStream.writeUTF(textInput.getText());
                objectOutputStream.writeObject(character);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * Responsible for handling the user request to load a .rsm file.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 13.01.2022
     */
    public void onLoadSerializeClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/programs/"));
        fileChooser.setTitle("Programm öffnen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".rsm", "*.rsm"));
        File file = fileChooser.showOpenDialog(scrollPane.getScene().getWindow());

        if (file != null && file.getName().endsWith(".rsm")) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                gameFieldPanelController.getGameFieldPanel().setGameField((GameField) objectInputStream.readObject());
                gameFieldPanelController.getGameFieldPanel().drawObjectsOnGameField();
                textInput.setText(objectInputStream.readUTF());
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
