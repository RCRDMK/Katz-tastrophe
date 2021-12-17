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

public class Program {

    String programName = "neue Katz-tastrophe";

    File userDirectory = new File(System.getProperty("user.dir"));
    String programFolder = "/programs/";
    String fileType = ".java";

    StringBuilder stringBuilder = new StringBuilder();

    public void createFile(String fileName) {
        File file = new File(userDirectory + programFolder + fileName + fileType);
        try (FileWriter writer = new FileWriter(file)) {
            programName = fileName;
            stringBuilder.append(getPrefix(fileName) + " void main(){   }" + getPostfix());
            writer.write(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeInFile(String fileName, String contentToWrite) {
        File file = new File(userDirectory + programFolder + fileName + fileType);
        try (FileWriter writer = new FileWriter(file)) {
            programName = fileName;
            stringBuilder.append(getPrefix(fileName) + " " + contentToWrite + getPostfix());
            writer.write(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile(String fileName, String contentToSave) {
        writeInFile(fileName, contentToSave);
    }

    public String loadTextForEditor(String fileName) {
        File file = new File(userDirectory + programFolder + fileName + fileType);
        String replacePostfix = "";
        try (FileReader reader = new FileReader(file)) {
            char[] strings = new char[(int) file.length()];
            reader.read(strings);

            String charToString = String.valueOf(strings);
            String replacePrefix = charToString.replace(getPrefix(fileName), "");
            replacePostfix = replacePrefix.replace(getPostfix(), "");
            programName = fileName;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return replacePostfix;
    }

    //Vorlesungsfolie UE35-Tools-Compiler, Seite 4
    public Class compileFile(String fileName, GameField gameField, GameCharacter gameCharacter) {
        String file = userDirectory + programFolder + fileName + fileType;
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
        CharaWrapper newActor = null;
        try {
            newActor = (CharaWrapper) c.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        newActor.setGameCharacter(gameField, gameCharacter);//Alten Character austauschen durch newActor. Vll als Return
        return c;
    }


    public String getPrefix(String fileName) {
        return "public class " + fileName + " extends game.CharaWrapper {\n\n public";
    }

    public String getPostfix() {
        return "\n}";
    }

    public String getProgramName() {
        return programName;
    }
}
