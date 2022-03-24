/*
 * Katz-tastrophe - a miniature programming learn environment
 * Copyright (C) 2022 RCRDMK
 *
 * This program (Katz-tastrophe) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program (Katz-tastrophe) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. See LICENSE File in the main directory. If not, see <https://www.gnu.org/licenses/>.
 */

import controller.AlertController;
import controller.FileController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.GameField;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        try {

            FXMLLoader loader = new FXMLLoader(GameField.class.getClassLoader().getResource("fxml/MainView.fxml"));

            if (Files.notExists(Path.of("programs"))) {
                Files.createDirectory(Path.of("programs"));
            }

            FileController fileController = new FileController();
            fileController.fileWhenFirstOpened();

            Parent root = loader.load();
            primaryStage.setScene(new Scene(root, 1150, 400));

            primaryStage.setTitle(fileController.getDefaultName().replace("_", " "));

            primaryStage.show();

            primaryStage.setMaxHeight(500);
            primaryStage.setMaxWidth(primaryStage.getWidth());

            primaryStage.setMinHeight(450);
            primaryStage.setMinWidth(1150);



        } catch (Exception e) {
            AlertController alertController = new AlertController();
            StringWriter errorMessage = new StringWriter();

            e.printStackTrace(new PrintWriter(errorMessage));
            alertController.exceptionAlert(Alert.AlertType.ERROR, "Ein Fehler ist aufgetreten", String.valueOf(errorMessage));
        }
    }
}
