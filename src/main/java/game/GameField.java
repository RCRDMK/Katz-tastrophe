package game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameField {

    static public String[][] gameField;
    public static int row;
    public static int column;
    static String cat = "C";
    static String wall = "W";
    static String drink = "D";
    static String character = "^";

    public static void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(GameField.class.getClassLoader().getResource("fxml/ClientView.fxml"));
        stage.setScene(new Scene(root, 1150, 400));

        stage.setTitle("Katz-tastrophe");

        stage.show();

        stage.setMaxHeight(500);
        stage.setMaxWidth(stage.getWidth());

        stage.setMinHeight(450);
        stage.setMinWidth(1150);
    }

    public static void printGameField() {
        //responsible for filling indexes which have the value null with an x
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (gameField[i][j] == null) {
                    gameField[i][j] = "x";
                }
            }
            //Hier kann auch ein normaler "toString" ausgeführt werden.
            //Da aber evtl später mit Cells gearbeitet wird, bleibt dieser Befehl als spätere Spickhilfe erhalten
            //System.out.println(Arrays.deepToString(this.gameField[i]));

        }
        //System.out.println("Bewege dich mit Richtungsanweisungen wie 'hoch', 'runter', 'links', 'rechts' oder rufe die Hilfe mit 'hilfe' auf.");
        //System.out.println();
    }

    public static void GameField(int rows, int columns) {
        row = rows;
        column = columns;
        gameField = new String[row][column];

        placesElementsInField(5, 5, cat);
        placesElementsInField(3, 1, wall);
        placesElementsInField(3, 2, cat);
        placesElementsInField(4, 1, drink);
        placesElementsInField(3, 3, character);
    }

    static void placesElementsInField(int row, int column, String element) {
        gameField[row][column] = element;
    }

    //should be called when the gamefield sizes changes
    public void checkIfCharacterOutOfBounds() {
        for (int i = 0; i <= row; i++) {
            for (int j = 0; j <= column; j++) {
                if (!gameField[i][j].equals("^")) {
                    gameField[0][0] = "^";
                } else if (!gameField[i][j].equals("v")) {
                    gameField[0][0] = "^";
                } else if (!gameField[i][j].equals("<")) {
                    gameField[0][0] = "^";
                } else if (!gameField[i][j].equals(">")) {
                    gameField[0][0] = "^";
                }
            }
        }
    }
}
