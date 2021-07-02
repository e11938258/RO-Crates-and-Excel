package at.tuwien.rocreateprofil.convertor.excel.parser.content;

import at.tuwien.rocreateprofil.model.entity.dataset.Column;
import at.tuwien.rocreateprofil.model.entity.dataset.Sheet;
import at.tuwien.rocreateprofil.output.rocrate.util.UniqueIdentifierGenerator;
import java.util.Map;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class ExcelColumnContentParser implements ExcelContentParser {

    @Override
    public void parse(final OntModel ontModel, final Map<String, Sheet> datasets) {
        // Get all cells
        final ResultSet cells = QueryExecutionFactory.create(Queries.GET_COLUMNS, ontModel).execSelect();
        // For each cell
        while (cells.hasNext()) {
            final QuerySolution column = cells.next();
            // Get dataset
            final String sheetName = column.getLiteral("SheetName").getString();
            final Sheet dataset = datasets.get(sheetName);

            // Add cell to dataset
            dataset.addColumn(new Column(column.getLiteral("ColumnID").getString(),
                    UniqueIdentifierGenerator.generateUniqueUUIDWithPrefix(column.getLiteral("ColumnID").getString(), 9)));

            // Update dataset in map
            datasets.put(sheetName, dataset);
        }
    }
}
