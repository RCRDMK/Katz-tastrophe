package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileController {

    public FileController() {

    }

    public void create() {
        try {
            File file = new File("src/main/program/test.java");
            if (file.createNewFile()) {
                System.out.println("created " + file.getName());
                //write();
            } else {
                System.out.println("file already created");
            }
            //read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            FileWriter writer = new FileWriter("src/main/program/test.txt");
            writer.write("Das ist ein Test");
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
}
