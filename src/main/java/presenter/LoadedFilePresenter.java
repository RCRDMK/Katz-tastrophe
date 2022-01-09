package presenter;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class LoadedFilePresenter extends ClientPresenter {
    @FXML
    ScrollPane scrollPane;

    @FXML
    TextArea textInput;


    public void setTextInput(String code) {
        textInput.setText(code);
    }
}
