package viewController;

import controller.FileController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.messages.NewFileHasBeenCreatedMessage;

import javax.lang.model.SourceVersion;

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
     * @param actionEvent the interaction of the user with the FXML Element
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
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 03.12.2021
     */
    //TODO Dynamisch Button dis- und enablen
    public void onNewFileAcceptedClicked(ActionEvent actionEvent) {
        if (validateName(newFileText.getText())) {
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
