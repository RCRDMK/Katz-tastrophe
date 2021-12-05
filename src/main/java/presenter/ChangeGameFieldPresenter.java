package presenter;

import game.GameField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pattern.ObserverInterface;

public class ChangeGameFieldPresenter implements ObserverInterface {

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

    public void initialize() {
        gameField.addObserver(this);
        //changeViewRowCurrently.setText("Momentan " + gameField.getGameFieldArray().length);
        //changeViewColumnCurrently.setText("Momentan " + gameField.getGameFieldArray()[0].length);
        /*changeViewAccept.setDisable(true);
        changeViewTextFieldRow.focusedProperty().addListener((arg0, oldValue, newValue) -> {
           if (!newValue) {
               if (!changeViewTextFieldRow.getText().matches("[2 - 10]")){
                   System.out.println(changeViewTextFieldRow.getText().matches("[2 - 10]"));
               }
           }
        });*/
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
            changeViewAccept.getScene().getWindow().hide();
        } else {
            changeViewErrorLabel.setVisible(true);
        }
    }

    @Override
    public void update(Object object) {
        gameField = (GameField) object;
    }
}
