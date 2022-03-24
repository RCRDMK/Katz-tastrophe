/*
 * Katz-tastrophe - a miniature programming learn environment
 * Copyright (C) 2022 RCRDMK
 *
 * This program (Katz-tastrophe) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program (Katz-tastrophe) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See LICENSE File in the main directory. If not, see <https://www.gnu.org/licenses/>.
 */

package view.viewController;

import controller.FileController;
import controller.SimulationController;
import controller.XMLController;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.CharaWrapper;
import model.GameCharacter;
import model.GameField;
import model.exceptions.*;
import model.messages.*;
import model.pattern.ObserverInterface;
import view.GameFieldPanelController;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * This class holds the entire view of the FXML file with which the user can interact. As such it starts the application
 * and handles the action events coming from the user.
 *
 * @since 03.11.2021
 */
public class MainViewController implements ObserverInterface {


    private GameField gameField;
    private GameCharacter character;
    private GameFieldPanelController gameFieldPanelController;
    private String currentFileName = "neue_Katztastrophe";
    private Boolean newFileHasBeenCreated = false;

    private FileController fileController = new FileController();
    private XMLController xmlController = new XMLController();
    private SimulationController simulationController;

    public MainViewController() {

    }

    /**
     * Initializes this controller with all of its global variables.
     * <p>
     * When this method is called during the loading of this controller instance, it first sets the values for the variables
     * necessary for running the application and registers observers at the necessary classes. It then loads the file
     * for the window and calls the method for the context menu. When the application has just started, it loads the
     * default file. If the user closes this instance through the X in the top corner, it first saves the content in
     * the textarea.
     *
     * @since 18.11.2021
     */
    public void initialize() {

        gameFieldPanelController = new GameFieldPanelController(7, 7);
        scrollPane.setContent(gameFieldPanelController.getGameFieldPanel());
        character = gameFieldPanelController.getCharacter();
        gameField = gameFieldPanelController.getGameField();
        simulationController = new SimulationController(gameFieldPanelController);
        gameField.addObserver(gameFieldPanelController.getGameFieldPanel());
        gameField.addObserver(this);
        simulationController.addObserver(this);



        Platform.runLater(() ->
                scrollPane.getScene().getWindow().setOnCloseRequest(event -> fileController.saveFile(currentFileName, textInput.getText()))
        );

        character = fileController.compileFileAndSetNewCharacter(fileController.getDefaultName(), gameField, character, gameFieldPanelController);//executed to being able to use the contextClick method right from the start
        textInput.setText(fileController.loadTextForEditor(fileController.getDefaultName()));

        pauseButton.setDisable(true);
        pauseMenuItem.setDisable(true);

        stopButton.setDisable(true);
        stopMenuItem.setDisable(true);

        contextClick();
    }

