package at.tuwien.rocreateprofil.output.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class WorkbookWriter {

    public static void write(HSSFWorkbook workbook, String filename) {
        OutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("output/" + filename);
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
