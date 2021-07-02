package at.tuwien.rocreateprofil.convertor.excel.parser.content;

import at.tuwien.rocreateprofil.model.entity.dataset.Cell;
import at.tuwien.rocreateprofil.model.entity.dataset.Column;
import at.tuwien.rocreateprofil.model.entity.dataset.Sheet;
import at.tuwien.rocreateprofil.model.entity.dataset.Row;
import at.tuwien.rocreateprofil.model.entity.value.Type;
import at.tuwien.rocreateprofil.model.entity.value.NumericValue;
import at.tuwien.rocreateprofil.model.entity.value.StringValue;
import at.tuwien.rocreateprofil.model.entity.value.Value;
import java.util.Map;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;

public class ExcelCellContentParser implements ExcelContentParser {

    @Override
    public void parse(final OntModel ontModel, final Map<String, Sheet> datasets) {
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
            Sheet sheet = datasets.get(sheetName);
            // Get column id if exists
            final Column column = sheet.getColumn(cell.getLiteral("ColumnID").getString());
            // Get row id if exists
            final Row row = sheet.getRow(cell.getLiteral("RowID").getString());
            // Get type of cell value
            final Type type = Type.valueOf(cell.getLiteral("type").getString());
            // Get value of cell
            final Value value = getValue(type, cell.getLiteral("value"));
            // Get function name of cell if exists
            String functionName = null;
            if (cell.contains("FunctionName")) {
                functionName = cell.getLiteral("FunctionName").getString();
            }

            // Add cell to dataset
            final Cell c = new Cell(cell.getLiteral("CellID").getString(),
                    functionName, type, value, column, row);
            column.addCell(c);
            row.addCell(c);

            // Update dataset in map
            datasets.put(sheetName, sheet);
        }
    }

    private Value getValue(Type type, Literal value) {
        switch (type) {
            case NumericValue:
                return new NumericValue(value.getDouble());
            default:
                return new StringValue(value.getString());
        }
    }
}
