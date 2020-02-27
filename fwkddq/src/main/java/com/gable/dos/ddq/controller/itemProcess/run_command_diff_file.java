package com.gable.dos.ddq.controller.itemProcess;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;
import java.io.IOException;

import static com.gable.dos.ddq.controller.SpringConfig.logger;
import static com.gable.dos.ddq.controller.SpringConfig.prop;

@Slf4j
public class run_command_diff_file implements ItemProcessor<String, Object>, StepExecutionListener {
    private boolean execute_status = false;

    @Override
    public Object process(String fileExport) throws Exception {
        String templateName = prop.getProperty("part.Input");
        logger.info("get part Input [" + templateName + "]");

        String exportName = fileExport;
        System.out.println("fileExport " + fileExport);
//        System.out.println("fileExport = " + exportName);
//        String exportName = "C:\\Users\\Admin\\Desktop\\ACTUAL_201408_TRIP_DATA_OUTPUT_CSV_RESULT.csv";


        File file_template = new File(templateName);
        File file_export = new File(exportName);


//        if (!file_template.isFile()) {
//            log.error("This file_template " + file_template.getName() + " does not exist");
//            return null;
//        }
//        if (!file_export.isFile()) {
//            log.error("This file_export " + file_export.getName() + " does not exist");
//            return null;
//        }

        runCommandDiffFile(file_template, file_export);
        return null;
    }

    private void runCommandDiffFile(File file_template, File file_export) {

        String command = "\"C:\\Program Files\\WinMerge\\WinMergeU.exe\" \"" + file_template.getAbsolutePath() + "\" \"" + file_export.getAbsolutePath() + "\" /maximize";
        logger.info("Create command run WinMerge");
        logger.info(command);
        log.info("command = " + command);
        try {
            logger.info("Runtime executing command...");
            Runtime.getRuntime().exec(command);
            logger.info("Runtime execute command success");
            execute_status = true;
        } catch (IOException e) {
            execute_status = false;
            logger.warning("Runtime execute command failed");
            e.printStackTrace();
        }
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {


    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info("Spring Batch process data-preparation Success!");
        if (!execute_status) {
            return ExitStatus.FAILED;
        }
        return ExitStatus.COMPLETED;
    }
}
