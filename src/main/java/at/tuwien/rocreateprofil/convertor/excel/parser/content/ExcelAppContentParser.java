package at.tuwien.rocreateprofil.convertor.excel.parser.content;

import ExcelReader.ExcelBasedReader;
import Transformer.ExcelTransformer;
import at.tuwien.rocreateprofil.convertor.excel.parser.ExcelParser;
import at.tuwien.rocreateprofil.model.entity.dataset.Column;
import at.tuwien.rocreateprofil.model.entity.dataset.Sheet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import at.tuwien.rocreateprofil.model.entity.rocrate.meta.util.RoCrateMetaUtil;
import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import at.tuwien.rocreateprofil.output.rocrate.Xlsx2rocrateSchema;
import java.nio.charset.Charset;
import org.apache.jena.ontology.OntModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ExcelAppContentParser implements ExcelParser {

    private final Map<String, Sheet> sheets = new HashMap<>();
    private final List<ExcelContentParser> contentParsers = new ArrayList<>();

    public ExcelAppContentParser() {
        // Register content parsers
        contentParsers.add(new ExcelSheetContentParser()); // Sheets
        contentParsers.add(new ExcelColumnContentParser()); // Columns
        contentParsers.add(new ExcelRowContentParser()); // Rows
        contentParsers.add(new ExcelCellContentParser()); // Cells
    }

    @Override
    public void parseInto(JSONArray roCrateMetadataGraph, File sourceFile) {
        try {
            // Load excel
            final ExcelBasedReader excelBasedReader = new ExcelBasedReader(sourceFile);
            excelBasedReader.readExcelConverter();

            // Transform excel and create model
            ExcelTransformer excelTransformer = new ExcelTransformer(excelBasedReader);
            excelTransformer.create();

            // Get rdf model
            final OntModel ontModel = excelTransformer.getModel();

            // Run content parsers
            for (ExcelContentParser excelContent : contentParsers) {
                excelContent.parse(ontModel, sheets);
            }

            // Build profile for each column of dataset
            for (Sheet dataset : sheets.values()) {
                dataset.buildProfile(true);
            }

            JSONObject fileDataEntity = RoCrateMetaUtil.retrieveEntityById(roCrateMetadataGraph, sourceFile.getName());
            // Types
            final JSONArray typeArr = new JSONArray();
            typeArr.add(RoCrateSchema.TYPE_FILE);
            typeArr.add(RoCrateSchema.CUSTOM_ONTOLOGY_NAMESPACE + ":"
                    + Xlsx2rocrateSchema.EXCEL_WORKBOOK);
            fileDataEntity.put(RoCrateSchema.TYPE, typeArr);

            // Encoding
            fileDataEntity.put(Xlsx2rocrateSchema.ENCODING, Charset.defaultCharset().displayName());

            // Add sheets ids
            final JSONArray sheetsArr = new JSONArray();
            for (Sheet sheet : sheets.values()) {
                final JSONObject s = new JSONObject();
                s.put(RoCrateSchema.ID, sheet.getId());
                sheetsArr.add(s);
            }
            fileDataEntity.put(Xlsx2rocrateSchema.SHEET, sheetsArr);

            // Write all sheets
            for (Sheet sheet : sheets.values()) {
                JSONObject sheetDataEntity = new JSONObject();
                sheet.writeToFile(sheetDataEntity);
                roCrateMetadataGraph.add(sheetDataEntity);
            }

            // Write all columns
            for (Sheet sheet : sheets.values()) {
                for (Column column : sheet.getColumns()) {
                    JSONObject columnDataEntity = new JSONObject();
                    column.writeToFile(columnDataEntity);
                    roCrateMetadataGraph.add(columnDataEntity);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ExcelAppContentParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
