package at.tuwien.rocreateprofil.model.entity.profile;

import at.tuwien.rocreateprofil.model.entity.dataset.Cell;
import at.tuwien.rocreateprofil.model.entity.value.Type;
import java.util.List;

public class BooleanProfileColumn implements ColumnProfile {
    
    private Type type = Type.BooleanValue;
    
    @Override
    public void build(List<Cell> cells) {
        //
    }
    
}
