package controller;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLController {

    public void saveAsXML() {
        try {

            File file = new File("/Users/joker/IdeaProjects/Katz-tastrophe/programs/te.xml");
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            dbFactory.setValidating(true);

            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();


            Element root = doc.createElement("gamefield");
            doc.appendChild(root);

            Attr gfRow = doc.createAttribute("row");
            gfRow.setValue("5");
            root.setAttributeNode(gfRow);

            Attr gfColumn = doc.createAttribute("column");
            gfColumn.setValue("6");
            root.setAttributeNode(gfColumn);

            Element wall = doc.createElement("wall");
            root.appendChild(wall);

            Attr wallRow = doc.createAttribute("row");
            wallRow.setValue("1");
            wall.setAttributeNode(wallRow);

            Attr wallColumn = doc.createAttribute("column");
            wallColumn.setValue("2");
            wall.setAttributeNode(wallColumn);

            /*Element cat = doc.createElement("cat");
            root.appendChild(cat);

            Attr catRow = doc.createAttribute("row");
            catRow.setValue("8");
            wall.setAttributeNode(catRow);

            Attr catColumn = doc.createAttribute("column");
            catColumn.setValue("7");
            wall.setAttributeNode(catColumn);*/

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMImplementation domImplementation = doc.getImplementation();
            DocumentType documentType = domImplementation.createDocumentType("doctype", "", "test.dtd");
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, documentType.getSystemId());
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

            StreamResult streamResult = new StreamResult(System.out);
            transformer.transform(source, streamResult);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void loadXML() {
        try {
            File file = new File("/Users/joker/IdeaProjects/Katz-tastrophe/programs/te.xml");
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();
            System.out.println("root: " + document.getDocumentElement().getNodeName());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
