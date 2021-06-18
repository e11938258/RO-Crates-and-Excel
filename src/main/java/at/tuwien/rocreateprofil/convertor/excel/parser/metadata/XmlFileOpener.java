package at.tuwien.rocreateprofil.convertor.excel.parser.metadata;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XmlFileOpener {

    public static Document openDocument(String xmlFileLocation) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        File xmlFile = new File(xmlFileLocation);

        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        dbf.setNamespaceAware(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

}
