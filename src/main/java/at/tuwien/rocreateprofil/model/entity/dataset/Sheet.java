package at.tuwien.rocreateprofil.model.entity.dataset;

import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import at.tuwien.rocreateprofil.output.rocrate.Xlsx2rocrateSchema;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Sheet {

    private final List<Column> columns = new ArrayList<>();
    private final List<Row> rows = new ArrayList<>();
    private final String name, id;
    private boolean missingValues = false;

    public Sheet(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public void buildProfile(boolean header) {
        // For each column
        for (Column column : columns) {
            // Build profile for each column
            column.buildProfile(header);
            // Missing values?
            if (column.isMissingValues()) {
                missingValues = true;
            }
        }
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public void addRow(Row row) {
        rows.add(row);
    }

    public Column getColumn(String id) {
        for (Column column : columns) {
            if (column.getName().equals(id)) {
                return column;
            }
        }
        return null;
    }

    public Row getRow(String id) {
        for (Row row : rows) {
            if (row.getId().equals(id)) {
                return row;
            }
        }
        return null;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
    
    public void writeToFile(JSONObject object) {
        // ID
        object.put(RoCrateSchema.ID, id);

        // Types
        final JSONArray typesArr = new JSONArray();
        typesArr.add(RoCrateSchema.DATA_CUBE_NAMESPACE + ":"
                + RoCrateSchema.DATA_CUBE_DATA_STRUCTURE_DEFINITION);
        typesArr.add(RoCrateSchema.CUSTOM_ONTOLOGY_NAMESPACE + ":"
                + Xlsx2rocrateSchema.EXCEL_SHEET);
        object.put(RoCrateSchema.TYPE, typesArr);
        
        // Number of column, rows
        object.put(Xlsx2rocrateSchema.NUMBER_OF_COLS, columns.size());
        object.put(Xlsx2rocrateSchema.NUMBER_OF_ROWS, rows.size());
        // Missing values
        object.put(Xlsx2rocrateSchema.MISSING_VALUES, missingValues);

        // Sheet name
        object.put(Xlsx2rocrateSchema.SHEET_NAME, name);
        
        // Components - columns
        final JSONArray componentsArr = new JSONArray();

        for (Column column : columns) {
            JSONObject columnDataEntity = new JSONObject();
            columnDataEntity.put(RoCrateSchema.ID, column.getId());
            componentsArr.add(columnDataEntity);
        }

        object.put(RoCrateSchema.COMPONENTS, componentsArr);
    }
}
