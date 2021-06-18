package at.tuwien.rocreateprofil.convertor.excel.parser;

import at.tuwien.rocreateprofil.model.entity.RoCrateModel;

import java.io.File;

public class ExcelContentsParser implements ExcelParser {

    @Override
    public RoCrateModel parseInto(File sourceFile, RoCrateModel model) {
        return model;
    }

}
