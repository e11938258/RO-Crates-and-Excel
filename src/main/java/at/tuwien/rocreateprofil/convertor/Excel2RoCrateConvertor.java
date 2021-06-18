package at.tuwien.rocreateprofil.convertor;


import at.tuwien.rocreateprofil.convertor.excel.ModelBuilderFromExcel;
import at.tuwien.rocreateprofil.model.entity.RoCrateModel;
import at.tuwien.rocreateprofil.output.rocrate.RoCrateOutputCreator;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Map;

public class Excel2RoCrateConvertor implements Convertor {

    public static final String EXCEL_FILE_LOCATION = "EXCEL_LOCATION";

    private String excelLocation;

    private ModelBuilderFromExcel excelParser;
    private RoCrateModel model;

    public Excel2RoCrateConvertor(Map<String, Object> paramMap) {
        setVariablesFomrParamMap(paramMap);
        excelParser = new ModelBuilderFromExcel(excelLocation);
    }

    private void setVariablesFomrParamMap(Map<String, Object> paramMap) {
        this.excelLocation = (String) paramMap.get(EXCEL_FILE_LOCATION);
    }

    @Override
    public void convert() {
        // parse data into model
        this.model = excelParser.buildModel();
        writeOutput();

    }

    @Override
    public void writeOutput() {
        RoCrateOutputCreator.createRoCrateFromModel(model);
    }
}
