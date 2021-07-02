package at.tuwien.rocreateprofil.convertor.excel.parser.content;

import at.tuwien.rocreateprofil.model.entity.dataset.Sheet;
import at.tuwien.rocreateprofil.model.entity.dataset.Row;
import java.util.Map;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class ExcelRowContentParser implements ExcelContentParser {
    
    @Override
    public void parse(final OntModel ontModel, final Map<String, Sheet> datasets) {
        // Get all cells
        final ResultSet cells = QueryExecutionFactory.create(Queries.GET_ROWS, ontModel).execSelect();
        // For each cell
        while (cells.hasNext()) {
            final QuerySolution row = cells.next();
            // Get dataset
            final String sheetName = row.getLiteral("SheetName").getString();
            final Sheet dataset = datasets.get(sheetName);
            
            // Add cell to dataset
            dataset.addRow(new Row(row.getLiteral("RowID").getString()));
            
            // Update dataset in map
            datasets.put(sheetName, dataset);
        }
    }
}
