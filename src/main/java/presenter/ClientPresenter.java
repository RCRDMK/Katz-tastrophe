package presenter;

import game.GameFieldPanel;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClientPresenter extends Application {

    public static final String fxml = "/fxml/ClientView.fxml";
    @FXML
    public static Pane test;
    static GameFieldPanel gameFieldPanel;
    Canvas canvas;
    GraphicsContext graCon;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ClientView.fxml"));
        stage.setScene(new Scene(root, 1150, 400));

        canvas = new Canvas(400, 600);
        graCon = canvas.getGraphicsContext2D();

        stage.setTitle("Katz-tastrophe");

        stage.show();

        stage.setMaxHeight(500);
        stage.setMaxWidth(stage.getWidth());

        stage.setMinHeight(450);
        stage.setMinWidth(1150);


    }
}
