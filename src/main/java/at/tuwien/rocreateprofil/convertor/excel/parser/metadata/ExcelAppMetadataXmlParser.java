package at.tuwien.rocreateprofil.convertor.excel.parser.metadata;

import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;
import at.tuwien.rocreateprofil.model.entity.RoCrateModel;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.nio.file.Path;

import static at.tuwien.rocreateprofil.exception.Error.FAILED_TO_PROCESS_EXCEL_METADATA;

public class ExcelAppMetadataXmlParser {

    private static final String APP_METADATA_FILE = "docProps/app.xml";


    public static void parseIntoModel(Path extractedPath, RoCrateModel model) {
        try {
            Document doc = XmlFileOpener.openDocument(extractedPath.toString() + "/" + APP_METADATA_FILE);

            model.setApplicationName(parseApplicationName(doc));
            model.setApplicationVersion(parseApplicationVersion(doc));

        } catch (Exception e) {
            throw new RoCrateProfileBaseException(FAILED_TO_PROCESS_EXCEL_METADATA);
        }
    }

    private static String parseApplicationName(Document doc) {
        NodeList list = doc.getElementsByTagNameNS("*", "Application");
        return list.item(0).getFirstChild().getTextContent();
    }

    private static String parseApplicationVersion(Document doc) {
        NodeList list = doc.getElementsByTagNameNS("*", "AppVersion");
        return list.item(0).getFirstChild().getTextContent();
    }
}
