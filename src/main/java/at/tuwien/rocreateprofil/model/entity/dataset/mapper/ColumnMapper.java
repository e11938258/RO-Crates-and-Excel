package at.tuwien.rocreateprofil.model.entity.dataset.mapper;

import at.tuwien.rocreateprofil.model.entity.dataset.Column;
import at.tuwien.rocreateprofil.model.entity.dataset.ColumnType;
import at.tuwien.rocreateprofil.model.entity.dataset.mapper.columnprofile.ColumnProfileMapper;
import at.tuwien.rocreateprofil.model.entity.profile.CategoricalProfileColumn;
import at.tuwien.rocreateprofil.model.entity.profile.ColumnProfile;
import at.tuwien.rocreateprofil.model.entity.profile.NumericProfileColumn;
import at.tuwien.rocreateprofil.model.entity.profile.StringProfileColumn;
import at.tuwien.rocreateprofil.output.rocrate.RoCrateSchema;
import at.tuwien.rocreateprofil.output.rocrate.Xlsx2rocrateSchema;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnMapper {

    private static final List<ColumnProfile> columnProfiles = Arrays.asList(new StringProfileColumn(), new NumericProfileColumn(), new CategoricalProfileColumn());

    public static Column fromJSON(JSONObject columnContextEntity) {
        // extract from JSON
        String id = (String) columnContextEntity.get(RoCrateSchema.ID);
        String name = (String) columnContextEntity.get(Xlsx2rocrateSchema.COLUMN_ID);
        String headerName = (String) columnContextEntity.get(Xlsx2rocrateSchema.COLUMN_NAME);
        Boolean missingValues = (Boolean) columnContextEntity.get(Xlsx2rocrateSchema.MISSING_VALUES);

        // map column properties
        Column column = new Column(name, id);
        column.setMissingValues(missingValues);
        column.setHeaderName(headerName);

        // profile
        ColumnProfile profile = mapColumnProfile(columnContextEntity, column);
        column.setColumnProfile(profile);

        return column;
    }

    private static ColumnProfile mapColumnProfile(JSONObject columnContextEntity, Column column) {
        // map sub-objects
        String columnType = (String) columnContextEntity.get(Xlsx2rocrateSchema.FORMAT_TYPE);
        ColumnType type = ColumnType.fromString(columnType);

        List<ColumnProfile> profileMapper = columnProfiles
                .stream()
                .filter(profile -> profile.getColumnType().equals(type))
                .collect(Collectors.toList());
        if (profileMapper.size() == 0) {
            throw new IllegalStateException("Unknown " + Xlsx2rocrateSchema.FORMAT_TYPE + " " + type);
        }
        ColumnProfileMapper mapper = profileMapper.get(0).getProfileMapper();
        ColumnProfile profile = mapper.map(columnContextEntity, column);
        return profile;
    }
}
