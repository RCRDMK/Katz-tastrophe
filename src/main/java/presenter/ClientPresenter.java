package presenter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientPresenter extends Application {

    public static final String fxml = "/fxml/ClientView.fxml";

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ClientView.fxml"));
        stage.setScene(new Scene(root, 1150 , 400));
        stage.setTitle("Katz-tastrophe");
        stage.show();

        stage.setMaxHeight(500);
        stage.setMaxWidth(stage.getWidth());

        stage.setMinHeight(450);
        stage.setMinWidth(1150);

    }
}
