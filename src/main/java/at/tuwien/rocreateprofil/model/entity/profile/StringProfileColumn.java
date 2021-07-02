package at.tuwien.rocreateprofil.model.entity.profile;

import at.tuwien.rocreateprofil.model.entity.dataset.Cell;
import at.tuwien.rocreateprofil.model.entity.value.Type;
import at.tuwien.rocreateprofil.output.rocrate.Xlsx2rocrateSchema;
import java.nio.charset.Charset;
import java.util.List;
import org.json.simple.JSONObject;

public class StringProfileColumn implements ColumnProfile {

    private Integer min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    private Double avr = null;
    private Type type = Type.StringValue;

    @Override
    public void build(List<Cell> cells) {
        Double sum = 0.0;

        for (Cell cell : cells) {
            final String value = (String) cell.getValue().getValue();

            // Sum
            sum += value.length();

            // Minimum?
            if (value.length() < min) {
                min = value.length();
            }

            // Maximum?
            if (value.length() > max) {
                max = value.length();
            }
        }

        // Calculate average length
        avr = sum / cells.size();

        // Get encoding
        Charset.defaultCharset().displayName();
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void writeToFile(JSONObject object) {
        // Write properties of column
        object.put(Xlsx2rocrateSchema.FORMAT_TYPE, "string");
        if (min != Integer.MAX_VALUE) {
            object.put(Xlsx2rocrateSchema.MINIMUM_LENGTH, min);
        }
        if (max != Integer.MIN_VALUE) {
            object.put(Xlsx2rocrateSchema.MAXIMUM_LENGTH, max);
        }
        if (avr != null) {
            object.put(Xlsx2rocrateSchema.AVERAGE_LENGTH, avr);
        }
    }
}
