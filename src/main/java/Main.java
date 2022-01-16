import controller.FileController;
import game.GameField;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(GameField.class.getClassLoader().getResource("fxml/MainView.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 1150, 400));

        FileController fileController = new FileController();
        primaryStage.setTitle(fileController.getProgramName().replace("_", " "));

        primaryStage.show();

        primaryStage.setMaxHeight(500);
        primaryStage.setMaxWidth(primaryStage.getWidth());

        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(1150);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //fileController.saveFile(fileController.getProgramName(), );
            }
        });
    }
}
