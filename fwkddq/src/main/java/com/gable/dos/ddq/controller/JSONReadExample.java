package com.gable.dos.ddq.controller;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

@Slf4j
public class JSONReadExample {

    public static void main(String[] args) throws Exception {

        // parsing file "JSONExample.json"
        Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));

        // typecasting obj to JSONArray
        JSONArray all_user = (JSONArray) obj;

        // Create a Workbook
        Workbook workbooks = new XSSFWorkbook();     // new HSSFWorkbook() for generating `.xls` file

        // Create a Sheet
        Sheet sheet = workbooks.createSheet("SheetTest");

        Drawing<?> drawing = sheet.createDrawingPatriarch();



        // Create a Font for styling header cells
        Font headerFont = workbooks.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 18);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbooks.createCellStyle();
        headerCellStyle.setFont(headerFont);


        // Create a Font for styling header cells
        Font errorFont = workbooks.createFont();
        errorFont.setBold(true);
        errorFont.setFontHeightInPoints((short) 14);
        errorFont.setColor(IndexedColors.RED.getIndex());

        // Create a CellStyle with the font
        CellStyle errorCellStyle = workbooks.createCellStyle();
        errorCellStyle.setFont(errorFont);


        //Make Boolean Check CreateHeader
        Boolean isCreateHeader = true;

        // Create a Row
        Row headerRow = sheet.createRow(0);

        //เตรียมแกะค่าใน all_user (JSONArray)
        Iterator users = all_user.iterator();
        XSSFCreationHelper createHelper = (XSSFCreationHelper) workbooks.getCreationHelper();
        XSSFClientAnchor anchor = createHelper.createClientAnchor();
        while (users.hasNext()) {

            JSONObject user = (JSONObject) users.next();

            //Make New Boolean Receive isCreateHeader
            Boolean finalIsCreateHeader = isCreateHeader;


            int rowNum = sheet.getLastRowNum() + 1;

            System.out.println("Rownum is : " + rowNum);

            Row row = sheet.createRow(rowNum);

            user.forEach((key, value) -> {


                System.out.println(key + "/" + value);
                int cellNum = row.getLastCellNum() < 0 ? 0 : row.getLastCellNum();

                if (finalIsCreateHeader) {
                    Cell cell = headerRow.createCell(cellNum);
                    cell.setCellValue(key.toString());
                    cell.setCellStyle(headerCellStyle);
                }


                System.out.println(key.toString() + " : " + value.toString());
                row.createCell(cellNum).setCellValue(value.toString());

                if (value.equals("Benx")){
                    Comment comment = drawing.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
                    comment.setString(new XSSFRichTextString("{"+ value.toString() + "} this wrong  please edit to ==> { BENZ }"));
                    row.getCell(cellNum).setCellStyle(errorCellStyle);
                    row.getCell(cellNum).setCellComment(comment);
                }
                if (value.equals("Mansal")){

                    Comment comment1 = drawing.createCellComment(anchor);
                    comment1.setString(new XSSFRichTextString("{"+ value.toString() + "} this wrong  please edit to ==> { Mansak }"));
                    row.getCell(cellNum).setCellStyle(errorCellStyle);
                    row.getCell(cellNum).setCellComment(comment1);
                }

            });
            isCreateHeader = false;
        }


        //autoSizeColumn
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("JSON-generated-file.xlsx");
        workbooks.write(fileOut);
        fileOut.close();

        workbooks.close();


    }
}

