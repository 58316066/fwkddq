package com.gable.dos.ddq.controller.itemReader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.File;


@Slf4j
public class readCheckFileName implements ItemReader<String> {

    public static boolean returns_step4 = false;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        String FILE_NAME = System.getProperty("part_file_input");

        if (FILE_NAME != null) {
            String ext1 = FilenameUtils.getExtension(FILE_NAME);
            if (!ext1.equals("csv")) {
                log.error("The system needs the file extension \".CSV\", not \"" + ext1 + "\".");
                return null;
            }

            if (!returns_step4) {
                returns_step4 = true;
//                File file = new File(FILE_NAME);
                System.out.println("test = " + FILE_NAME);
                return FILE_NAME;
            }
            return null;

        }
        log.error("FILE_NAME is null");

        return null;
    }
}
