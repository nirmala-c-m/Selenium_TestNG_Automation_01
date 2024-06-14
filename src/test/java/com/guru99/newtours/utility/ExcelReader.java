package com.guru99.newtours.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import java.text.DecimalFormat;

public class ExcelReader {

    public static String[][] readTestData(String filePath, String sheetName) {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getLastCellNum();

            String[][] testData = new String[rowCount - 1][colCount];
            DecimalFormat df = new DecimalFormat("#");

            for (int i = 1; i < rowCount; i++) { // Start from 1 to skip the header row
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);

                    if (cell == null) {
                        testData[i - 1][j] = ""; // Handle empty cells
                    } else {
                        switch (cell.getCellType()) {
                            case STRING:
                                testData[i - 1][j] = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    testData[i - 1][j] = cell.getDateCellValue().toString();
                                } else {
                                    testData[i - 1][j] = df.format(cell.getNumericCellValue());
                                }
                                break;
                            case BOOLEAN:
                                testData[i - 1][j] = String.valueOf(cell.getBooleanCellValue());
                                break;
                            default:
                                testData[i - 1][j] = "";
                        }
                    }
                }
            }

            return testData;
        } catch (IOException e) {
            e.printStackTrace();
            return new String[0][0]; // Return empty array in case of an exception
        }
    }

    public static void main(String[] args) {
        String filePath = "/home/nirmala/testdata.xlsx"; // Update the path to the Excel file

        // Reading LoginData sheet
        System.out.println("Reading LoginData sheet:");
        String loginSheet = "LoginData";
        String[][] loginData = readTestData(filePath, loginSheet);
        for (String[] row : loginData) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }

        // Reading RegisterData sheet
        System.out.println("Reading RegisterData sheet:");
        String registerSheet = "RegisterData";
        String[][] registerData = readTestData(filePath, registerSheet);
        for (String[] row : registerData) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
    }
}
