package presenter;

import controller.FileController;
import javafx.event.ActionEvent;

public class LoadedFilePresenter extends MainPresenter {

    String loadedProgramName;
    FileController fileController;

    public void setTextInput(String code, String programName, FileController fileController) {
        textInput.setText(code);
        loadedProgramName = programName;
        this.fileController = fileController;
    }

    @Override
    public void onSaveFileClicked(ActionEvent actionEvent) {
        fileController.saveFile(loadedProgramName, textInput.getText());
        System.out.println("Save " + fileController.getProgramName());
    }
}