    /*****************************************************
     *               Helper methods                      *
     *****************************************************/

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
            public void handle(ContextMenuEvent event) {
                ContextMenu contextMenu = new ContextMenu();
                CharaWrapper cw = (CharaWrapper) gameFieldPanelController.getCharacter();
                Class c = cw.getClass();
                Method[] m = c.getDeclaredMethods();
                for (Method met : m
                ) {//Context menu for the main method and methods written by the user at runtime
                    if (Modifier.isPublic(met.getModifiers()) && !Modifier.isStatic(met.getModifiers()) && met.getParameterCount() == 0) {
                        MenuItem menuItem = new MenuItem(met.toString());

                        contextMenu.getItems().add(menuItem);
                        menuItem.setOnAction(new EventHandler<ActionEvent>() {
                            Method method;

                            {
                                method = met;
                            }

                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    method.invoke(character);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
                CharaWrapper chaWra = new CharaWrapper();
                Class cl = chaWra.getClass();
                Method[] me = cl.getDeclaredMethods();
                for (Method met : me
                ) {//Context menu for methods in the CharaWrapper class
                    Method method;

                    {
                        method = met;
                    }
                    if (met.getParameterCount() == 0) {
                        MenuItem menuItem = new MenuItem(met.toString().replace("model.CharaWrapper.", ""));

                        contextMenu.getItems().add(menuItem);
                        menuItem.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    method.invoke(character);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }

                scrollPane.setContextMenu(contextMenu);
            }
        });
    }

    /**
     * Responsible for handling drag&drop requests from the user.
     * <p>
     * When this method is called, it first checks, if the right or the left mouse button was clicked. If the left was clicked,
     * it moves the character to the field on which the user released the mouse button.
     *
     * @param mouseEvent the event which has been fired when the left mouse button has been held.
     * @since 20.01.2022
     */
    private void characterDropped(MouseEvent mouseEvent) {
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
                gameField.placeObjectsInGameField(xAxis, yAxis, gameField.getCharacter());
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
    private String selectFileToLoad(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/programs/"));
        fileChooser.setTitle("Programm öffnen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".java", "*.java"));
        File selectedFile = fileChooser.showOpenDialog(stage);


        if (selectedFile != null) {
            return selectedFile.getName().replace(".java", "");
        } else {
            return null;
        }
    }

    /**
     * Responsible for loading a new window.
     * <p>
     * When this method is called, a new window will be loaded with which the user can interact. Depending on if it's
     * a newly created file or an already existing file, the textarea of the window will be filled with the code in
     * the file.
     *
     * @param loadedFile   Name of the file which should be loaded in the new window
     * @param primaryStage The stage on which the new window should be depicted
     * @throws IOException
     * @since 09.02.2022
     */
    private void loadNewWindow(String loadedFile, Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(GameField.class.getClassLoader().getResource("fxml/MainView.fxml"));

        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 1150, 400));

        MainViewController childMainViewController = loader.getController();
        if (currentFileName.equals(loadedFile)) {
            primaryStage.toFront();
            //return;
        }
        childMainViewController.currentFileName = loadedFile;
        primaryStage.setTitle(childMainViewController.currentFileName + " Katz-tastrophe");

        primaryStage.show();

        childMainViewController.textInput.requestFocus();

        primaryStage.setMaxHeight(500);
        primaryStage.setMaxWidth(primaryStage.getWidth());

        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(1150);

        childMainViewController.textInput.setText(childMainViewController.fileController.loadTextForEditor(childMainViewController.currentFileName));
    }

    /**
     * Responsible saving the scrollPane as a .png or .gif file.
     *
     * @since 23.12.2021
     */
    //https://stackoverflow.com/questions/38028825/javafx-save-view-of-pane-to-image
    private void createImage() {
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
    private void printUserCode(Stage stage) {
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

    /*****************************************************
     *              FXML action events                   *
     *****************************************************/

    /**
     * Responsible handling the request of the user to compile the current file.
     * To enhance the user experience, it saves the file before compiling it.
     *
     * @since 16.12.2021
     */
    public void onCompileFileClicked() {
        fileController.saveFile(currentFileName, textInput.getText());
        fileController.compileFileAndSetNewCharacter(currentFileName, gameField, character, gameFieldPanelController);
        character = gameFieldPanelController.getCharacter();
        infoLabel.setText("Datei erfolgreich kompiliert");
    }

    /**
     * Responsible for handling the request of the user to create a new file.
     * <p>
     * When this method is called, a window will pop up, asking to input the name for the new file. After it, it will load
     * the new file in a new window.
     *
     * @since 03.12.2021
     */
    public void onNewFileClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(GameField.class.getClassLoader().getResource("fxml/NewFileView.fxml"));
            Parent root = loader.load();
            NewFileViewController nfvc = loader.getController();
            nfvc.setMainViewController(this);
            Stage stage = new Stage();
            stage.setTitle("Neue Datei erstellen");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (newFileHasBeenCreated) {
                loadNewWindow(currentFileName, stage);
                newFileHasBeenCreated = false;
            }
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
     * @throws IOException
     * @since 10.12.2021
     */
    public void onLoadFileClicked() throws IOException {
        Stage primaryStage = new Stage();
        String nameOfTheSelectedFile = selectFileToLoad(primaryStage);

        if (nameOfTheSelectedFile == null) {
            return;
        }

        loadNewWindow(nameOfTheSelectedFile, primaryStage);
    }

    /**
     * Responsible for handling the request made by the user to save the current file as a .java file.
     *
     * @since 15.12.2021
     */
    public void onSaveFileClicked() throws IOException {
        if (currentFileName.equals(fileController.getDefaultName())) {
            FXMLLoader loader = new FXMLLoader(GameField.class.getClassLoader().getResource("fxml/NewFileView.fxml"));
            Parent root = loader.load();
            NewFileViewController newFileViewController = loader.getController();
            newFileViewController.setMainViewController(this);
            Stage stage = new Stage();
            stage.setTitle("Möchtest du deine Datei unter einem neuen Namen speichern?");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Stage currentControllerStage = (Stage) scrollPane.getScene().getWindow();
            if (!currentFileName.equals("neue_Katztastrophe")) {
                currentControllerStage.setTitle(currentFileName + " Katz-tastrophe");
            }
        }
        fileController.saveFile(currentFileName, textInput.getText());
        infoLabel.setText("Datei erfolgreich gespeichert");
    }

    /**
     * Responsible for handling the request made by the user to save the current file as a .xml file.
     *
     * @since 16.01.2022
     */
    public void onSaveAsXmlClicked() {
        xmlController.saveAsXML(gameField, character, (Stage) scrollPane.getScene().getWindow());
        infoLabel.setText("Datei gespeichert");
    }

    /**
     * Responsible for handling the request made by the user to load a .xml file.
     *
     * @since 18.01.2022
     */
    public void onLoadXmlClicked() {
        xmlController.loadXML(gameField, character, (Stage) scrollPane.getScene().getWindow());
        infoLabel.setText("Datei geladen");
    }

    /**
     * Responsible for handling the user request to save the current file as a .rsm file.
     *
     * @since 11.01.2022
     */
    public void onSaveAsSerializeClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/programs/"));
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
        infoLabel.setText("Datei serialisiert");
    }

    /**
     * Responsible for handling the user request to load a .rsm file.
     *
     * @since 13.01.2022
     */
    public void onLoadSerializeClicked() {
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
        infoLabel.setText("Datei deserialisiert");
    }

    /**
     * Responsible for handling the request made by the user to save the current file as an image.
     *
     * @since 23.12.2021
     */
    public void onSaveAsImageClicked() {
        createImage();
    }

    /**
     * Responsible for handling the interaction with the quit menu item and button.
     * <p>
     * If the user clicks this menu item, the current window will be closed. If it was the last open window, the
     * application will be terminated.
     *
     * @since 19.11.2021
     */
    public void onQuitClicked() {
        Stage stage = (Stage) textInput.getScene().getWindow();
        fileController.saveFile(currentFileName, textInput.getText());
        stage.hide();
    }

    /**
     * Responsible for handling the interaction with the change gamefield size menu item and button.
     * <p>
     * When this method is called, a new instance of the ChangeGameFieldView gets created.
     *
     * @throws IOException
     * @since 21.11.2021
     */
    public void onChangeSizeFieldClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(GameField.class.getClassLoader().getResource("fxml/ChangeGameFieldView.fxml"));
            Parent root = loader.load();
            ChangeGameFieldViewController changeGameFieldViewController = loader.getController();
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
     * @since 20.11.2021
     */
    public void onPlaceCharaClicked() {
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
                    gameField.placeObjectsInGameField(xAxis, yAxis, ">");
                    gameFieldPanelController.getGameFieldPanel().drawObjectsOnGameField();
                }
                characterDropped(event);
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
     * @since 20.11.2021
     */
    public void onPlaceCatClicked() {
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
     * @since 20.11.2021
     */
    public void onPlaceWallClicked() {
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
     * @since 20.11.2021
     */
    public void onPlaceDrinkClicked() {
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
     * @since 20.11.2021
     */
    public void onDeleteContentClicked() {
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
     * @since 26.12.2021
     */
    public void onPrintClicked() {
        printUserCode((Stage) scrollPane.getScene().getWindow());
    }

    /**
     * Responsible for handling the interaction with the menu item and button for moving up.
     *
     * @since 19.11.2021
     */
    public void onMoveUpClicked() throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException, InvalidDirectionException {
        character.lookHere("up");
        character.moveUp();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for moving down.
     *
     * @since 19.11.2021
     */
    public void onMoveDownClicked() throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException, InvalidDirectionException {
        character.lookHere("down");
        character.moveDown();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for moving left.
     *
     * @since 19.11.2021
     */
    public void onMoveLeftClicked() throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException, InvalidDirectionException {
        character.lookHere("left");
        character.moveLeft();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for moving right.
     *
     * @since 19.11.2021
     */
    public void onMoveRightClicked() throws WallInFrontException, DrinkInFrontException, EndOfGameFieldException, CatInFrontException, InvalidDirectionException {
        character.lookHere("right");
        character.moveRight();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for picking the cat up.
     *
     * @since 19.11.2021
     */
    public void onPickCatUpClicked() throws HandsNotEmptyException, CatInFrontException, EndOfGameFieldException {
        character.takeCat();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for picking the drink up.
     *
     * @since 19.11.2021
     */
    public void onPickDrinkUpClicked() throws HandsNotEmptyException, DrinkInFrontException, EndOfGameFieldException {
        character.takeDrink();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for putting the cat down.
     *
     * @since 19.11.2021
     */
    public void onPutCatDownClicked() throws WallInFrontException, DrinkInFrontException, NoCatInHandException, CatInFrontException, EndOfGameFieldException {
        character.putCatDown();
    }

    /**
     * Responsible for handling the interaction with the menu item and button for putting the drink down.
     *
     * @since 19.11.2021
     */
    public void onPutDrinkDownClicked() throws WallInFrontException, NoDrinkInHandException, CatInFrontException, EndOfGameFieldException {
        character.putDrinkDown();
    }


    /**
     * Responsible for handling the request made by the user to execute the contents of the main method in the
     * currently compiled class.
     *
     * @since 15.01.2022
     */
    public void onStartButtonClicked() {
        simulationController.start(this);
        infoLabel.setText("Simulation läuft");
    }

    /**
     * Responsible for handling the request made by the user for pausing the current execution of the code written by the user.
     *
     * @since 08.02.2022
     */
    public void onPauseButtonClicked() {
        simulationController.pause();
        infoLabel.setText("Simulation pausiert");
    }

    /**
     * Responsible for handling the request made by the user for terminating the current execution of the code written by the user.
     *
     * @since 08.02.2022
     */
    public void onStopButtonClicked() {
        simulationController.stop();
        infoLabel.setText("Simulation beendet");
    }


    @Override
    public void update(Object object) {

        if (object.getClass() == NewFileHasBeenCreatedMessage.class) {
            currentFileName = ((NewFileHasBeenCreatedMessage) object).getNewFileName();
            newFileHasBeenCreated = true;
        }

        if (object.getClass() == SimulationHasStartedMessage.class || object.getClass() == SimulationHasResumedMessage.class) {
            startButton.setDisable(true);
            startMenuItem.setDisable(true);

            pauseButton.setDisable(false);
            pauseButton.setDisable(false);

            stopButton.setDisable(false);
            stopMenuItem.setDisable(false);
        }

        if (object.getClass() == SimulationHasBeenPausedMessage.class) {
            startButton.setDisable(false);
            startMenuItem.setDisable(false);

            pauseButton.setDisable(true);
            pauseMenuItem.setDisable(true);

            stopButton.setDisable(false);
            stopMenuItem.setDisable(false);
        }

        if (object.getClass() == SimulationHasEndedMessage.class) {
            startButton.setDisable(false);
            startMenuItem.setDisable(false);

            pauseButton.setDisable(true);
            pauseMenuItem.setDisable(true);

            stopButton.setDisable(true);
            stopMenuItem.setDisable(true);
        }
    }

    /*****************************************************
     *           FXML elements declaration               *
     *****************************************************/

    @FXML
    MenuItem newFile;

    @FXML
    MenuItem loadFile;

    @FXML
    MenuItem loadXML;

    @FXML
    MenuItem compileFile;

    @FXML
    MenuItem quit;

    @FXML
    MenuItem saveFile;

    @FXML
    MenuItem saveAsXml;

    @FXML
    MenuItem saveAsImage;

    @FXML
    MenuItem saveAsSerialize;

    @FXML
    MenuItem loadSerialize;

    @FXML
    MenuItem changeSizeField;

    @FXML
    RadioMenuItem placeCharaMenuItem;

    @FXML
    RadioMenuItem placeCatMenuItem;

    @FXML
    RadioMenuItem placeWallMenuItem;

    @FXML
    RadioMenuItem placeDrinkMenuItem;

    @FXML
    RadioMenuItem deleteContentMenuItem;

    @FXML
    MenuItem print;

    @FXML
    MenuItem moveUp;

    @FXML
    MenuItem moveDown;

    @FXML
    MenuItem moveLeft;

    @FXML
    MenuItem moveRight;

    @FXML
    RadioMenuItem pickCatUp;

    @FXML
    RadioMenuItem pickDrinkUp;

    @FXML
    RadioMenuItem putCatDown;

    @FXML
    RadioMenuItem putDrinkDown;

    @FXML
    RadioMenuItem startMenuItem;

    @FXML
    RadioMenuItem pauseMenuItem;

    @FXML
    RadioMenuItem stopMenuItem;

    @FXML
    ScrollPane scrollPane;

    @FXML
    TextArea textInput;

    @FXML
    Label infoLabel;

    @FXML
    Button compileFileButton;

    @FXML
    Button newFileButton;

    @FXML
    Button saveFileButton;

    @FXML
    Button openFileButton;

    @FXML
    Button changeSizeFieldButton;

    @FXML
    ToggleButton placeCharaButton;

    @FXML
    ToggleButton placeDrinkButton;

    @FXML
    ToggleButton placeCatButton;

    @FXML
    ToggleButton placeWallButton;

    @FXML
    ToggleButton deleteContentButton;

    @FXML
    Button pickDrinkUpButton;

    @FXML
    Button pickCatUpButton;

    @FXML
    Button putDrinkDownButton;

    @FXML
    Button putCatDownButton;

    @FXML
    Button moveUpButton;

    @FXML
    Button moveDownButton;

    @FXML
    Button moveLeftButton;

    @FXML
    Button moveRightButton;

    @FXML
    ToggleButton startButton;

    @FXML
    ToggleButton pauseButton;

    @FXML
    ToggleButton stopButton;

}
