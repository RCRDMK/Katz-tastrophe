package presenter;

import controller.Program;
import javafx.event.ActionEvent;

public class LoadedFilePresenter extends ClientPresenter {

    String s;
    Program p;

    public void setTextInput(String code, String programName, Program program) {
        textInput.setText(code);
        s = programName;
        p = program;
    }

    @Override
    public void onSaveFileClicked(ActionEvent actionEvent) {
        p.saveFile(s, textInput.getText());
        System.out.println("Save " + p.getProgramName());
    }
}
