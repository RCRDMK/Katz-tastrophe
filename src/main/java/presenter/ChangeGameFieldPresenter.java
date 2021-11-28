package presenter;

import game.GameField;
import game.GameFieldPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ChangeGameFieldPresenter {

    static GameField gameField;
    static GameFieldPanel gameFieldPanel;

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

    public static void setGameField(GameField gameField) {
        ChangeGameFieldPresenter.gameField = gameField;
    }

    public static void setGameFieldPanel(GameFieldPanel gameFieldPanel) {
        ChangeGameFieldPresenter.gameFieldPanel = gameFieldPanel;
    }

    public void initialize() {
        changeViewRowCurrently.setText("Momentan " + Integer.toString(gameField.getRow()));
        changeViewColumnCurrently.setText("Momentan " + Integer.toString(gameField.getColumn()));
    }

    public void onChangeViewCancelClicked(ActionEvent actionEvent) {
        changeViewCancel.getScene().getWindow().hide();
    }

    public void onChangeViewAcceptClicked(ActionEvent actionEvent) {
        if (changeViewTextFieldRow.getText().matches("[2-9]$") && changeViewTextFieldColumn.getText().matches("[2-9]$")) {
            int rows = Integer.valueOf(changeViewTextFieldRow.getText());
            int columns = Integer.valueOf(changeViewTextFieldColumn.getText());
            gameField.resizeGameFieldSize(rows, columns);
            gameField.checkIfCharacterExists();
            gameFieldPanel.drawObjectsOnGameField();
            changeViewAccept.getScene().getWindow().hide();
        } else {
            changeViewErrorLabel.setVisible(true);
        }
    }
}
