package com.gable.dos.ddq.controller.itemReader;

import com.gable.dos.ddq.model.TableAudit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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
import java.util.List;

import static com.gable.dos.ddq.controller.SpringConfig.logger;
import static com.gable.dos.ddq.controller.SpringConfig.prop;
import static com.gable.dos.ddq.controller.itemProcess.process_clone_file_csv.header_csv;
import static com.gable.dos.ddq.controller.itemReader.ReadTableDimension.table_dimension;

@Slf4j
@Data
public class readRelationConfig implements ItemReader, StepExecutionListener {
    public static List<String> input;
    public static List<String> stringList;

    private boolean check = false;
    public static boolean check_rule2 = false;

    @Override
    public List<String> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (!check_rule2) {
            if (stringList.size() != 0) {
                return pasercsv(stringList.get(0));
            }
            return null;
        }
        return null;
    }

    private List<String> pasercsv(String s) {
        String[] tokens = s.split(",");
        List<String> b = new ArrayList<>();
        for (String t : tokens) {
            System.out.println(t);
            b.add(t);
        }
        return b;

    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        String path_file = prop.getProperty("part.relation_config");

        BufferedReader bufferedReader = null;

        try {
            // read csv file
            input = new ArrayList<String>();
            File inputFile = new File(path_file); //read output file

            bufferedReader = new BufferedReader(new FileReader(inputFile));
            String readLine = "";
            while ((readLine = bufferedReader.readLine()) != null) {
                input.add(readLine);
            }
            input.remove(0);
            stringList = new ArrayList<>(input);

        } catch (
                IOException e) {
            e.printStackTrace();
        } finally { // Close file
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}


