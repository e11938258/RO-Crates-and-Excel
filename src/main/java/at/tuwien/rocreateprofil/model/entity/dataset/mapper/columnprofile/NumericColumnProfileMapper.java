package at.tuwien.rocreateprofil.model.entity.dataset.mapper.columnprofile;

import at.tuwien.rocreateprofil.model.entity.dataset.Column;
import at.tuwien.rocreateprofil.model.entity.profile.NumericProfileColumn;
import at.tuwien.rocreateprofil.output.rocrate.Xlsx2rocrateSchema;
import org.json.simple.JSONObject;

public class NumericColumnProfileMapper implements ColumnProfileMapper {


    @Override
    public NumericProfileColumn map(JSONObject columnEntity, Column column) {
        Double min = (Double) columnEntity.get(Xlsx2rocrateSchema.MINIMUM);
        Double max = (Double) columnEntity.get(Xlsx2rocrateSchema.MAXIMUM);
        Double mean = (Double) columnEntity.get(Xlsx2rocrateSchema.MEAN);
        Double stdev = (Double) columnEntity.get(Xlsx2rocrateSchema.STDEV);
        Double median = (Double) columnEntity.get(Xlsx2rocrateSchema.MEDIAN);
        Double sum = (Double) columnEntity.get(Xlsx2rocrateSchema.SUM);
        Boolean isIntegerOnly = (Boolean) columnEntity.get(Xlsx2rocrateSchema.INTEGER);

        NumericProfileColumn numericProfileColumn = new NumericProfileColumn();
        numericProfileColumn.setMin(min);
        numericProfileColumn.setMax(max);
        numericProfileColumn.setMean(mean);
        numericProfileColumn.setStdev(stdev);
        numericProfileColumn.setMedian(median);
        numericProfileColumn.setSum(sum);
        numericProfileColumn.setInteger(isIntegerOnly);

        return numericProfileColumn;
    }
}
