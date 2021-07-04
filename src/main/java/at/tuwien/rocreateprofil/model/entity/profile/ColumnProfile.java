package at.tuwien.rocreateprofil.model.entity.profile;

import at.tuwien.rocreateprofil.model.entity.dataset.Cell;
import at.tuwien.rocreateprofil.model.entity.dataset.ColumnType;
import at.tuwien.rocreateprofil.model.entity.dataset.mapper.columnprofile.ColumnProfileMapper;
import org.json.simple.JSONObject;

import java.util.List;

public interface ColumnProfile {

    double MISSING_VALUE_PROPORTION = 0.3;

    void build(List<Cell> cells);
    
    ColumnType getColumnType();
    
    void writeToFile(JSONObject object);

    ColumnProfileMapper getProfileMapper();

    String generateValidValue();
}
