package at.tuwien.rocreateprofil.convertor.excel.parser.metadata;

import at.tuwien.rocreateprofil.model.entity.RoCrateModel;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

public class ExcelCoreMetadataXmlParser {

    private static final String CORE_METADATA_FILE = "docProps/core.xml";

    public static void parseIntoModel(Path extractedPath, RoCrateModel model) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        File xmlFile = new File(extractedPath.toString() + "/" + CORE_METADATA_FILE);
        try {
            Document doc = openDocument(dbf, xmlFile);

            model.setAuthor(parseAuthor(doc));
            model.setCreated(parseCreatedDate(doc));
            model.setModified(parseModifiedDate(doc));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private static String parseAuthor(Document doc) {
        NodeList list = doc.getElementsByTagNameNS("http://purl.org/dc/elements/1.1/", "creator");
        return list.item(0).getFirstChild().getTextContent();
    }

    private static Instant parseCreatedDate(Document doc) {
        NodeList list = doc.getElementsByTagNameNS("http://purl.org/dc/terms/", "created");
        String createdDateString = list.item(0).getFirstChild().getTextContent();
        Instant createdDate = Instant.parse(createdDateString);
        return createdDate;
    }

    private static Instant parseModifiedDate(Document doc) {
        NodeList list = doc.getElementsByTagNameNS("http://purl.org/dc/terms/", "modified");
        String createdDateString = list.item(0).getFirstChild().getTextContent();
        Instant createdDate = Instant.parse(createdDateString);
        return createdDate;
    }

    private static Document openDocument(DocumentBuilderFactory dbf, File xmlFile) throws ParserConfigurationException, SAXException, IOException {
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        dbf.setNamespaceAware(true);

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc;
    }
}
