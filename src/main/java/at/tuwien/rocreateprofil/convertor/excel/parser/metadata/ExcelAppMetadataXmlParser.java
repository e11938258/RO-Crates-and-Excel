package at.tuwien.rocreateprofil.convertor.excel.parser.metadata;

import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;
import at.tuwien.rocreateprofil.model.entity.RoCrateModel;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.nio.file.Path;

import static at.tuwien.rocreateprofil.exception.Error.FAILED_TO_PROCESS_EXCEL_METADATA;

public class ExcelAppMetadataXmlParser {

    private static final String APP_METADATA_FILE = "docProps/app.xml";

    public static String parseApplicationName(Path extractedPath) {
        try {
            Document doc = XmlFileOpener.openDocument(extractedPath.toString() + "/" + APP_METADATA_FILE);
            NodeList list = doc.getElementsByTagNameNS("*", "Application");
            return list.item(0).getFirstChild().getTextContent();
        } catch (Exception e) {
            throw new RoCrateProfileBaseException(FAILED_TO_PROCESS_EXCEL_METADATA);
        }

    }

    public static String parseApplicationVersion(Path extractedPath) {
        try {
            Document doc = XmlFileOpener.openDocument(extractedPath.toString() + "/" + APP_METADATA_FILE);

            NodeList list = doc.getElementsByTagNameNS("*", "AppVersion");
            return list.item(0).getFirstChild().getTextContent();
        } catch (Exception e) {
            throw new RoCrateProfileBaseException(FAILED_TO_PROCESS_EXCEL_METADATA);
        }
    }
}
