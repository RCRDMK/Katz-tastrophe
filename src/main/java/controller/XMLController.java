package controller;

import game.GameField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLController {

    public void saveAsXML(GameField gameField, Stage stage) {//TODO Character Zustände abspeichern und laden
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/programs/"));
        fileChooser.setTitle("Speichern als XML-Datei");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".xml", "*.xml"));
        File selectedFile = fileChooser.showSaveDialog(stage);

        if (selectedFile != null) {
            try {
                DocumentBuilderFactory dbFactory =
                        DocumentBuilderFactory.newInstance();
                dbFactory.setValidating(true);

                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();


                Element root = doc.createElement("gamefield");
                doc.appendChild(root);

                Attr gamefieldRow = doc.createAttribute("row");
                gamefieldRow.setValue(String.valueOf(gameField.getRow()));
                root.setAttributeNode(gamefieldRow);

                Attr gamefieldColumn = doc.createAttribute("column");
                gamefieldColumn.setValue(String.valueOf(gameField.getColumn()));
                root.setAttributeNode(gamefieldColumn);


                for (int i = 0; i < gameField.getGameFieldArray().length; i++) {//setting value for elements which can only appear once in the array
                    for (int j = 0; j < gameField.getGameFieldArray()[0].length; j++) {
                        if (gameField.getGameFieldArray()[i][j].equals("^") || gameField.getGameFieldArray()[i][j].equals("v") || gameField.getGameFieldArray()[i][j].equals("<") || gameField.getGameFieldArray()[i][j].equals(">")) {

                            Element character = doc.createElement("character");
                            root.appendChild(character);

                            Attr characterRow = doc.createAttribute("row");
                            characterRow.setValue(Integer.toString(i));
                            character.setAttributeNode(characterRow);

                            Attr characterColumn = doc.createAttribute("column");
                            characterColumn.setValue(Integer.toString(j));
                            character.setAttributeNode(characterColumn);

                        } else if (gameField.getGameFieldArray()[i][j].equals("D")) {

                            Element drink = doc.createElement("drink");
                            root.appendChild(drink);

                            Attr drinkRow = doc.createAttribute("row");
                            drinkRow.setValue(Integer.toString(i));
                            drink.setAttributeNode(drinkRow);

                            Attr drinkColumn = doc.createAttribute("column");
                            drinkColumn.setValue(Integer.toString(j));
                            drink.setAttributeNode(drinkColumn);
                        }
                    }
                }

                for (int i = 0; i < gameField.getGameFieldArray().length; i++) {//setting value for elements which can appear multiple times in the array
                    for (int j = 0; j < gameField.getGameFieldArray()[0].length; j++) {
                        if (gameField.getGameFieldArray()[i][j].equals("W")) {
                            Element wall = doc.createElement("wall");
                            root.appendChild(wall);

                            Attr wallRow = doc.createAttribute("row");
                            wallRow.setValue(Integer.toString(i));
                            wall.setAttributeNode(wallRow);

                            Attr wallColumn = doc.createAttribute("column");
                            wallColumn.setValue(Integer.toString(j));
                            wall.setAttributeNode(wallColumn);

                        } else if (gameField.getGameFieldArray()[i][j].equals("C")) {
                            Element cat = doc.createElement("cat");
                            root.appendChild(cat);

                            Attr catRow = doc.createAttribute("row");
                            catRow.setValue(Integer.toString(i));
                            cat.setAttributeNode(catRow);

                            Attr catColumn = doc.createAttribute("column");
                            catColumn.setValue(Integer.toString(j));
                            cat.setAttributeNode(catColumn);
                        }
                    }
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                //DOMImplementation domImplementation = doc.getImplementation();
                //DocumentType documentType = domImplementation.createDocumentType("doctype", "", "test.dtd");
                //transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, documentType.getSystemId());

                DOMSource source = new DOMSource(doc);

                StreamResult result = new StreamResult(selectedFile);
                transformer.transform(source, result);

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadXML(GameField gameField, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "/programs/"));
        fileChooser.setTitle("XML-Datei laden");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".xml", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = builderFactory.newDocumentBuilder();
                Document document = builder.parse(selectedFile);
                document.getDocumentElement().normalize();


                NodeList nodeList = document.getElementsByTagName("gamefield");
                int row = Integer.parseInt(nodeList.item(0).getAttributes().getNamedItem("row").getNodeValue());
                int column = Integer.parseInt(nodeList.item(0).getAttributes().getNamedItem("column").getNodeValue());
                gameField.resizeGameFieldSize(row, column);


                nodeList = document.getElementsByTagName("wall");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    row = Integer.parseInt(nodeList.item(i).getAttributes().getNamedItem("row").getNodeValue());
                    column = Integer.parseInt(nodeList.item(i).getAttributes().getNamedItem("column").getNodeValue());
                    gameField.placeObjectsInGameField(row, column, "W");
                }


                nodeList = document.getElementsByTagName("cat");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    row = Integer.parseInt(nodeList.item(i).getAttributes().getNamedItem("row").getNodeValue());
                    column = Integer.parseInt(nodeList.item(i).getAttributes().getNamedItem("column").getNodeValue());
                    gameField.placeObjectsInGameField(row, column, "C");
                }

                nodeList = document.getElementsByTagName("character");
                row = Integer.parseInt(nodeList.item(0).getAttributes().getNamedItem("row").getNodeValue());
                column = Integer.parseInt(nodeList.item(0).getAttributes().getNamedItem("column").getNodeValue());
                gameField.placeObjectsInGameField(row, column, "^");

                nodeList = document.getElementsByTagName("drink");
                row = Integer.parseInt(nodeList.item(0).getAttributes().getNamedItem("row").getNodeValue());
                column = Integer.parseInt(nodeList.item(0).getAttributes().getNamedItem("column").getNodeValue());
                gameField.placeObjectsInGameField(row, column, "D");


            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
    }
}
