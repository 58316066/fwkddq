package com.gable.dos.ddq.controller.itemReader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.gable.dos.ddq.controller.SpringConfig.logger;
import static com.gable.dos.ddq.controller.itemProcess.process_clone_file_csv.header_csv;

@Slf4j
public class readerGetSourceFile_csv implements ItemReader<HashMap<String, ArrayList<String>>>, StepExecutionListener {


    private static ArrayList<String> list_header;
    private static HashMap<String, ArrayList<String>> hashMap_header;


    private DataFormatter dataFormatter = new DataFormatter();
    private int i = 0;
    public static boolean returns_step2 = false;
    private boolean checkFile = true;
    private String part_file = null;


    @Override
    public HashMap<String, ArrayList<String>> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!returns_step2) {
            part_file = System.getProperty("part_file_input");

            System.out.println("part_file = " + part_file);

            if (part_file == null) {
                log.error("part_output is null!!");
                return null;
            }
            try (
                    Reader reader = Files.newBufferedReader(Paths.get(part_file));
                    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
            ) {
                File file = new File(part_file);
                System.setProperty("file_name", file.getName());


                String fileName = file.getName().replace(".csv", "_tmp");
                logger.info("Replace \"" + file.getName() + "\" to \"" + fileName + "\"");
                System.out.println("Filename = " + fileName);
                System.setProperty("tmp_tbl_name", fileName);

                hashMap_header = new HashMap<>();
                list_header = new ArrayList<>();
                CSVRecord header_row = csvParser.getRecords().get(0);
                System.out.println("csv = " + header_row.size());
                String cell = header_csv.get(0);
                String[] tokens = cell.split(",");

                for (String t : tokens) {
                    System.out.println(t);
                    list_header.add(t);
                }
                hashMap_header.put(fileName, list_header);
                System.out.println("hashMap_header = " + hashMap_header);

            } catch (Exception e) {
                log.error(e.getMessage());

                checkFile = false;
                return null;
            }

//        return null;

            returns_step2 = true;
            return hashMap_header;
        }
        return null;
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.setProperty("ddl.length", "255");

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        if (!checkFile) {
            return ExitStatus.FAILED;
        }
        return ExitStatus.COMPLETED;
    }
}
