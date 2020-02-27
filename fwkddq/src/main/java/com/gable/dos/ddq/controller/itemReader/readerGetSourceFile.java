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
public class readerGetSourceFile implements ItemReader<HashMap<String, ArrayList<String>>> {


    private static ArrayList<String> list_header;
    private static HashMap<String, ArrayList<String>> hashMap_header;
    public static final String SAMPLE_XLSX_FILE_PATH = "./201402_trip_data.xlsx";

    private DataFormatter dataFormatter = new DataFormatter();
    private static int i = 0;
    private boolean returns = false;

    int index_sheet = 0;

    @Override
    public HashMap<String, ArrayList<String>> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

//        setProperty of File Name
//        System.setProperty("FileName", ".//201402_trip_data.csv");

        FileInputStream excelFile = new FileInputStream(new File(SAMPLE_XLSX_FILE_PATH));

        Workbook workbook = new XSSFWorkbook(excelFile);

        log.info("Workbook has : " + workbook.getNumberOfSheets() + " Sheets");

        for (Sheet sheet : workbook) {
            log.info("Sheet Name is : " + sheet.getSheetName());
        }

        if (!returns) {
            returns = true;
            return getHeaderOfSheet(workbook);
        }
        return null;
    }

    private HashMap<String, ArrayList<String>> getHeaderOfSheet(Workbook workbook) {
        hashMap_header = new HashMap<>();

        for (Sheet sheet : workbook) {
            list_header = new ArrayList<>();
            log.info("Start Process Sheet Name : [ " + sheet.getSheetName() + " ]");
            Row row = sheet.getRow(0);
            log.info("Number of cell : " + row.getLastCellNum());
            for (Cell cell : row) {
                log.info(cell.getStringCellValue());
                list_header.add(cell.getStringCellValue());
            }

            log.info("Header List : " + list_header);
            hashMap_header.put(sheet.getSheetName(), list_header);
            System.out.println("");
            System.setProperty("SheetName_" + index_sheet, sheet.getSheetName());
            index_sheet++;
        }

        System.out.println("hashMap_header ss: " + hashMap_header);

        return hashMap_header;
    }
}
