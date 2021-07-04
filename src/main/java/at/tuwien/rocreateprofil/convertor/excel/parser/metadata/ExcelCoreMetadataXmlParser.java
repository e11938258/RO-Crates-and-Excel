package at.tuwien.rocreateprofil.convertor.excel.parser.metadata;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.time.Instant;
import java.util.logging.Logger;

public class ExcelCoreMetadataXmlParser {

    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private static final String CORE_METADATA_FILE = "docProps/core.xml";

    public static String parseAuthor(Path extractedPath) {
        try {
            Document doc = XmlFileOpener.openDocument(extractedPath.toString() + "/" + CORE_METADATA_FILE);
            NodeList list = doc.getElementsByTagNameNS("http://purl.org/dc/elements/1.1/", "creator");
            return list.item(0).getFirstChild().getTextContent();
        } catch (Exception e) {
            logger.info("Failed to locate author in excel metadata - skipping...");
            return null;
        }
    }

    public static String parseCreatedDate(Path extractedPath) {
        try {
            Document doc = XmlFileOpener.openDocument(extractedPath.toString() + "/" + CORE_METADATA_FILE);
            NodeList list = doc.getElementsByTagNameNS("http://purl.org/dc/terms/", "created");
            String createdDateString = list.item(0).getFirstChild().getTextContent();
            Instant createdDate = Instant.parse(createdDateString);
            return createdDate.toString();
        } catch (Exception e) {
            logger.info("Failed to locate dateCreated in excel metadata - skipping...");
            return null;
        }
    }

    public static String parseModifiedDate(Path extractedPath) {
        try {
            Document doc = XmlFileOpener.openDocument(extractedPath.toString() + "/" + CORE_METADATA_FILE);
            NodeList list = doc.getElementsByTagNameNS("http://purl.org/dc/terms/", "modified");
            String createdDateString = list.item(0).getFirstChild().getTextContent();
            Instant createdDate = Instant.parse(createdDateString);
            return createdDate.toString();
        } catch (Exception e) {
            logger.info("Failed to locate dateModified in excel metadata - skipping...");
            return null;
        }
    }


}
