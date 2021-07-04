package at.tuwien.rocreateprofil.convertor.excel.parser.metadata;

import at.tuwien.rocreateprofil.convertor.ExcelFileDecompressor;
import at.tuwien.rocreateprofil.convertor.excel.parser.ExcelParser;
import at.tuwien.rocreateprofil.model.entity.rocrate.meta.CreateActionContextEntityBuilder;
import at.tuwien.rocreateprofil.model.entity.rocrate.meta.PersonContextEntityBuilder;
import at.tuwien.rocreateprofil.model.entity.rocrate.meta.SoftwareApplicationContextEntityBuilder;
import at.tuwien.rocreateprofil.model.entity.rocrate.meta.util.RoCrateMetaUtil;
import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import at.tuwien.rocreateprofil.output.rocrate.util.ContextEntityReferenceBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.nio.file.Path;

public class ExcelMetadataParser implements ExcelParser {

    @Override
    public void parseInto(JSONArray roCrateMetadataGraph, File sourceFile) {
        JSONObject fileDataEntity = RoCrateMetaUtil.retrieveEntityById(roCrateMetadataGraph, sourceFile.getName());

        Path extractedPath = extractExcelAsZip(sourceFile);

        addCreatedAndModifiedDatesToFile(fileDataEntity, extractedPath);
        addAuthorInformation(roCrateMetadataGraph, fileDataEntity, extractedPath);
        addProvenanceInformationForFile(roCrateMetadataGraph, extractedPath, sourceFile.getName());
    }

    private void addProvenanceInformationForFile(JSONArray roCrateMetadataGraph, Path extractedPath, String fileName) {
        // extract the values from excel
        String applicationName = ExcelAppMetadataXmlParser.parseApplicationName(extractedPath);
        String applicationVersion = ExcelAppMetadataXmlParser.parseApplicationVersion(extractedPath);
        // build CreateAction with file as its output
        JSONObject createActionContextEntity = CreateActionContextEntityBuilder.build(fileName);
        // bind it with instrument (SoftwareApplication used to create/modify the file)
        String applicationContextEntityId = ((JSONObject)(createActionContextEntity.get(RoCrateSchema.INSTRUMENT))).get(RoCrateSchema.ID).toString();
        JSONObject applicationContextEntity = SoftwareApplicationContextEntityBuilder.build(applicationContextEntityId, applicationName, applicationVersion);
        // add both entities to the graph
        roCrateMetadataGraph.add(createActionContextEntity);
        roCrateMetadataGraph.add(applicationContextEntity);
    }

    private void addAuthorInformation(JSONArray roCrateMetadataGraph, JSONObject fileDataEntity, Path extractedPath) {
        if (isAuthorPresent(extractedPath)) {
            JSONObject authorReference = addAuthorToFileEntity(fileDataEntity);
            addAuthorAuthorContextEntityToGraph(roCrateMetadataGraph, extractedPath, authorReference);
        }
    }

    private void addCreatedAndModifiedDatesToFile(JSONObject fileDataEntity, Path extractedPath) {
        String createdDate = ExcelCoreMetadataXmlParser.parseCreatedDate(extractedPath);
        if (createdDate != null) {
            fileDataEntity.put(RoCrateSchema.CREATED, createdDate);
        }
        String modifiedDate = ExcelCoreMetadataXmlParser.parseModifiedDate(extractedPath);
        if (modifiedDate != null) {
            fileDataEntity.put(RoCrateSchema.MODIFIED, modifiedDate);
        }
    }

    private Path extractExcelAsZip(File sourceFile) {
        ExcelFileDecompressor.unzip(sourceFile);
        Path extractedPath = ExcelFileDecompressor.buildExtractedPath(sourceFile);
        return extractedPath;
    }

    private JSONObject addAuthorToFileEntity(JSONObject fileDataEntity) {
        JSONObject authorReference = ContextEntityReferenceBuilder.buildReference();
        fileDataEntity.put(RoCrateSchema.AUTHOR, authorReference);
        return authorReference;
    }

    private void addAuthorAuthorContextEntityToGraph(JSONArray roCrateMetadataGraph, Path extractedPath, JSONObject authorReference) {
        String authorName = ExcelCoreMetadataXmlParser.parseAuthor(extractedPath);
        JSONObject authorContextEntity = PersonContextEntityBuilder.build(authorReference.get(RoCrateSchema.ID).toString(), authorName);
        roCrateMetadataGraph.add(authorContextEntity);
    }

    private boolean isAuthorPresent(Path extractedPath) {
        return ExcelCoreMetadataXmlParser.parseAuthor(extractedPath) != null;
    }
}
