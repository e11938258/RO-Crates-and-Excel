package at.tuwien.rocreateprofil.excel;

import at.tuwien.rocreateprofil.model.entity.RoCrateModel;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExcelParser {

    private RoCrateModel model;
    private File excelFile;

    List<ExcelPartParser> parsers = Collections.emptyList();
    private ExcelFileMetadataParser excelFileMetadataParser;
    private ExcelContentsParser excelContentsParser;

    public ExcelParser(File excelFile) {
        this.model = new RoCrateModel();
        this.excelFile = excelFile;
        initializeParsers(excelFile);
    }

    private void initializeParsers(File excelFile) {
        excelFileMetadataParser = new ExcelFileMetadataParser();
        excelContentsParser = new ExcelContentsParser();
        this.parsers = Arrays.asList(excelFileMetadataParser, excelContentsParser);
    }

    public RoCrateModel buildModel(File excelFile) {
        for (ExcelPartParser parser: parsers) {
            model = parser.parseInto(this.excelFile, this.model);
        }

        return model;
    }
}
