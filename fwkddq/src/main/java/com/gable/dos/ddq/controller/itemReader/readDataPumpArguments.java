package com.gable.dos.ddq.controller.itemReader;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gable.dos.ddq.controller.SpringConfig.*;

@Slf4j
public class readDataPumpArguments implements ItemReader {

    private File fileConfigFormat;
    public static List<String> field_config_name = new ArrayList<>();
    public static List<String> field_config_formats = new ArrayList<>();
    public static List<String> field_arg_name = new ArrayList<>();

    private boolean return_read = false;

    @Override
    public Object read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (!return_read) {

            log.info(String.valueOf(field_name));
            log.info(String.valueOf(row_num));

            String path_fileConfig = prop.getProperty("part.data_pump_config");
            fileConfigFormat = new File(path_fileConfig);

            boolean checkFile = fileConfigFormat.isFile();

            log.info("CheckFile = " + checkFile);
            if (checkFile) {
                getFormat_Row(path_fileConfig);
            }
            return_read = true;
            return "Object";
        }
        return null;
    }

    private void getFormat_Row(String path_fileConfig) {
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(path_fileConfig);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        XSSFWorkbook workbook = null;
        try {
            assert inputStream != null;
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert workbook != null;
        XSSFSheet sheet = workbook.getSheetAt(0); // sheet Config_format
        System.out.println("Get Sheet Name ==> " + sheet.getSheetName());

        CreationHelper createHelper = workbook.getCreationHelper();
        XSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        int lastRowNum = sheet.getLastRowNum();

        log.info("lastRowNum = " + lastRowNum);
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);

            Cell cellFieldName = row.getCell(1);
            Cell cellFormat = row.getCell(2);
            field_config_formats.add(cellFormat.getStringCellValue());
            field_config_name.add(cellFieldName.getStringCellValue());
        }
        log.info("field_config_name = \n" + field_config_name);
        log.info("size field_config_name = \n" + field_config_name.size());
        log.info("field_config_formats = \n" + field_config_formats);
        log.info("size field_config_formats = \n" + field_config_formats.size());


        //parse field name array
        String field_names = field_name.get(0);
        String[] tokens = field_names.split("/");

        field_arg_name.addAll(Arrays.asList(tokens));

        log.info("field_arg_name = \n" + field_arg_name);
        log.info("size field_arg_name = \n" + field_arg_name.size());
    }
}
