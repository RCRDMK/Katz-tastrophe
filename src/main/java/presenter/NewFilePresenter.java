package presenter;

import controller.FileController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;

public class NewFilePresenter {

    @FXML
    Button newFileCancel;

    @FXML
    Button newFileAccept;

    @FXML
    TextField newFileText;

    FileController fileController = new FileController();

    public void onNewFileCancelClicked(ActionEvent actionEvent) {
        newFileCancel.getScene().getWindow().hide();
    }

    public void onNewFileAcceptedClicked(ActionEvent actionEvent) throws IOException {
        //fileController.create(newFileText.getText());
        fileController.compile(new File("programs/Test.java"));
        newFileAccept.getScene().getWindow().hide();

    }
}
