package at.tuwien.rocreateprofil.convertor.excel.parser.content;

import ExcelReader.ExcelBasedReader;
import Transformer.ExcelTransformer;
import at.tuwien.rocreateprofil.convertor.excel.parser.ExcelParser;
import at.tuwien.rocreateprofil.model.entity.dataset.Dataset;
import at.tuwien.rocreateprofil.model.entity.RoCrateModel;
import at.tuwien.rocreateprofil.model.entity.dataset.Column;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.ontology.OntModel;

public class ExcelAppContentParser implements ExcelParser {

    public final Map<String, Dataset> datasets = new HashMap<>();
    public final List<ExcelContentParser> contentParsers = new ArrayList<>();

    public ExcelAppContentParser() {
        // Register content parsers
        contentParsers.add(new ExcelSheetContentParser()); // Sheets
        contentParsers.add(new ExcelColumnContentParser()); // Columns
        contentParsers.add(new ExcelRowContentParser()); // Rows
        contentParsers.add(new ExcelCellContentParser()); // Cells
    }
    
    @Override
    public RoCrateModel parseInto(File sourceFile, RoCrateModel model) {
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
                excelContent.parse(ontModel, datasets);
            }
            
            // Build profile for each column of dataset
            for (Dataset dataset : datasets.values()) {
                for (Column column : dataset.getColumns()) {
//                    column.buildProfile();
                }
            }
            
            // Add all dataset to model
            for (Dataset dataset : datasets.values()) {
                model.addDataset(dataset);
            }
        } catch (IOException ex) {
            Logger.getLogger(ExcelAppContentParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }
}
