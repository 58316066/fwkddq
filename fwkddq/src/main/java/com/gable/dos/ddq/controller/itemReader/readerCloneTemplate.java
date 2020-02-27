package com.gable.dos.ddq.controller.itemReader;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
public class readerCloneTemplate implements ItemReader<Object> {

    public static final String SAMPLE_XLSX_FILE_PATH = "./Spec-Query-to-Excel-Template.xlsx";
    public static final String NEW_SHEET_NAME = "Output";
    public static ArrayList list = new ArrayList<String>();
    public static int rowNum;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        FileInputStream excelFile = new FileInputStream(new File(SAMPLE_XLSX_FILE_PATH));
        Workbook workbook = new XSSFWorkbook(excelFile);
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        for (Sheet sheet : workbook) {
            log.info("sheetName = " + sheet.getSheetName());
        }


        Sheet sheet_output = workbook.getSheet(NEW_SHEET_NAME);

        if (sheet_output != null) {
            log.info("Sheet have [" + NEW_SHEET_NAME + "]");
            workbook.removeSheetAt(1);
            log.info("Remove Sheet [" + NEW_SHEET_NAME + "] Success");
            cloneSheet(workbook, 0, NEW_SHEET_NAME);
            writefileUpdate(workbook);
        } else {
            log.info("Sheet no have [" + NEW_SHEET_NAME + "]");
            cloneSheet(workbook, 0, NEW_SHEET_NAME);
            writefileUpdate(workbook);
        }


        Sheet sheetTemplate = workbook.getSheetAt(0);
        System.out.println("XSSFWorkbook get Sheet name index[0] ::> " + sheetTemplate.getSheetName());

        DataFormatter dataFormatter = new DataFormatter();

        for (Row row : sheetTemplate) {
            for (Cell cell : row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                if (cellValue.startsWith("P", 0) || cellValue.startsWith("p", 0)) {
                    HashMap<String, Integer> hashMap = new HashMap<>();
                    System.out.println("[" + cellValue + "] \t");
                    System.out.println("getRowIndex = " + cell.getRowIndex());
                    System.out.println("getColumnIndex = " + cell.getColumnIndex());
                    hashMap.put(cellValue, cell.getColumnIndex());
                    list.add(hashMap);
                    rowNum = cell.getRowIndex();
                }
            }
        }
        System.out.println("List : " + list);

//        FileOutputStream output = new FileOutputStream(new File(SAMPLE_XLSX_FILE_PATH));
//        workbook.write(output);
//        output.close();
//        workbook.close();
        return null;

    }

    private void writefileUpdate(Workbook workbook) {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(SAMPLE_XLSX_FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Write Workbook Success!!!");

    }

    public static Sheet cloneSheet(final Workbook workbook, final int sheetNo, final String name) {
        final Sheet sheet = workbook.cloneSheet(sheetNo);
        final int newSheetNo = workbook.getSheetIndex(sheet);

        workbook.setSheetName(newSheetNo, name);
        log.info("Clone Sheet [" + NEW_SHEET_NAME + "] Success!!");
        return sheet;
    }

}
