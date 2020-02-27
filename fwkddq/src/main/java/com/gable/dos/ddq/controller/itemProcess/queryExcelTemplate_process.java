package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Config_Datetime;
import com.gable.dos.ddq.library.Connection_executes_result_set;
import com.gable.dos.ddq.controller.itemReader.readerCloneTemplate;
import com.gable.dos.ddq.model.TableAudit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Data
@Slf4j
public class queryExcelTemplate_process implements ItemProcessor<Object, Object>, JobExecutionListener, StepExecutionListener {
    //    private int lastVersion = 0;
//    private static String jobName = "";
    private String[] columns = {"TABLE_NAME", "FieldNo", "IS_NULLABLE", "SIZE"};


    private Connection_executes_result_set connectionLibrary;
    private final String LOCATION_PATH = "datasource/context-Microsoft-SQL-Server.xml";
    private final String CONTEXT_BEAN = "SqlServer";
    private String SQL;
    private ResultSet rs;
    private Config_Datetime config_datetime = new Config_Datetime();

    /*get Date Config*/
    private Date date = config_datetime.dateConfig();
    private Date dateString = Config_Datetime.dateString;

    /* initial poi lib */
    private static File file = null;
    private static Sheet sheet;
    private static FileOutputStream fileOut;
    private static int LastRowNum;
    private static XSSFWorkbook workbook;
    private static CellStyle dateCellStyle;

    public static final String SAMPLE_XLSX_FILE_PATH = "./Spec-Query-to-Excel-Template.xlsx";
    private ArrayList arrayList = readerCloneTemplate.list;
    private FileInputStream inputStream;
    private int columnCount = 0;

    @Override
    public Object process(Object object) throws Exception {

        if (object.getClass().equals(TableAudit.class)) {
            return FuncQueryCountRecord((TableAudit) object);
        }
        return null;
    }


    private Object FuncQueryCountRecord(TableAudit table) throws ParseException {

        // new class ProcessTestInterface
        connectionLibrary = new Connection_executes_result_set();

        // config sql_Query (1)
        SQL = "select a.TABLE_NAME as P1 , count(*) as P2\n" +
                "from\n" +
                "information_schema.COLUMNS a\n" +
                "where \n" +
                "a.TABLE_SCHEMA = '" + table.getSchema() + "'\n" +
                "and a.TABLE_NAME = '" + table.getTable_name() + "' \n" +
                "GROUP BY a.TABLE_NAME;";
        System.out.println("Query  " + SQL);

        rs = connectionLibrary.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);
        try {
            writers(rs);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void writers(ResultSet rs) throws IOException, InvalidFormatException {
        try {
            columnCount = rs.getMetaData().getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        try {
//            System.out.println(" rs.getMetaData(); = " + rs.getMetaData().getColumnCount());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int lastRow = sheet.getLastRowNum();

            sheet.shiftRows(lastRow - 1, lastRow, 1);
            Row row = sheet.createRow(lastRow - 1);

            for (Object hashMap : arrayList) {
                HashMap<String, Integer> template_key = (HashMap) hashMap;

                if (template_key.containsKey("P1")) {
//                    System.out.println("P1 Value ==> " + template_key.get("P1"));
                    try {
                        row.createCell(template_key.get("P1"))
                                .setCellValue(rs.getString("P1"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (template_key.containsKey("P2")) {
//                    System.out.println("P2 Value ==> " + template_key.get("P2"));
                    try {
                        row.createCell(template_key.get("P2"))
                                .setCellValue(rs.getInt("P2"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (template_key.containsKey("P3")) {
                    try {
                        row.createCell(template_key.get("P3"))
                                .setCellValue(rs.getString("P3"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (template_key.containsKey("P4")) {
                    try {
                        row.createCell(template_key.get("P4"))
                                .setCellValue(rs.getString("P4"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                if (template_key.containsKey("PCD")) {
                    Cell dateOfProcessDate = row.createCell(template_key.get("PCD"));
                    dateOfProcessDate.setCellValue(new Date());
                    dateOfProcessDate.setCellStyle(dateCellStyle);

                }

            }

//            System.out.println("shiftRows starting..." + new DateTime());
        }
    }


    @Override
    public void beforeJob(JobExecution jobExecution) {
//        jobName = jobExecution.getJobInstance().getJobName();
//        log.info("This JobName = " + jobName);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {


    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("BeforeStep Started");
        try {
            inputStream = new FileInputStream(SAMPLE_XLSX_FILE_PATH);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        try {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sheet = workbook.getSheetAt(1);
        System.out.println("Get Sheet Name ==> " + sheet.getSheetName());

        CreationHelper createHelper = workbook.getCreationHelper();
        dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
//        Row removingRow = sheet.getRow(rowNum);
//        sheet.removeRow(removingRow);
//        sheet.shiftRows(rowNum, rowNum + 1, 1);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        for (int i = 0; i < arrayList.size(); i++) {
            sheet.autoSizeColumn(i);
        }
        try {
            System.out.println("------------------ afterStep ------------------");
            XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            fileOut = new FileOutputStream(SAMPLE_XLSX_FILE_PATH);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            System.out.println("------------------ workbook , fileOut Closed ------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
