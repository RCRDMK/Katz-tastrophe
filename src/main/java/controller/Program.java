package controller;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
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

    public void loadFile(String fileName) {
        File file = new File(userDirectory + programFolder + fileName + fileType);
        try (FileReader reader = new FileReader(file)) {
            char[] strings = new char[(int) file.length()];
            reader.read(strings);

            String charToString = String.valueOf(strings);
            String replacePrefix = charToString.replace(getPrefix(fileName), "");
            String replacePostfix = replacePrefix.replace(getPostfix(), "");
            programName = fileName;
            System.out.println(replacePostfix);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Vorlesungsfolie UE35-Tools-Compiler, Seite 4
    public Class compileFile(String fileName) throws ClassNotFoundException, MalformedURLException {
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

        URL classUrl = new URL("file:///" + userDirectory + programFolder);
        URL[] urls = {classUrl};
        URLClassLoader classLoader = new URLClassLoader(urls);
        Class c = classLoader.loadClass(fileName);
        System.out.println("loaded");
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
