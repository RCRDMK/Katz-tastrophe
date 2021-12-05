package presenter;

import controller.FileController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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

    public void onNewFileAcceptedClicked(ActionEvent actionEvent) {
        fileController.create(newFileText.getText());
        newFileAccept.getScene().getWindow().hide();

    }
}
