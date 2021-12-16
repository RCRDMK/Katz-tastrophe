package presenter;

import controller.Program;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.lang.model.SourceVersion;
import java.io.IOException;

public class NewFilePresenter {

    @FXML
    Button newFileCancel;

    @FXML
    Button newFileAccept;

    @FXML
    TextField newFileText;

    Program program = new Program();

    public void onNewFileCancelClicked(ActionEvent actionEvent) {
        newFileCancel.getScene().getWindow().hide();
    }

    //TODO Dynamisch Button dis- und enablen
    public void onNewFileAcceptedClicked(ActionEvent actionEvent) throws IOException {
        if (validateName(newFileText.getText())) {

            program.createFile(newFileText.getText());
            newFileAccept.getScene().getWindow().hide();
        }
    }

    public boolean validateName(String name) {
        return SourceVersion.isIdentifier(name) && !SourceVersion.isKeyword(name);
    }
}
