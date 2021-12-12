package controller;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class FileController {

    Path javaSource = null;

    File userDirectory = new File(System.getProperty("user.dir"));
    String programFolder = "/programs/";
    String fileType = ".java";

    String fileClassImport = "import game.GameField;\n" +
            "import game.GameFieldPanel;\n";
    String fileClassDeclaration = "public class ";
    String fileClassInheritance = " extends game.GameCharacter {\n\n";
    String fileContent = "GameField gameField = new GameField(6,6);" +
            "\nGameFieldPanel gameFieldPanel = new GameFieldPanel(gameField, 250, 250);\n" +
            "String textfieldContent;\n\n" +
            "    public GameFieldPanel getGameFieldPanel() {\n" +
            "        return gameFieldPanel;\n" +
            "    }\n" +
            "\n" +
            "    public String getTextfieldContent() {\n" +
            "        return textfieldContent;\n" +
            "    }\n" +
            "\n" +
            "    public void setTextfieldContent(String textfieldContent) {\n" +
            "        this.textfieldContent = textfieldContent;\n" +
            "    }";
    String fileClassEnd = "\n}";

    //TODO vll nur die ScrollPane und TextArea nur abspeichern?

    public FileController() {

    }

    public void create(String fileName//, GameFieldPanel gameFieldPanel
    ) {
        try {
            File file = new File(userDirectory + programFolder + fileName + fileType);
            if (file.createNewFile()) {
                System.out.println("created " + file.getName());
                write(fileName, fileClassImport + fileClassDeclaration + fileName + fileClassInheritance + fileContent + fileClassEnd);
            } else {
                System.out.println("file already created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String fileName, String contentToWrite) {
        try {
            FileWriter writer = new FileWriter(userDirectory + programFolder + fileName + fileType);
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
    public void compileTest() throws Exception {
        final String file1 = "programs/Test.java";
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        boolean success =
                javac.run(null, null, err, file1) == 0;
        if (!success) {
            System.out.println(err.toString());
        } else {
            System.out.println("ok");
            loadClass();
        }
    }

    public void loadClass() throws Exception {
        URL classUrl = new URL("file:///" + userDirectory + programFolder);
        URL[] urls = {classUrl};
        URLClassLoader classLoader = new URLClassLoader(urls);
        Class c = classLoader.loadClass("Test");
        System.out.println("loaded");
        Method method = c.getMethod("te", String.class);
        Object o = method.invoke(c.getDeclaredConstructor().newInstance(), "hi");


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

}
