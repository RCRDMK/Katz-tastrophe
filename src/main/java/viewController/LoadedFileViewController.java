package viewController;

import controller.FileController;
import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * This class is responsible for showing and handling all request if the user decides to load a program. This program
 * will then be loaded through this fxml controller. Because it inherits the main fxml controller and all of its methods,
 * this classes methods will be mainly used to overwrite methods from the main fxml controller in benefit for this fxml
 * controller.
 *
 * @since 28.12.2021
 */
public class LoadedFileViewController extends MainViewController {

    private String loadedProgramName;
    private FileController fileController;

    /**
     * Responsible for setting up the view and the value of the textarea.
     * <p>
     * When this method is called, it sets the given string in the parameter as the value of the textarea. It also
     * initiates the variables, making sure that this scene is linked to the of the loaded program. Because of this, it
     * can't overwrite other programs unless it's explicitly ordered to.
     *
     * @param code           the code, written by the user, which is to be depicted in the textarea.
     * @param programName    the name of program, loaded in this view.
     * @param fileController the fileController, to make it possible to interact with the file from this controller,
     *                       rather than having to go through the methods in the main controller.
     * @since 28.12.2021
     */
    public void setTextInput(String code, String programName, FileController fileController) throws IOException {
        super.initialize();
        loadedProgramName = programName;
        this.fileController = fileController;
        textInput.setText(code);
    }

    /**
     * Responsible for handling the request from the user to save the file.
     * <p>
     * It doesn't have any different function than it has in the main controller, but by overwriting the method and
     * handling it here, it can only save the depicted file isn't overwriting a different one by accident, thanks to the
     * loadedProgramName variable, which is set once and then doesn't change again.
     *
     * @param actionEvent the interaction of the user with the FXML Element
     * @since 03.01.2022
     */
    @Override
    public void onSaveFileClicked(ActionEvent actionEvent) {
        fileController.saveFile(loadedProgramName, textInput.getText());
        System.out.println("Save " + fileController.getProgramName());
    }
}
