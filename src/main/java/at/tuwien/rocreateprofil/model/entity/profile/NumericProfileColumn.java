package at.tuwien.rocreateprofil.model.entity.profile;

import at.tuwien.rocreateprofil.model.entity.dataset.Cell;
import at.tuwien.rocreateprofil.model.entity.value.Type;
import at.tuwien.rocreateprofil.output.rocrate.Xlsx2rocrateSchema;
import java.util.Arrays;
import java.util.List;
import org.json.simple.JSONObject;

public class NumericProfileColumn implements ColumnProfile {

    private Type type = Type.NumericValue;
    // Attributes
    private Double min = Double.MAX_VALUE, max = Double.MIN_VALUE, mean = null,
            stdev = null, median = null, sum = 0.0;
    private Integer numberOfSamples = null;
    private boolean integer = true;

    @Override
    public void build(List<Cell> cells) {
        numberOfSamples = cells.size();
        Double[] values = new Double[numberOfSamples];
        int i = 0;

        for (Cell cell : cells) {
            final Double value = (Double) cell.getValue().getValue();

            // Sum
            sum += value;

            // Minimum?
            if (value < min) {
                min = value;
            }

            // Maximum?
            if (value > max) {
                max = value;
            }

            // Only integer?
            if (integer && !(value == Math.floor(value)) && !Double.isInfinite(value)) {
                integer = false;
            }

            // Add value to array
            values[i++] = value;
        }

        // Calculate mean
        mean = sum / numberOfSamples;

        // Calculate standard deviation
        int tmp = 0;
        for (Cell cell : cells) {
            tmp += Math.pow(((Double) cell.getValue().getValue()) - mean, 2);
        }
        stdev = Math.sqrt(tmp / numberOfSamples);

        // Calculate median
        Arrays.sort(values);
        if (numberOfSamples % 2 == 0) {
            int v1 = numberOfSamples / 2;
            int v2 = v1 - 1;
            median = (values[v1] + values[v2]) / 2;
        } else {
            median = values[(int) Math.ceil(numberOfSamples / 2)];
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void writeToFile(JSONObject object) {
        // Write properties of column
        object.put(Xlsx2rocrateSchema.FORMAT_TYPE, "numeric");
        if (min != null) {
            object.put(Xlsx2rocrateSchema.MINIMUM, min);
        }
        if (max != null) {
            object.put(Xlsx2rocrateSchema.MAXIMUM, max);
        }
        if (mean != null) {
            object.put(Xlsx2rocrateSchema.MEAN, mean);
        }
        if (stdev != null) {
            object.put(Xlsx2rocrateSchema.STDEV, stdev);
        }
        if (median != null) {
            object.put(Xlsx2rocrateSchema.MEDIAN, median);
        }
        if (sum != 0.0) {
            object.put(Xlsx2rocrateSchema.SUM, sum);
        }
        object.put(Xlsx2rocrateSchema.INTEGER, integer);
    }
}
