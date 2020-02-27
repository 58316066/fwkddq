package com.gable.dos.ddq.controller.itemWriter;

import com.gable.dos.ddq.library.Connection_execute_file;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.io.*;
import java.util.List;

import static com.gable.dos.ddq.controller.SpringConfig.logger;
import static com.gable.dos.ddq.controller.SpringConfig.prop;

@Slf4j
@Configuration
@EnableBatchProcessing
@PropertySource("classpath:mongoconfig.properties")
public class WriterDDL_Create_Tables implements ItemWriter<String>, StepExecutionListener {

    @Autowired
    Environment env;

    private final String LOCATION_PATH = "datasource/context-datasource.xml";
    private final String CONTEXT_BEAN = "dataSource";
    private String DDL_Scrip = "";


    @Override
    public void write(List<? extends String> items) throws Exception {

        DDL_Scrip = items.get(0);
        try {

            File file = new File(prop.getProperty("sql.ddl_file") + "DDL_File_" + System.getProperty("tmp_tbl_name") + ".sql");

            FileWriter writer;
            writer = new FileWriter(file, false);
            writer.write(DDL_Scrip);
            writer.close();
            logger.info("Write DDL script to " + file.getPath());
            System.out.println("Write SQL File Success!");
            System.out.println(file.getPath());
            logger.info("Execute DDL file");
            Connection_execute_file connection_execute_file = new Connection_execute_file();
            connection_execute_file.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, file.getPath());


        } catch (Exception e) {
            logger.warning(e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {


    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }
}

@Configuration
class a {

}