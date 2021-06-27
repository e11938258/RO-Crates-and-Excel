package at.tuwien.rocreateprofil.model.entity.profile;

import at.tuwien.rocreateprofil.model.entity.dataset.Cell;
import at.tuwien.rocreateprofil.model.entity.value.Type;
import java.util.List;

public class StringProfileColumn implements ColumnProfile {
    
    private Type type = Type.StringValue;
    
    @Override
    public void build(List<Cell> cells) {
        // 
    }
    
}
