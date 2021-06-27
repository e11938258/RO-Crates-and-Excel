package at.tuwien.rocreateprofil.model.entity.dataset;

import at.tuwien.rocreateprofil.model.entity.profile.BooleanProfileColumn;
import at.tuwien.rocreateprofil.model.entity.profile.ColumnProfile;
import at.tuwien.rocreateprofil.model.entity.profile.NumericProfileColumn;
import at.tuwien.rocreateprofil.model.entity.profile.StringProfileColumn;
import at.tuwien.rocreateprofil.model.entity.value.Type;
import java.util.ArrayList;
import java.util.List;

public class Column {

    ColumnProfile columnProfile;
    final String Id;
    final List<Cell> cells = new ArrayList<>();

    public Column(String Id) {
        this.Id = Id;
    }

    public String getId() {
        return Id;
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public void buildProfile() {
        columnProfile = getType();
        columnProfile.build(cells);
    }

    private ColumnProfile getType() {
        // Boolean < Numeric < String
        int booleanCount = 0, numericCount = 0, stringCount = 0;

        // Get a little stat
        for (Cell cell : cells) {
            if (!cell.getType().equals(Type.BooleanValue)) {
                booleanCount++;
            } else if (!cell.getType().equals(Type.BooleanValue)) {
                numericCount++;
            } else {
                stringCount++;
            }
        }
        
        // Decide what type of columns
        if(stringCount > 0) {
            return new StringProfileColumn();
        } else if(numericCount > 1) {
            return new NumericProfileColumn();
        } else {
            return new BooleanProfileColumn(); 
        }
    }
}
