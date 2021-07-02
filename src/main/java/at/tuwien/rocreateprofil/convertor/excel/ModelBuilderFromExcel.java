package at.tuwien.rocreateprofil.convertor.excel;

import at.tuwien.rocreateprofil.convertor.excel.parser.content.ExcelAppContentParser;
import at.tuwien.rocreateprofil.convertor.excel.parser.metadata.ExcelMetadataParser;
import at.tuwien.rocreateprofil.convertor.excel.parser.ExcelParser;
import at.tuwien.rocreateprofil.model.entity.RoCrateModel;
import at.tuwien.rocreateprofil.model.entity.rocrate.RoCrate;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ModelBuilderFromExcel {

    private RoCrateModel model;
    private String excelFileLocation;

    List<ExcelParser> parsers = Collections.emptyList();
    private ExcelMetadataParser excelFileMetadataParser;
    private ExcelAppContentParser excelContentsParser;

    public ModelBuilderFromExcel(String excelFile) {
        this.model = new RoCrateModel();
        this.excelFileLocation = excelFile;
        initializeParsers(excelFile);
    }

    private void initializeParsers(String excelFile) {
        ExcelFileGuard.guardValidExcelFile(excelFile);
        excelFileMetadataParser = new ExcelMetadataParser();
        excelContentsParser = new ExcelAppContentParser();
        this.parsers = Arrays.asList(excelFileMetadataParser, excelContentsParser);
    }

    public RoCrateModel buildModel() {
        File excelFile = new File(excelFileLocation);
        for (ExcelParser parser : parsers) {
//            model = parser.parseInto(excelFile, this.model);
        }

        return model;
    }
}
