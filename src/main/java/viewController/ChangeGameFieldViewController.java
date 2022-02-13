package viewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.GameField;

import java.util.regex.Pattern;

/**
 * This class is responsible for the fxml view in case the user decides to change the size of the gamefield.
 *
 * @since 21.11.2021
 */

public class ChangeGameFieldViewController {

    private GameField gameField;

    private boolean row = false;

    private boolean column = false;

    @FXML
    Label changeViewErrorLabel;

    @FXML
    Label changeViewRowCurrently;

    @FXML
    TextField changeViewTextFieldRow;

    @FXML
    Button changeViewAccept;

    @FXML
    Button changeViewCancel;

    @FXML
    TextField changeViewTextFieldColumn;

    @FXML
    Label changeViewColumnCurrently;


    public void init(GameField gameField) {
        this.gameField = gameField;
        changeViewRowCurrently.setText("Momentan " + gameField.getGameFieldArray().length);
        changeViewColumnCurrently.setText("Momentan " + gameField.getGameFieldArray()[0].length);
        changeViewAccept.setDisable(true);
        validateTextField();
    }

    /**
     * Responsible for validating if the textfields are both containing valid entries. If so, it enables the accept button.
     *
     * @since 21.11.2021
     */
    private void validateTextField() {

        changeViewTextFieldRow.textProperty().addListener(new ChangeListener<String>() {//checks the content of the row textfield
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.matches("^[2-9]$", changeViewTextFieldRow.getText())) {
                    row = true;
                } else if (!Pattern.matches("^[2-9]$", changeViewTextFieldRow.getText())) {
                    row = false;
                }
                if (row == true && column == true) {
                    changeViewAccept.setDisable(false);
                }
                if (row == false || column == false) {
                    changeViewAccept.setDisable(true);
                }
            }
        });

        changeViewTextFieldColumn.textProperty().addListener(new ChangeListener<String>() {//checks the content of the column textfield
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.matches("^[2-9]$", changeViewTextFieldColumn.getText())) {
                    column = true;
                } else if (!Pattern.matches("^[2-9]$", changeViewTextFieldColumn.getText())) {
                    column = false;
                }
                if (row == true && column == true) {
                    changeViewAccept.setDisable(false);
                }
                if (row == false || column == false) {
                    changeViewAccept.setDisable(true);
                }
            }
        });

    }

    /**
     * Responsible for handling the action event when the cancel button was being clicked.
     *
     * @since 21.11.2021
     */
    public void onChangeViewCancelClicked() {
        changeViewCancel.getScene().getWindow().hide();
    }

    /**
     * Responsible for handling the action event when the accept button was being clicked.
     * <p>
     * When this method is being called, it converts the entries in the textfields into int values. It then calls the
     * resize method of the GameField class while using the before created int values as parameters. Afterwards, it calls
     * the checkCharacter method of the GameField class before it hides the fxml view.
     *
     * @since 21.11.2021
     */
    public void onChangeViewAcceptClicked() {
        int rows = Integer.valueOf(changeViewTextFieldRow.getText());
        int columns = Integer.valueOf(changeViewTextFieldColumn.getText());
        gameField.resizeGameFieldSize(rows, columns);
        gameField.checkIfCharacterExists();
        changeViewAccept.getScene().getWindow().hide();
    }
}
