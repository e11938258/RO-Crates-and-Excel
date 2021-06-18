package at.tuwien.rocreateprofil.convertor.excel.parser.metadata;

import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;
import at.tuwien.rocreateprofil.model.entity.RoCrateModel;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.nio.file.Path;
import java.time.Instant;

import static at.tuwien.rocreateprofil.exception.Error.FAILED_TO_PROCESS_EXCEL_METADATA;

public class ExcelCoreMetadataXmlParser {

    private static final String CORE_METADATA_FILE = "docProps/core.xml";

    public static void parseIntoModel(Path extractedPath, RoCrateModel model) {

        try {
            Document doc = XmlFileOpener.openDocument(extractedPath.toString() + "/" + CORE_METADATA_FILE);

            model.setAuthor(parseAuthor(doc));
            model.setCreated(parseCreatedDate(doc));
            model.setModified(parseModifiedDate(doc));

        } catch (Exception e) {
            throw new RoCrateProfileBaseException(FAILED_TO_PROCESS_EXCEL_METADATA);
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


}
