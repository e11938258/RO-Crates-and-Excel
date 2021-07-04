package at.tuwien.rocreateprofil.model.entity.profile;

import at.tuwien.rocreateprofil.model.entity.dataset.Cell;
import at.tuwien.rocreateprofil.model.entity.dataset.ColumnType;
import at.tuwien.rocreateprofil.model.entity.dataset.mapper.columnprofile.ColumnProfileMapper;
import at.tuwien.rocreateprofil.model.entity.dataset.mapper.columnprofile.StringColumnProfileMapper;
import at.tuwien.rocreateprofil.output.rocrate.Xlsx2rocrateSchema;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;

import java.nio.charset.Charset;
import java.util.List;

public class StringProfileColumn implements ColumnProfile {

    private Long min = Long.MAX_VALUE, max = Long.MIN_VALUE;
    private Double avr = null;
    private boolean isAlphaNumericWithWhitespace = false;

    @Override
    public void build(List<Cell> cells) {
        Double sum = 0.0;

        for (Cell cell : cells) {
            final String value = (String) cell.getValue().getValue();

            // Sum
            sum += value.length();

            // Minimum?
            if (value.length() < min) {
                min = (long) value.length();
            }

            // Maximum?
            if (value.length() > max) {
                max = (long) value.length();
            }
        }

        // Calculate average length
        avr = sum / cells.size();

        // Get encoding
        Charset.defaultCharset().displayName();
    }

    @Override
    public ColumnType getColumnType() {
        return ColumnType.STRING;
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

    @Override
    public ColumnProfileMapper getProfileMapper() {
        return new StringColumnProfileMapper();
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Double getAvr() {
        return avr;
    }

    public void setAvr(double avr) {
        this.avr = avr;
    }

    @Override
    public String generateValidValue() {
        int numberOfCharacters = (int) ((int) (Math.random() * (max - min)) + min);
        return RandomStringUtils.randomAlphabetic(numberOfCharacters);
    }

}
