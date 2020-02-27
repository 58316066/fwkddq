package com.gable.dos.ddq.controller.itemReader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;


import static com.gable.dos.ddq.controller.SpringConfig.logger;
import static com.gable.dos.ddq.controller.itemWriter.WriterPreparation.ddl;

public class readerDDLScript implements ItemReader<String> {
    String ddl_step1 = "";
    public static boolean returns_step3 = false;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (!returns_step3) {
            ddl_step1 = ddl;

            if (!ddl_step1.isEmpty()) {
//                System.out.println("\nStep 2 Reader : " + ddl_step1 + "\n");
                logger.info("Get DDL script");
                returns_step3 = true;
                return ddl_step1;
            }

            System.out.println("DDL Script is null!");
            logger.warning("DDL Script is null!");
            return null;
        }
        return null;
    }
}
