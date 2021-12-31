package controller;

import game.CharaWrapper;
import game.GameCharacter;
import game.GameField;

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

public class Program {

    String programName = "neue_Katztastrophe";

    File userDirectory = new File(System.getProperty("user.dir"));
    String programFolder = "/programs/";
    String fileType = ".java";


    public void fileWhenFirstOpened() {
        createFile(programName);
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
            programName = fileName;
            System.out.println(programName);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getPrefix(fileName) + " void main(){   }" + getPostfix());
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
            programName = fileName;
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

    public String loadTextForEditor(String fileName) {
        File file = new File(userDirectory + programFolder + fileName + fileType);
        programName = fileName;
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
    public CharaWrapper compileFileAndSetNewCharacter(String fileName, GameField gameField, GameCharacter gameCharacter) {
        String file = userDirectory + programFolder + fileName + fileType;
        programName = fileName;
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        boolean success =
                javac.run(null, null, err, file) == 0;
        if (!success) {
            System.out.println(err.toString());
        } else {
            System.out.println("ok");
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
        System.out.println("loaded");
        CharaWrapper newCharacter = new CharaWrapper();
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
        newCharacter.setGameCharacter(gameField, gameCharacter);//Alten Character austauschen durch newCharacter. Vll als Return
        return newCharacter;
    }

    /**
     * Responsible for compiling .java files to .class files to, later then, read the, from the user, declared methods in
     * the .class file.
     *
     * @param fileName name of the .java file which is to be compiled
     * @return compiled .class file from which the declared methods can be read
     * @since 18.12.2021
     */
    public Class compiledMethods(String fileName) {
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
        return c;
    }


    public String getPrefix(String fileName) {
        return "public class " + fileName + " extends game.CharaWrapper {\n\n public";
    }

    public String getPostfix() {
        return "\n}";
    }

    public String getProgramTitleName() {
        if (!programName.contains("Katz-tastrophe")) {
            return programName + " Katz-tastrophe";
        } else {
            return programName;
        }
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }
}
