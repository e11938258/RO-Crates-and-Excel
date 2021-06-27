package at.tuwien.rocreateprofil.model.entity.dataset;

import at.tuwien.rocreateprofil.exception.RoCrateProfileBaseException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Dataset {

    // Static var - file naming
    public static String filePatternName = "sheet_";
    private static int fileNumber = 1;
    
    // Non-static var
    private final List<Column> columns = new ArrayList<>();
    private final List<Row> rows = new ArrayList<>();
    private final List<Cell> cells = new ArrayList<>();
    private final String name, fileName, format;

    public Dataset(String name) {
        this.name = name;
        this.format = "application/json";
        this.fileName = filePatternName + (fileNumber++);
    }
    
    public void addColumn(Column column) {
        columns.add(column);
    }
    
    public void addRow(Row row) {
        rows.add(row);
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }
    
    public Column getColumn(String id) {
        for (Column column : columns) {
            if(column.getId().equals(id)) {
                return column;
            }
        } 
        return null;
    }
    
    public Row getRow(String id) {
        for (Row row : rows) {
            if(row.getId().equals(id)) {
                return row;
            }
        } 
        return null;
    }

    public List<Column> getColumns() {
        return columns;
    }
    
    public String getId() {
        return fileName;
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }

    public void writeToFile(String path) {
        final JSONObject fileEntity = new JSONObject();

        // Write each cell
        final JSONArray cellAr = new JSONArray();
        for (Cell cell : cells) {
            // Put cell to array
            cellAr.add(cell.getJSONObject());
        }

        // Write arrays to file
        fileEntity.put("cells", cellAr);

        // Write to file
        FileWriter file = null;
        try {
            file = new FileWriter(path + "/" + fileName + ".json");
            file.write(fileEntity.toString());
        } catch (IOException e) {
            throw new RoCrateProfileBaseException(at.tuwien.rocreateprofil.exception.Error.RO_CRATE_CONTENT_WRITE_FAILED, fileEntity);
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                throw new RoCrateProfileBaseException(at.tuwien.rocreateprofil.exception.Error.RO_CRATE_CONTENT_WRITE_FAILED, fileEntity);
            }
        }

        // Write properties of dataset
        fileEntity.put("name", name);
    }
}
