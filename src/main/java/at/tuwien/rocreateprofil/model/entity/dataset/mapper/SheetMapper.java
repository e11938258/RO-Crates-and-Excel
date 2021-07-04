package at.tuwien.rocreateprofil.model.entity.dataset.mapper;

import at.tuwien.rocreateprofil.model.entity.dataset.Column;
import at.tuwien.rocreateprofil.model.entity.dataset.Sheet;
import at.tuwien.rocreateprofil.model.entity.rocrate.meta.util.RoCrateMetaUtil;
import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import at.tuwien.rocreateprofil.output.rocrate.Xlsx2rocrateSchema;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

public class SheetMapper {

    public static Sheet fromJSON(JSONArray entityGraph, JSONObject sheetContextEntity) {
        // load values from metadata
        String name = (String) sheetContextEntity.get(Xlsx2rocrateSchema.SHEET_NAME);
        String id = (String) sheetContextEntity.get(RoCrateSchema.ID);
        Long numberOfRows = (Long) sheetContextEntity.get(Xlsx2rocrateSchema.NUMBER_OF_ROWS);
        Long numberOfColumns = (Long) sheetContextEntity.get(Xlsx2rocrateSchema.NUMBER_OF_COLS);
        // map into sheet object
        Sheet sheet = new Sheet(name, id);
        sheet.setNumberOfRows(numberOfRows);
        sheet.setNumberOfColumns(numberOfColumns);

        JSONArray dimensions = (JSONArray) sheetContextEntity.get(RoCrateSchema.COMPONENTS);
        Iterator dimensionIterator = dimensions.iterator();
        while (dimensionIterator.hasNext()) {
            JSONObject columnReference = (JSONObject) dimensionIterator.next();
            String columnContextEntityId = columnReference.get(RoCrateSchema.ID).toString();
            JSONObject columnContextEntity = RoCrateMetaUtil.retrieveEntityById(entityGraph, columnContextEntityId);

            Column column = ColumnMapper.fromJSON(columnContextEntity);
            sheet.addColumn(column);
        }

        return sheet;
    }

}
