package presenter;

import game.GameField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class ChangeGameFieldPresenter {

    private GameField gameField;

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

    //TODO Button dynamisch en- und disablen

    public void init(GameField gameField) {
        this.gameField = gameField;
        changeViewRowCurrently.setText("Momentan " + gameField.getGameFieldArray().length);
        changeViewColumnCurrently.setText("Momentan " + gameField.getGameFieldArray()[0].length);
        changeViewAccept.setDisable(true);
        validateTextField();
    }

    public void validateTextField() {
        changeViewTextFieldRow.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (Pattern.matches("[2-9]", changeViewTextFieldRow.getText())) {
                    changeViewAccept.setDisable(false);
                }
            }
        });

    }

    public void onChangeViewCancelClicked(ActionEvent actionEvent) {
        changeViewCancel.getScene().getWindow().hide();
    }

    public void onChangeViewAcceptClicked(ActionEvent actionEvent) {
        if (changeViewTextFieldRow.getText().matches("^[2 - 9]$") && changeViewTextFieldColumn.getText().matches("^[2 - 9]$")) {
            changeViewAccept.setDisable(false);
            int rows = Integer.valueOf(changeViewTextFieldRow.getText());
            int columns = Integer.valueOf(changeViewTextFieldColumn.getText());
            gameField.resizeGameFieldSize(rows, columns);
            gameField.checkIfCharacterExists();
            changeViewAccept.getScene().getWindow().hide();
        } else {
            changeViewErrorLabel.setVisible(true);
        }
    }


}
