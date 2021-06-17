package at.tuwien.rocreateprofil.excel;

import at.tuwien.rocreateprofil.model.entity.RoCrateModel;

import java.io.File;

public interface ExcelPartParser {

    RoCrateModel parseInto(File sourceFile, RoCrateModel model);

}
