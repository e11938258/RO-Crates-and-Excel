package at.tuwien.rocreateprofil.convertor.excel.parser;

import at.tuwien.rocreateprofil.model.entity.RoCrateModel;

import java.io.File;

public interface ExcelParser {

    RoCrateModel parseInto(File sourceFile, RoCrateModel model);

}
