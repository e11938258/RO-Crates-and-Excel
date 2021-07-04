package at.tuwien.rocreateprofil.output.excel;

import at.tuwien.rocreateprofil.model.entity.dataset.Column;
import at.tuwien.rocreateprofil.model.entity.dataset.Sheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class SheetInserter {

    public static void insertWithGeneratedValues(Sheet sheet, HSSFWorkbook workbook) {
        HSSFSheet insertedSheet = workbook.createSheet(sheet.getName());
        // insert rows
        insertHeader(insertedSheet, sheet);
        insertRowsWithGeneratedValues(sheet, insertedSheet);
    }

    private static void insertHeader(HSSFSheet insertedSheet, Sheet sheet) {
        HSSFRow headerRow = insertedSheet.createRow(0);
        for (Column column : sheet.getColumns()) {
            int columnIndex = mapColumnLetterToNumber(column.getName());
            headerRow.createCell(columnIndex).setCellValue(column.getHeaderName());
        }
    }

    private static void insertRowsWithGeneratedValues(Sheet sheet, HSSFSheet insertedSheet) {
        int rowsInserted = 1;
        while (rowsInserted <= sheet.getNumberOfRows()) {
            HSSFRow insertedRow = insertedSheet.createRow(rowsInserted);
            for (Column column : sheet.getColumns()) {
                int columnIndex = mapColumnLetterToNumber(column.getName());
                String value = column.generateValidValueForColumn();
                insertedRow.createCell(columnIndex).setCellValue(value);
            }
            rowsInserted++;
        }
    }

    private static int mapColumnLetterToNumber(String columnName) {
        int firstLetterOffset = 'A';
        firstLetterOffset -= 1; // we want to set A to 1 to calculate multiletter columns correctly
        int alphabetLength = 26;
        int fullAlphabetPasses = columnName.length()-1;
        int columnNumber = 0;
        for (char currentLetter: columnName.toCharArray()) {
            guardCharacterIsCapitalLetter(currentLetter);
            columnNumber += currentLetter - firstLetterOffset;
            if (fullAlphabetPasses > 0) {
                //account for the multiletter indexing
                columnNumber += Math.pow(alphabetLength,fullAlphabetPasses);
            }
            fullAlphabetPasses--;
        }
        return columnNumber - columnName.length(); // 1 to account for offset 1 in case of the first A
    }

    private static void guardCharacterIsCapitalLetter(char c) {
        if ((int)c < (int)'A' || (int)c > (int)'Z') {
            throw new IllegalArgumentException("Character '" + c + "' which is part of a column name is not within A-Z range"); //TODO: @marwin turn this into a proper error + exception
        }
    }

}
