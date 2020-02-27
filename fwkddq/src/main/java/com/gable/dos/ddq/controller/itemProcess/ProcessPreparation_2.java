package com.gable.dos.ddq.controller.itemProcess;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import static com.gable.dos.ddq.controller.SpringConfig.logger;

public class ProcessPreparation_2 implements ItemProcessor<String, String>, StepExecutionListener {

    @Override
    public String process(String srt_ddl) throws Exception {

        if (!srt_ddl.isEmpty()) {
            System.out.println("prop.getProperty(\"ddl.length\") = " + System.getProperty("ddl.length"));
            return replaceDDL(srt_ddl, System.getProperty("ddl.length")); // default 255 ;
        }

        System.out.println("DDL Script is null!");
        return null;
    }

    public String replaceDDL(String ddl, String length) {

        if (!ddl.isEmpty()) {
            String ddl_replace = ddl.replace("xxx", length);
            logger.info("Replace DDL script length(xxx) to length(" + length + ")");
            logger.info(ddl_replace);
            return ddl_replace;
        }
        logger.warning("DDL Script is null!");
        System.out.println("DDL Script is null!");
        return null;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
