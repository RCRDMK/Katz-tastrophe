package controller;

import presenter.ClientPresenter;

import javax.tools.*;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class FileController {

    Path javaSource = null;

    String directory = "programs/";
    String fileType = ".java";

    String fileHeader = "public class ";
    String initialFileContent = " extends game.GameCharacter {\n \n public static void main(String[] args) {\n" +
            "       System.out.println(\"Hello World!\");" +
            "    \n}\n}";


    public FileController() {

    }

    public void create(String fileName) {
        try {
            File file = new File(directory + fileName + fileType);
            if (file.createNewFile()) {
                System.out.println("created " + file.getName());
                write(fileName, fileHeader + fileName + initialFileContent);
            } else {
                System.out.println("file already created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String fileName, String contentToWrite) {
        try {
            FileWriter writer = new FileWriter(directory + fileName + fileType);
            writer.write(contentToWrite);
            writer.close();
            System.out.println("written");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read(File file) {
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    //Vorlesungsfolie UE35-Tools-Compiler, Seite 4
    public void compileTest() throws IOException {
        final String file1 = "programs/Test.java";
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        boolean success =
                javac.run(null, null, err, file1) == 0;
        if (!success) {
            System.out.println(err.toString());
        } else {
            System.out.println("ok");
        }
    }

    //https://openbook.rheinwerk-verlag.de/java8/18_002.html#u18.2
    public void compile(File file) throws IOException {
        //javaSource = Paths.get(file);

        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager javaFileManager = javaCompiler.getStandardFileManager(diagnosticCollector, null, null);
        Iterable<? extends JavaFileObject> files = Arrays.asList();
        JavaCompiler.CompilationTask compilationTask = javaCompiler.getTask(null, javaFileManager, diagnosticCollector, null, null, files);
        compilationTask.call();


    }

    private JavaCompiler.CompilationTask getCompilationTask(Writer writer, JavaFileManager javaFileManager, DiagnosticListener<? super JavaFileObject> diagnosticListener, Iterable<String> options, Iterable<String> classes, Iterable<? extends JavaFileObject> compilationUnits) throws IOException {
        URL[] url = {javaSource.toAbsolutePath().getParent().toUri().toURL()};
        try (URLClassLoader classLoader = new URLClassLoader(url)) {
            Class.forName("Test", true, classLoader);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Files.delete(javaSource);
        return null;
    }

    public void saveTest(int row) throws IOException {
        FileOutputStream saveFile = new FileOutputStream("src/main/programs/obj.sav");
        ObjectOutputStream saveObject = new ObjectOutputStream(saveFile);
        saveObject.writeObject(ClientPresenter.class);
        saveObject.close();
    }

    public void loadTest() throws Exception {
        FileInputStream readFile = new FileInputStream("src/main/programs/obj.sav");
        ObjectInputStream readObject = new ObjectInputStream(readFile);
        System.out.println(readObject.readObject());

    }

}
