package at.tuwien.rocreateprofil.convertor.excel.parser;

import ExcelReader.ExcelBasedReader;
import Transformer.ExcelTransformer;
import at.tuwien.rocreateprofil.convertor.excel.Queries;
import at.tuwien.rocreateprofil.model.entity.Cell;
import at.tuwien.rocreateprofil.model.entity.Dataset;
import at.tuwien.rocreateprofil.model.entity.RoCrateModel;
import at.tuwien.rocreateprofil.model.entity.Type;
import at.tuwien.rocreateprofil.model.entity.value.BooleanValue;
import at.tuwien.rocreateprofil.model.entity.value.NumericValue;
import at.tuwien.rocreateprofil.model.entity.value.StringValue;
import at.tuwien.rocreateprofil.model.entity.value.Value;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;

public class ExcelContentsParser implements ExcelParser {

    public final Map<String, Dataset> datasets = new HashMap<>();

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
            // Add cells
            addCells(ontModel);
            // Add all dataset to model
            for (Dataset dataset : datasets.values()) {
                model.addDataset(dataset);
            }
        } catch (IOException ex) {
            Logger.getLogger(ExcelContentsParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    private void addCells(final OntModel ontModel) {
        // Get all cells
        final ResultSet cells = QueryExecutionFactory.create(Queries.GET_CELLS, ontModel).execSelect();
        // For each cell
        while (cells.hasNext()) {
            final QuerySolution cell = cells.next();
            // Sheet name
            String sheetName;
            if (cell.contains("SheetName")) {
                sheetName = cell.getLiteral("SheetName").getString();
            } else if (cell.contains("SheetNameCol")) {
                sheetName = cell.getLiteral("SheetNameCol").getString();
            } else {
                // No connection to sheet
                continue;
            }
            
            // Get dataset
            Dataset dataset = datasets.get(sheetName);
            if (dataset == null) {
                dataset = new Dataset(sheetName);
            }
            
            // Get type of cell value
            final Type type = Type.valueOf(cell.getLiteral("type").getString());
            // Get value of cell
            final Value value = getValue(type, cell.getLiteral("value"));
            
            // Get function name of cell if exists
            String functionName = null;
            if (cell.contains("FunctionName")) {
                functionName = cell.getLiteral("FunctionName").getString();
            }
            // Get column id if exists
            String colId = null;
            if (cell.contains("ColumnID")) {
                colId = cell.getLiteral("ColumnID").getString();
            }
            // Get row id if exists
            String rowId = null;
            if (cell.contains("RowID")) {
                rowId = cell.getLiteral("RowID").getString();
            }
            
            // Add cell to dataset
            dataset.addCell(new Cell(cell.getLiteral("CellID").getString(),
                    functionName, type, value, colId, rowId));
            
            // Update dataset in map
            datasets.put(sheetName, dataset);
        }
    }

    private Value getValue(Type type, Literal value) {
        switch (type) {
            case BooleanValue:
                return new BooleanValue(value.getBoolean());
            case NumericValue:
                return new NumericValue(value.getDouble());
            default:
                return new StringValue(value.getString());
        }
    }
}
