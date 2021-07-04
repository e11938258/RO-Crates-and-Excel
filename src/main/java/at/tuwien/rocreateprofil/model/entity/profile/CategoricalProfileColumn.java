package at.tuwien.rocreateprofil.model.entity.profile;

import at.tuwien.rocreateprofil.model.entity.dataset.Cell;
import at.tuwien.rocreateprofil.model.entity.dataset.ColumnType;
import at.tuwien.rocreateprofil.model.entity.dataset.mapper.columnprofile.CategoricalColumnProfileMapper;
import at.tuwien.rocreateprofil.model.entity.dataset.mapper.columnprofile.ColumnProfileMapper;
import at.tuwien.rocreateprofil.model.entity.value.Type;
import at.tuwien.rocreateprofil.output.rocrate.Xlsx2rocrateSchema;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONObject;

public class CategoricalProfileColumn implements ColumnProfile {

    private final HashMap<String, Integer> categories = new HashMap<>();
    private final Type type = Type.StringValue;
    private Long mode = null;
    private String modeKey = null;
    private Double modeInPercent = null;

    @Override
    public void build(List<Cell> cells) {

        for (Cell cell : cells) {
            final String value = String.valueOf(cell.getValue().getValue());

            // Get value
            Integer count = categories.get(value);
            if (count != null) {
                categories.put(value, ++count);
            } else {
                categories.put(value, 1);
            }
        }

        // Get mode
        mode = 0L;
        modeKey = "";

        for (String key : categories.keySet()) {
            final Long value = Long.valueOf(categories.get(key));
            // New mode?
            if (value > mode) {
                mode = value;
                modeKey = key;
            }
        }

        modeInPercent = (mode.doubleValue() / cells.size()) * 100;

    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.CATEGORICAL;
    }

    @Override
    public void writeToFile(JSONObject object) {
        // Write properties of column
        object.put(Xlsx2rocrateSchema.FORMAT_TYPE, "categorical");
        if (mode != null) {
            object.put(Xlsx2rocrateSchema.MODE, mode);
        }
        if (modeKey != null) {
            object.put(Xlsx2rocrateSchema.MODE_KEY, modeKey);
        }
        if (modeInPercent != null) {
            object.put(Xlsx2rocrateSchema.MODE_IN_PERCENT, modeInPercent);
        }
    }

    @Override
    public ColumnProfileMapper getProfileMapper() {
        return new CategoricalColumnProfileMapper();
    }

    public Long getMode() {
        return mode;
    }

    public void setMode(Long mode) {
        this.mode = mode;
    }

    public String getModeKey() {
        return modeKey;
    }

    public void setModeKey(String modeKey) {
        this.modeKey = modeKey;
    }

    public Double getModeInPercent() {
        return modeInPercent;
    }

    public void setModeInPercent(Double modeInPercent) {
        this.modeInPercent = modeInPercent;
    }

    @Override
    public String generateValidValue() {
        return modeKey;
    }
}
