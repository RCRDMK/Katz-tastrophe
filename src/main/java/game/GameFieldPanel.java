package game;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class GameFieldPanel extends Region {

    //https://edencoding.com/javafx-canvas/

    static GraphicsContext graCon;
    private static GameField gameField;
    Canvas canvas;

    GameFieldPanel(GameField gameField) {
        this.gameField = gameField;
    }

    public static void color(Canvas canvas, Color color, int x, int y) {
        graCon = canvas.getGraphicsContext2D();
        graCon.setFill(color);
        graCon.fillRect(x, y, 25, 25);
    }

    public static void refreshElements(Canvas canvas) {
        graCon = canvas.getGraphicsContext2D();

        for (int i = 0; i < gameField.row - 1; i++) {
            for (int j = 0; j < gameField.column - 1; j++) {
                if (gameField.gameField[i][j].equals("^")) {
                    graCon.setFill(Color.RED);
                    graCon.fillRect(i * 25, j * 25, 25, 25);
                } else if (gameField.gameField[i][j].equals("W")) {
                    graCon.setFill(Color.BLACK);
                    graCon.fillRect(i * 25, j * 25, 25, 25);
                } else if (gameField.gameField[i][j].equals("C")) {
                    graCon.setFill(Color.YELLOW);
                    graCon.fillRect(i * 25, j * 25, 25, 25);
                } else if (gameField.gameField[i][j].equals("D")) {
                    graCon.setFill(Color.BLUE);
                    graCon.fillRect(i * 25, j * 25, 25, 25);
                }
            }
        }

    }
}
