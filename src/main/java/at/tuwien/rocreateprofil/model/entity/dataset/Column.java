package at.tuwien.rocreateprofil.model.entity.dataset;

import at.tuwien.rocreateprofil.model.entity.profile.CategoricalProfileColumn;
import at.tuwien.rocreateprofil.model.entity.profile.ColumnProfile;
import at.tuwien.rocreateprofil.model.entity.profile.NumericProfileColumn;
import at.tuwien.rocreateprofil.model.entity.profile.StringProfileColumn;
import at.tuwien.rocreateprofil.model.entity.value.Type;
import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import at.tuwien.rocreateprofil.output.rocrate.Xlsx2rocrateSchema;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class Column {

    private final Set<String> categories = new HashSet<>();
    private final List<Cell> cells = new ArrayList<>();
    private final int categoricalThreshold = 10;
    private final String name, id;
    private String headerName = null;
    private ColumnProfile columnProfile;
    private boolean missingValues = false;

    public Column(String excelId, String id) {
        this.name = excelId;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public void buildProfile(boolean header) {
        if (cells.size() > 0) {
            // Order cells by name
            Collections.sort(cells);

            // Missing values
            Integer count = Integer.parseInt(cells.get(cells.size() - 1).getId().replace(name, ""))
                    - Integer.parseInt(cells.get(0).getId().replace(name, ""));
            if (cells.size() < count) {
                missingValues = true;
            }

            // Get header
            if (header) {
                headerName = (String) cells.get(0).getValue().getValue();
                cells.remove(0);
            }

            // Build column profile
            columnProfile = getType();
            columnProfile.build(cells);
        }
    }

    private ColumnProfile getType() {
        // Numeric < Categorical < String
        int numericCount = 0, stringCount = 0;

        // Get a little stat
        for (Cell cell : cells) {
            if (cell.getType().equals(Type.NumericValue)) {
                numericCount++;
            } else {
                stringCount++;
                // Add string to hashset
                categories.add((String) cell.getValue().getValue());
            }
        }

        // Decide what type of column
        if (stringCount == 0 && numericCount > 0) {
            return new NumericProfileColumn();
        } else if (categories.size() <= categoricalThreshold) {
            return new CategoricalProfileColumn();
        } else {
            return new StringProfileColumn();
        }
    }

    public boolean isMissingValues() {
        return missingValues;
    }

    public void writeToFile(JSONObject object) {
        // ID
        object.put(RoCrateSchema.ID, id);

        // Types
        final JSONArray typesArr = new JSONArray();
        typesArr.add(RoCrateSchema.DATA_CUBE_NAMESPACE + ":"
                + RoCrateSchema.DATA_CUBE_DIMENSTION_PROPERTY);
        typesArr.add(RoCrateSchema.CUSTOM_ONTOLOGY_NAMESPACE + ":"
                + Xlsx2rocrateSchema.EXCEL_COLUMN);
        object.put(RoCrateSchema.TYPE, typesArr);

        // Column id
        object.put(Xlsx2rocrateSchema.COLUMN_ID, name);

        // Header name
        if (headerName != null) {
            object.put(Xlsx2rocrateSchema.COLUMN_NAME, headerName);
        }

        // Missing values
        object.put(Xlsx2rocrateSchema.MISSING_VALUES, missingValues);
        // Write column profile as well
        columnProfile.writeToFile(object);
    }

    public String generateValidValueForColumn() {
        if (Math.random() >= ColumnProfile.MISSING_VALUE_PROPORTION) {
            return columnProfile.generateValidValue();
        }
        return StringUtils.EMPTY;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public int getCategoricalThreshold() {
        return categoricalThreshold;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public ColumnProfile getColumnProfile() {
        return columnProfile;
    }

    public void setColumnProfile(ColumnProfile columnProfile) {
        this.columnProfile = columnProfile;
    }

    public void setMissingValues(boolean missingValues) {
        this.missingValues = missingValues;
    }
}
