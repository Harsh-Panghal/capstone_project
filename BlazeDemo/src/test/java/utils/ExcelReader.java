package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    public List<Map<String, String>> getData(String excelFilePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        Workbook workbook = null;
        try {
            FileInputStream fis = new FileInputStream(new File(excelFilePath));
            workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet(sheetName);
            
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in Excel file!");
            }

            Row headerRow = sheet.getRow(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row currentRow = sheet.getRow(i);
                Map<String, String> rowMap = new LinkedHashMap<>();
                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    String key = headerRow.getCell(j).getStringCellValue();
                    String value = "";
                    Cell cell = currentRow.getCell(j);
                    if (cell != null) {
                        DataFormatter formatter = new DataFormatter();
                        value = formatter.formatCellValue(cell);
                    }
                    rowMap.put(key, value);
                }
                data.add(rowMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}