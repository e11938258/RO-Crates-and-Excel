package at.tuwien.rocreateprofil.model.entity.profile;

import at.tuwien.rocreateprofil.model.entity.dataset.Cell;
import at.tuwien.rocreateprofil.model.entity.value.Type;
import java.util.List;
import org.json.simple.JSONObject;

public interface ColumnProfile {
    
    public void build(List<Cell> cells);
    
    public Type getType();
    
    public void writeToFile(JSONObject object);
    
}
