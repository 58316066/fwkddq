package com.gable.dos.ddq.controller.itemWriter;

import com.gable.dos.ddq.library.Connection_execute_bulk_file;
import com.gable.dos.ddq.controller.itemReader.readCheckFileName;
import com.gable.dos.ddq.controller.itemReader.readerDDLScript;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

import static com.gable.dos.ddq.controller.SpringConfig.logger;

@Slf4j
public class write_File_to_table implements ItemWriter<String>, StepExecutionListener {
    //    private final String LOCATION_PATH = "datasource/context-postgres.xml";
//    private final String CONTEXT_BEAN = "myDataSource";
    private final String LOCATION_PATH = "datasource/context-datasource.xml";
    private final String CONTEXT_BEAN = "dataSource";
    private boolean execute = false;


    @Override
    public void write(List<? extends String> sql) throws Exception {
        String sql_load_file = sql.get(0);
        logger.info("Execute SQL bulk load file");
        Connection_execute_bulk_file executeBulkFile = new Connection_execute_bulk_file();
        execute = executeBulkFile.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, sql_load_file);


    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        if (!execute) {
            log.info("ExitStatus = FAILED");
            // re config
            readerDDLScript.returns_step3 = false;
            readCheckFileName.returns_step4 = false;
            String ddl_length = System.getProperty("ddl.length");
            log.info("ddl_length = " + ddl_length);
            switch (ddl_length) {
                case "255":
                    System.setProperty("ddl.length", "512");
                    break;
                case "512":
                    System.setProperty("ddl.length", "1024");
                    break;
                case "1024":
                    System.setProperty("ddl.length", "2048");
                    break;
                case "2048":
                    System.setProperty("ddl.length", "4096");
                    break;
                default:
                    return new ExitStatus("EXIT");
            }
            return new ExitStatus("FAILED");
        } else {
            log.info("ExitStatus = COMPLETED");
            return new ExitStatus("COMPLETED");
        }
    }
}
