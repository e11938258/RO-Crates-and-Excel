package at.tuwien.rocreateprofil.model.entity.profile;

import at.tuwien.rocreateprofil.model.entity.dataset.Cell;
import at.tuwien.rocreateprofil.model.entity.value.Type;
import java.util.List;

public class NumericProfileColumn implements ColumnProfile {

    private Double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
    private Type type = Type.NumericValue;

    @Override
    public void build(List<Cell> cells) {
        for (Cell cell : cells) {
            final Double value = (Double) cell.getValue().getValue();
            // New minimum?
            if(value < min) {
                min = value;
            }
            // New maximum?
            if(value > max) {
                max = value;
            }
        }
    }

}
