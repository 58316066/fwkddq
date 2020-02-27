package com.gable.dos.ddq.controller;

import com.gable.dos.ddq.library.Connection_execute_file;
import com.gable.dos.ddq.library.Connection_executes;
import com.gable.dos.ddq.library.Connection_executes_result_set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

import static com.gable.dos.ddq.controller.SpringConfig.logger;
import static com.gable.dos.ddq.controller.SpringConfig.prop;
import static com.gable.dos.ddq.controller.itemReader.readCheckFileName.returns_step4;
import static com.gable.dos.ddq.controller.itemReader.read_actual_length.returns_step5;
import static com.gable.dos.ddq.controller.itemReader.readerDDLScript.returns_step3;
import static com.gable.dos.ddq.controller.itemReader.readerGetSourceFile_csv.returns_step2;
import static com.gable.dos.ddq.controller.itemReader.reader_csv_file.fileNo;
import static com.gable.dos.ddq.controller.itemReader.reader_csv_file.returns_step1;

@Slf4j
public class write_templatetable_to_newtable implements ItemWriter<String>, StepExecutionListener {
    private final String LOCATION_PATH = "datasource/context-datasource.xml";
    private final String CONTEXT_BEAN = "dataSource";
    private boolean execute_CreateTable, execute_DDL_ActualFile = false;


    @Override
    public void write(List<? extends String> items) throws Exception {

        String ddl = items.get(0);

        try {
            File file = new File(prop.getProperty("sql.ddl_actual") + "./DDL_Actual_" + System.getProperty("tmp_tbl_name") + ".sql");

            FileWriter writer;
            writer = new FileWriter(file, false);
            writer.write(ddl);
            writer.close();
            log.info("Write DDL_Actual File Success!");
            log.info(file.getPath());
            logger.info("Write DDL script actual file to " + file.getPath());
            Connection_execute_file connection_execute_file = new Connection_execute_file();
            connection_execute_file.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, file.getPath());
            execute_DDL_ActualFile = true;

            createNewTable();

        } catch (Exception e) {
            execute_DDL_ActualFile = false;
            log.error(e.getMessage());
        }
    }

    private void createNewTable() {
        logger.info("Create SQL Create newTable_Name [" + System.getProperty("newTable_Name") + "]");
        String SQLCreateNewTable = "INSERT INTO " + System.getProperty("newTable_Name") + "  SELECT * FROM " + System.getProperty("tmp_tbl_name") + ";";
        log.info("SQLCreateNewTable = " + SQLCreateNewTable);
        logger.info(SQLCreateNewTable);
        Connection_executes connection_executes = new Connection_executes();
        try {
            execute_CreateTable = connection_executes.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQLCreateNewTable);

            // export data to csv file
            sqlToCSV("select * from " + System.getProperty("newTable_Name"), prop.getProperty("part.Export"));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public String sqlToCSV(String query, String filename) {
        logger.info("Create SQL [" + query + "]");

        File theDir = new File(prop.getProperty("part.Export"));
        if (!theDir.exists()) {
            logger.info("Create directory [" + theDir.getName() + "]");
            log.info("creating directory: " + theDir.getName());
            boolean result = false;

            try {
                theDir.mkdir();
                logger.info("Create directory [" + theDir.getName() + "] success!");
                result = true;
            } catch (SecurityException se) {
                logger.warning("Create directory [" + theDir.getName() + "] failed!");
                logger.warning(se.getMessage());
                //handle it
            }
            if (result) {
                log.info("DIR created");
            }
        }

        log.info("creating csv file: " + filename);

        try {
            Connection_executes_result_set connection_executes = new Connection_executes_result_set();

            FileWriter fw = new FileWriter(filename + new File(System.getProperty("file_input")).getName());

            ResultSet rs = connection_executes.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, query);

            int cols = rs.getMetaData().getColumnCount();
            log.info("columnMetaData = " + cols);

            while (rs.next()) {

                for (int i = 1; i <= cols; i++) {
                    fw.append(rs.getString(i));
                    if (i < cols) fw.append(',');
                }
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            logger.info("Get ResultSet write to .csv file");
            log.info("CSV File is created successfully.");
//            return file_output;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            log.error(e.getMessage());
        }
        return null;
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        log.info("execute_DDL_ActualFile = " + execute_DDL_ActualFile + "\nexecute_CreateTable = " + execute_CreateTable);
        if (!execute_DDL_ActualFile || !execute_CreateTable) {
            logger.warning("Process csv file failed!");
            return ExitStatus.FAILED;
        }
        // delete index 0 in file csv lists.

        log.info("afterStep check fileNo of lists = " + fileNo.size());

        if (fileNo.size() != 0) {
            fileNo.remove(0);
            returns_step1 = false;
            returns_step2 = false;
            returns_step3 = false;
            returns_step4 = false;
            returns_step5 = false;
            log.info("beforeStep check fileNo of lists = " + fileNo.size());
            logger.info("Process csv file " + System.getProperty("filename") + " success!");
            return new ExitStatus("SUCCESS");
        }
        logger.info("Process csv file " + System.getProperty("filename") + " success!");
        return ExitStatus.COMPLETED;

    }
}
