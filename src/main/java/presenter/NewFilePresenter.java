package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NewFilePresenter {

    @FXML
    Button newFileCancel;

    public void onNewFileCancelClicked(ActionEvent actionEvent) {
        newFileCancel.getScene().getWindow().hide();
    }
}
