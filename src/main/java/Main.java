import controller.Program;
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
        FXMLLoader loader = new FXMLLoader(GameField.class.getClassLoader().getResource("fxml/ClientView.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 1150, 400));

        Program program = new Program();
        primaryStage.setTitle(program.getProgramTitleName());

        primaryStage.show();

        primaryStage.setMaxHeight(500);
        primaryStage.setMaxWidth(primaryStage.getWidth());

        primaryStage.setMinHeight(450);
        primaryStage.setMinWidth(1150);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //program.saveFile(program.getProgramName(), );
            }
        });
    }
}
