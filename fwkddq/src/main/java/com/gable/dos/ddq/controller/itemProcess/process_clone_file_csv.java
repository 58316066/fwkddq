package com.gable.dos.ddq.controller.itemProcess;

import org.springframework.batch.item.ItemProcessor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.gable.dos.ddq.controller.SpringConfig.logger;


public class process_clone_file_csv implements ItemProcessor<String, List<String>> {
    public static List<String> header_csv;

    @Override
    public List<String> process(String path) throws Exception {

        return addColumn(path);

    }

    private List<String> addColumn(String path_file) {
        BufferedReader bufferedReader = null;

        String sep = ",";
        String newCol = "NewLine";

        try {
            // read csv file
            List<String> input = new ArrayList<String>();
            File inputFile = new File(path_file); //read output file

            bufferedReader = new BufferedReader(new FileReader(inputFile));
            String readLine = "";
            while ((readLine = bufferedReader.readLine()) != null) {
                input.add(readLine);
            }

            int numOfRecords = input.size();
            System.out.println("numOfRecords = " + numOfRecords);
            logger.info("Add header \"NewLine\"");
            if (numOfRecords > 1) {
                header_csv = new ArrayList<String>();
                List<String> output = new ArrayList<String>();
                String header = input.get(0) + sep + newCol;
                header_csv.add(header);
                logger.info("Add number of records \"NewLine\"");

                for (int i = 1; i < numOfRecords; i++) {
                    // I am simply going to get the last column from record
                    String row = input.get(i);
                    StringBuilder sb = new StringBuilder(row);
                    String[] entries = row.split(sep); // ","
                    int length = entries.length;
                    if (length > 0) {
                        sb.append(sep);
                        sb.append(i);
                        output.add(String.valueOf(sb));
                    }
                }
                return output;
            } else {
                logger.warning("No records to process");
                System.out.println("No records to process");
            }
        } catch (IOException e) {
            logger.warning(e.getMessage());
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
        return null;
    }
}
