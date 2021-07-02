package at.tuwien.rocreateprofil.convertor.excel.parser.content;

import at.tuwien.rocreateprofil.model.entity.dataset.Sheet;
import at.tuwien.rocreateprofil.output.rocrate.util.UniqueIdentifierGenerator;
import java.util.Map;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class ExcelSheetContentParser implements ExcelContentParser {

    @Override
    public void parse(final OntModel ontModel, final Map<String, Sheet> sheets) {
        // Get all cells
        final ResultSet cells = QueryExecutionFactory.create(Queries.GET_SHEETS, ontModel).execSelect();
        // For each cell
        while (cells.hasNext()) {
            final QuerySolution sheet = cells.next();
            // Get sheet name and create a new dataset for each column
            final String sheetName = sheet.getLiteral("SheetName").getString();
            sheets.put(sheetName, new Sheet(sheetName, 
                    UniqueIdentifierGenerator.generateUniqueUUIDWithPrefix(sheetName, 9)));
        }
    }
}
