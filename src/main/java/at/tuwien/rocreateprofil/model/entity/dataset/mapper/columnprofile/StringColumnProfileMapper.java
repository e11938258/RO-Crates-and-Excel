package at.tuwien.rocreateprofil.model.entity.dataset.mapper.columnprofile;

import at.tuwien.rocreateprofil.model.entity.dataset.Column;
import at.tuwien.rocreateprofil.model.entity.profile.StringProfileColumn;
import at.tuwien.rocreateprofil.output.rocrate.Xlsx2rocrateSchema;
import org.json.simple.JSONObject;

public class StringColumnProfileMapper implements ColumnProfileMapper {

    @Override
    public StringProfileColumn map(JSONObject columnEntity, Column column) {
        Long minLength = (Long) columnEntity.get(Xlsx2rocrateSchema.MINIMUM_LENGTH);
        Long maxLength = (Long) columnEntity.get(Xlsx2rocrateSchema.MAXIMUM_LENGTH);
        Double avgLength = (Double) columnEntity.get(Xlsx2rocrateSchema.AVERAGE_LENGTH);
        Double stdev = (Double) columnEntity.get(Xlsx2rocrateSchema.STDEV_LENGTH);

        StringProfileColumn profile = new StringProfileColumn();
        profile.setMin(minLength);
        profile.setMax(maxLength);
        profile.setAvr(avgLength);
        profile.setStdev(stdev);

        return profile;
    }
}
