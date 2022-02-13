package viewController;

import controller.FileController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import model.messages.NewFileHasBeenCreatedMessage;

import javax.lang.model.SourceVersion;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * This class is responsible for the fxml view which is being called when the user wishes to create a new file.
 *
 * @since 03.12.2021
 */

public class NewFileViewController {


    @FXML
    Button newFileCancel;

    @FXML
    Button newFileAccept;

    @FXML
    TextField newFileText;

    FileController fileController = new FileController();

    MainViewController mainViewController;

    /**
     * Responsible for handling the action event when the cancel button was being clicked.
     *
     * @param actionEvent the interaction of the user with the FXML test.Element
     * @since 03.12.2021
     */
    public void onNewFileCancelClicked(ActionEvent actionEvent) {
        newFileCancel.getScene().getWindow().hide();
    }

    /**
     * Responsible for handling the action event when the cancel button was being clicked.
     * <p>
     * When this method is called, it first checks if the entered name is valid as name. If so, it then calls the
     * create method of the FileController class. After that, it hides the fxml view, and lastly it creates a
     * NewFileHasBeenCreated message, which is being sent to the MainViewController.
     * <p>
     * If there already exist a file with the same name, it warns the user and asks him if he really does want to
     * overwrite the file.
     *
     * @param actionEvent the interaction of the user with the FXML test.Element
     * @since 03.12.2021
     */
    //TODO Dynamisch Button dis- und enablen
    public void onNewFileAcceptedClicked(ActionEvent actionEvent) {
        if (validateName(newFileText.getText())) {
            if (Files.exists(Path.of("programs/" + newFileText.getText() + ".java"))) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Datei überschreiben?");
                alert.setContentText("Achtung!\nDu bist dabei eine Datei mit dem selben Namen zu überschreiben. Bist du wirklich sicher, dass sie überschreiben möchtest?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                    newFileAccept.getScene().getWindow().hide();
                    return;
                }
            }
            fileController.createFile(newFileText.getText());
            newFileAccept.getScene().getWindow().hide();
            new NewFileHasBeenCreatedMessage(newFileText.getText(), mainViewController);
        }
    }

    /**
     * Responsible for checking if the given parameter is a keyword (like new, static, class, etc.) in the Java language
     * or not.
     *
     * @param name value which has to be checked if it contains keywords or not
     * @return boolean value if the given parameter can be used as a class name in Java
     * @since 07.12.2021
     */
    public boolean validateName(String name) {
        return SourceVersion.isIdentifier(name) && !SourceVersion.isKeyword(name);
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }
}
