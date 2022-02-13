package controller;

import javafx.scene.control.Alert;
import model.CharaWrapper;
import model.GameCharacter;
import model.GameField;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * This class is responsible managing and creating new files which are mandatory to run the code the user inputs into
 * the textarea in the client
 *
 * @since 15.12.2021
 */

public class FileController {

    final String defaultName = "neue_Katztastrophe";

    private File userDirectory = new File(System.getProperty("user.dir"));
    private String programFolder = "/programs/";
    private String fileType = ".java";

    public void fileWhenFirstOpened() {
        createFile(defaultName);
    }

    /**
     * Responsible for creating a new file
     * <p>
     * When called, this method creates a new file with all the content it needs.
     *
     * @param fileName the name of the new file
     * @since 15.12.2021
     */
    public void createFile(String fileName) {
        File file = new File(userDirectory + programFolder + fileName + fileType);
        try (FileWriter writer = new FileWriter(file)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getPrefix(fileName) + "public void main(){   }" + getPostfix());
            writer.write(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Responsible for writing the contents of the textarea in the current file.
     *
     * @param fileName       name of the file in which is being written
     * @param contentToWrite content which is about to be written in the file
     * @since 15.12.2021
     */
    private void writeInFile(String fileName, String contentToWrite) {
        File file = new File(userDirectory + programFolder + fileName + fileType);
        try (FileWriter writer = new FileWriter(file)) {
            String s = getPrefix(fileName) + contentToWrite + getPostfix();
            writer.write(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Responsible for calling the writeInFile method.
     *
     * @param fileName      name of the file which is being saved
     * @param contentToSave content which is to be saved
     * @since 15.12.2021
     */
    public void saveFile(String fileName, String contentToSave) {
        writeInFile(fileName, contentToSave);
    }

    /**
     * Responsible for loading the code, written by the user, in the textarea.
     * <p>
     * It reads the string inside the given file, removes the prefix and the postfix and then returns it.
     *
     * @param fileName The given name of the file which code should be read
     * @return the code inside the chosen file as a string. It removes the prefix, such as class name and inheritance,
     * and postfix, closing semicolon of the class, so that only the main method and methods written by the user during
     * runtime will be depicted.
     * @since 18.12.2021
     */
    public String loadTextForEditor(String fileName) {
        File file = new File(userDirectory + programFolder + fileName + fileType);
        String replacePrefix = "";
        String replacePostfix = "";
        try (FileReader reader = new FileReader(file)) {
            char[] strings = new char[(int) file.length()];
            reader.read(strings);

            String charToString = String.valueOf(strings);
            replacePrefix = charToString.replace(getPrefix(fileName), "");
            replacePostfix = replacePrefix.replace(getPostfix(), "");


        } catch (IOException e) {
            e.printStackTrace();
        }
        return replacePostfix;
    }

    /**
     * Responsible for compiling .java files into .class files and replacing the current game character instance with
     * the newly created one.
     *
     * @param fileName      name of the .java file which is to be compiled
     * @param gameField     value of the gameField instance to create a new game character
     * @param gameCharacter value of the gameCharacter instance to create a new game character
     * @return value of the newly created game character
     * @since 15.12.2021
     */
    //Vorlesungsfolie UE35-Tools-Compiler, Seite 4
    public CharaWrapper compileFileAndSetNewCharacter(String fileName, GameField gameField, GameCharacter gameCharacter, GameFieldPanelController gameFieldPanelController) {
        String file = userDirectory + programFolder + fileName + fileType;
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        boolean success =
                javac.run(null, null, err, file) == 0;
        if (!success) {
            AlertController alertController = new AlertController();
            alertController.compileAlert(Alert.AlertType.ERROR, "Datei konnte nicht kompiliert werden", err.toString());
        } else {
            System.out.println("Syntax of the class seems okay");
        }

        URL classUrl = null;
        try {
            classUrl = new URL("file:///" + userDirectory + programFolder);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URL[] urls = {classUrl};
        URLClassLoader classLoader = new URLClassLoader(urls);
        Class c = null;
        try {
            c = classLoader.loadClass(fileName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("...has been compiled");
        CharaWrapper newCharacter = new CharaWrapper(gameField);
        try {
            newCharacter = (CharaWrapper) c.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        newCharacter.setGameCharacter(gameField, gameCharacter);
        gameFieldPanelController.setCharacter(newCharacter);
        return newCharacter;
    }

    public String getPrefix(String fileName) {
        return "public class " + fileName + " extends model.CharaWrapper {\n\n";
    }

    public String getPostfix() {
        return "\n}";
    }

    public String getDefaultName() {
        return defaultName;
    }
}
