package com.gable.dos.ddq.controller.itemWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.gable.dos.ddq.controller.SpringConfig.logger;

public class WriterPreparation implements org.springframework.batch.item.ItemWriter {
    public static String ddl = "";
    public static HashMap<String, ArrayList<String>> hashMap_header;
//    int i = 0;

    @Override
    public void write(List items) throws Exception {

        System.out.println("ItemWriter : " + items);
        hashMap_header = (HashMap<String, ArrayList<String>>) items.get(0);

//        int i = hashMap.size() - 1;

        System.out.println("Size hashMap WriterPreparation = " + hashMap_header.size());
        logger.info("Create DDL script varchar(xxx)");
        String DDL = "";
        ddl = "";
        for (String key : hashMap_header.keySet()) {
            // for MariaDB
            DDL = "DROP TABLE IF EXISTS `" + key + "`; \n\n";
            DDL = DDL.concat("CREATE TABLE `" + key + "` ( \n");
            for (String cellHeader : hashMap_header.get(key)) {
                System.out.println("cellHeader : " + cellHeader);
                DDL = DDL.concat("`" + cellHeader + "` varchar(xxx), \n");
            }
            int cutIndex = DDL.lastIndexOf(",", DDL.length());
            String sub_ddl = DDL.substring(0, cutIndex);
            sub_ddl = sub_ddl.concat("\n );");

            // set DDL to static String
            ddl = ddl.concat("\n" + sub_ddl);
        }
        logger.info(ddl);
        System.out.println("\nDDL Script :: " + ddl);
    }
}


