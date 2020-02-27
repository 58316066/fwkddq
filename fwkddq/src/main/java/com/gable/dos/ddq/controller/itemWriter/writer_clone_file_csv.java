package com.gable.dos.ddq.controller.itemWriter;

import org.springframework.batch.item.ItemWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static com.gable.dos.ddq.controller.SpringConfig.logger;
import static com.gable.dos.ddq.controller.SpringConfig.prop;

public class writer_clone_file_csv implements ItemWriter<List<String>> {
    public static ArrayList<String> headrerList = new ArrayList<>();

    @Override
    public void write(List<? extends List<String>> items) {
        PrintWriter p = null;
        System.out.println("path = " + prop.getProperty("part.Input") + new File(System.getProperty("file_input")).getName());

        try {
            logger.info("Clone file .csv from " + prop.getProperty("part.initial.csv") + " to " + prop.getProperty("part.Input"));
            p = new PrintWriter(new FileWriter(prop.getProperty("part.Input") + new File(System.getProperty("file_input")).getName(), false));
            logger.info("Clone file .csv success");
        } catch (IOException e) {
            logger.warning("Exception clone file .csv!!!");
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
        for (String row : items.get(0)) {
            p.println(row);
        }
        if (p != null) {
            logger.info("PrintWriter.close Success");
            p.close();
        }
    }
}

