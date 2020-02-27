package com.gable.dos.ddq.controller.itemReader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.boot.logging.java.SimpleFormatter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;

import static com.gable.dos.ddq.controller.SpringConfig.*;

@Slf4j
public class reader_csv_file implements ItemReader, JobExecutionListener, StepExecutionListener {

    private static String file_input = "";

    private File folder = null;

    public static boolean returns_step1 = false;
    private String ext1 = "";
    public static List<String> fileNo;


    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (fileNo.size() > 0) {
            file_input = fileNo.get(0);
        } else {
            return null;
        }

        System.out.println("file_input name = " + file_input);

        if (new File(file_input).isFile()) {
            String filename = new File(file_input).getName();
            ext1 = FilenameUtils.getExtension(file_input);
            if (ext1.equals("csv")) {
                if (!returns_step1) {
                    returns_step1 = true;
                    System.setProperty("file_input", file_input);
                    System.setProperty("filename", filename);
                    System.setProperty("part_file_input", prop.getProperty("part.Input") + filename);
                    try {
                        fh = new FileHandler(prop.getProperty("part.logfile") + filename + ".log");
                        logger = Logger.getLogger(filename);
                        logger.addHandler(fh);
                        SimpleFormatter formatter = new SimpleFormatter();
                        fh.setFormatter(formatter);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    logger.info("Open file \"" + filename + "\"");
                    return file_input;
                }
            }
        }
        return null;
    }


    public void search(final String pattern, final File folderNo, List<String> fileNo) {

        for (final File f : folderNo.listFiles()) {

            if (f.isDirectory()) {
                search(pattern, f, fileNo);
            }
            if (f.isFile()) {
                if (f.getName().matches(pattern)) {
                    fileNo.add(f.getPath());
                }
            }
        }
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
//        fileNo.remove(0);
        if (new File(file_input).isFile()) {
            if (!ext1.equals("csv")) {
                log.error("This extension file should be a .CSV, not an ." + ext1);
                return ExitStatus.FAILED;
            }
            return ExitStatus.COMPLETED;
        }
        log.error("Cannot find the " + file_input + " file");
        return ExitStatus.FAILED;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        folder = new File(prop.getProperty("part.initial.csv"));
        // get sum files.
        fileNo = new ArrayList<>();
        System.out.println("\n Directory Path : \"" + folder + "\"");
        System.out.println("...............................................");

        search(".*\\.csv", folder, fileNo);

        System.out.println(":::::::::::::::::::::::::::::");
        System.out.println("Number of File = " + fileNo.size() + " & FileName = " + fileNo);
        System.out.println(":::::::::::::::::::::::::::::");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}
