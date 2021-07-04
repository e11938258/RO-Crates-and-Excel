package at.tuwien.rocreateprofil.model.entity.dataset.mapper.columnprofile;

import at.tuwien.rocreateprofil.model.entity.dataset.Column;
import at.tuwien.rocreateprofil.model.entity.profile.ColumnProfile;
import org.json.simple.JSONObject;

public interface ColumnProfileMapper {

    ColumnProfile map(JSONObject columnEntity, Column column);
}
