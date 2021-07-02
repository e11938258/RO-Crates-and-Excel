package at.tuwien.rocreateprofil.convertor;


import at.tuwien.rocreateprofil.convertor.excel.parser.content.ExcelAppContentParser;
import at.tuwien.rocreateprofil.convertor.excel.parser.metadata.ExcelMetadataParser;
import at.tuwien.rocreateprofil.model.entity.rocrate.RoCrate;
import at.tuwien.rocreateprofil.model.entity.rocrate.meta.util.RoCrateMetaUtil;
import org.json.simple.JSONArray;

import java.io.File;
import java.net.URL;
import java.util.Map;

public class Excel2RoCrateConvertor implements Convertor {

    public static final String EXCEL_FILE = "EXCEL_FILE";
    public static final String LICENSE_URL = "LICENSE_URL";
    private static final String OUTPUT_LOCATION = "/"; // TODO: @marwin would be nice to make this parametrized by args

    private ExcelMetadataParser excelFileMetadataParser;
    private ExcelAppContentParser excelContentsParser;


    private File excelFile;
    private URL licenseURL;

    private RoCrate roCrate;

    public Excel2RoCrateConvertor(Map<String, Object> paramMap) {
        setVariablesFromParamMap(paramMap);
        this.excelFileMetadataParser = new ExcelMetadataParser();
        this.excelContentsParser = new ExcelAppContentParser();
    }

    private void setVariablesFromParamMap(Map<String, Object> paramMap) {
        this.excelFile = (File) paramMap.get(EXCEL_FILE);
        this.licenseURL = (URL) paramMap.get(LICENSE_URL);
    }

    @Override
    public void convert() {
        // build Ro-crate skeleton
        roCrate = new RoCrate(OUTPUT_LOCATION, licenseURL);
        // append file information
        roCrate.addExcelFile(excelFile);
        // extend the Ro-crate metadata with context entities derived from the information inside the excel metadata files
        JSONArray roCrateMetadataGraph = RoCrateMetaUtil.getRoCrateMetadataGraphArray(roCrate.getRoCrateMetadata());
        excelFileMetadataParser.parseInto(roCrateMetadataGraph, excelFile);
        // extend the Ro-crate metadata with the structural information of the dataset inside the excel file
        excelContentsParser.parseInto(roCrateMetadataGraph, excelFile);
    }

    @Override
    public void writeOutput() {
        roCrate.write();
    }
}
