package at.tuwien.rocreateprofil.convertor.excel.parser;

import org.json.simple.JSONArray;

import java.io.File;

public interface ExcelParser {

    void parseInto(JSONArray roCrateMetadataGraphArray, File sourceFile);

}
