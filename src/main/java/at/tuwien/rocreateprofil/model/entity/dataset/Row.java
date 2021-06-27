package at.tuwien.rocreateprofil.model.entity.dataset;

import java.util.ArrayList;
import java.util.List;

public class Row {
    
    final String Id;
    final List<Cell> cells = new ArrayList<>();

    public Row(String Id) {
        this.Id = Id;
    }
    
    public String getId() {
        return Id;
    }
    
    public void addCell(Cell cell) {
        cells.add(cell);
    }
}
