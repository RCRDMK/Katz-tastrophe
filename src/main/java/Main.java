import controller.AlertController;
import controller.FileController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.GameField;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(GameField.class.getClassLoader().getResource("fxml/MainView.fxml"));

            Parent root = loader.load();
            primaryStage.setScene(new Scene(root, 1150, 400));

            FileController fileController = new FileController();
            fileController.fileWhenFirstOpened();
            primaryStage.setTitle(fileController.getDefaultName().replace("_", " "));

            primaryStage.show();

            primaryStage.setMaxHeight(500);
            primaryStage.setMaxWidth(primaryStage.getWidth());

            primaryStage.setMinHeight(450);
            primaryStage.setMinWidth(1150);

        } catch (Throwable t) {
            AlertController alertController = new AlertController();
            StringWriter errorMessage = new StringWriter();

            t.printStackTrace(new PrintWriter(errorMessage));
            alertController.exceptionAlert(Alert.AlertType.ERROR, "Ein Fehler ist aufgetreten", String.valueOf(errorMessage));
        }
    }
}
