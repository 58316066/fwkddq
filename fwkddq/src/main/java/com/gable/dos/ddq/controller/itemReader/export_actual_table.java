package com.gable.dos.ddq.controller.itemReader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import static com.gable.dos.ddq.controller.SpringConfig.logger;
import static com.gable.dos.ddq.controller.SpringConfig.prop;


@Slf4j
public class export_actual_table implements ItemReader<String>, StepExecutionListener {
    private boolean execute_ExportTable = false;
    private String file_output;
    private boolean returns = false;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (!returns) {
            file_output = prop.getProperty("part.Export");
            logger.info("get part Export [" + file_output + "]");
            returns = true;
            return file_output;
        }

        return null;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("execute_ExportTable = " + execute_ExportTable);
        if (!execute_ExportTable) {
            return ExitStatus.FAILED;
        }
        return null;
    }
}
