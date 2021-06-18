package at.tuwien.rocreateprofil.convertor;

import org.apache.commons.lang3.NotImplementedException;

import java.io.File;
import java.util.Map;

public class RoCrate2ExcelConvertor implements Convertor {

    public static final String RO_CRATE_ROOT = "RO_CRATE_ROOT";

    private File file;

    public RoCrate2ExcelConvertor(Map<String, Object> parameterMap) {

    }

    @Override
    public void convert() {
        throw new NotImplementedException();
    }

    @Override
    public void writeOutput() {
        throw new NotImplementedException();
    }
}
