package at.tuwien.rocreateprofil.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Dataset {

    private final List<Cell> cells = new ArrayList<>();
    private final String name;

    public Dataset(String name) {
        this.name = name;
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }
}
