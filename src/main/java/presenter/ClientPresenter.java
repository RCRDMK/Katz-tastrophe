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
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ClientView.fxml"));
        stage.setScene(new Scene(root, 200, 200));
        stage.show();

    }
}
