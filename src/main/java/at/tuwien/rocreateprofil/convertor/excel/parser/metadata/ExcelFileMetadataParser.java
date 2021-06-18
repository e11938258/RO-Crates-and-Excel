package at.tuwien.rocreateprofil.convertor.excel.parser.metadata;

import at.tuwien.rocreateprofil.convertor.ExcelFileDecompressor;
import at.tuwien.rocreateprofil.convertor.excel.parser.ExcelParser;
import at.tuwien.rocreateprofil.model.entity.RoCrateModel;

import java.io.File;
import java.nio.file.Path;

public class ExcelFileMetadataParser implements ExcelParser {

    @Override
    public RoCrateModel parseInto(File excelFile, RoCrateModel model) {
        ExcelFileDecompressor.unzip(excelFile);
        Path extractedPath = ExcelFileDecompressor.buildExtractedPath(excelFile);
        ExcelCoreMetadataXmlParser.parseIntoModel(extractedPath, model);

        return model;
    }
}
