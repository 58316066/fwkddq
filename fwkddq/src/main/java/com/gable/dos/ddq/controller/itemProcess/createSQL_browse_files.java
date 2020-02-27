package com.gable.dos.ddq.controller.itemProcess;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

import static com.gable.dos.ddq.controller.SpringConfig.logger;
import static com.gable.dos.ddq.controller.SpringConfig.prop;

@Slf4j
public class createSQL_browse_files implements ItemProcessor<String, String> {

    @Override
    public String process(@NotNull String filename) throws Exception {
        log.info("\n\nStarting Create SQL Bulk Load Files...\n");
        logger.info("Create SQL bulk load file");

        if (System.getProperty("file_name") != null) {
            logger.info("Get Property file name [" + filename + "]");
            logger.info("Get Property table name [" + System.getProperty("tmp_tbl_name") + "]");
            String SQL_LoadFile = "LOAD DATA LOCAL INFILE '" + filename + "' INTO TABLE " + System.getProperty("tmp_tbl_name") + "\n" +
                    " FIELDS TERMINATED BY ','\n LINES TERMINATED BY '\\n'";

            log.info("\n\n Create SQL Bulk Load Files Success!! ==> \n" + SQL_LoadFile + "\n\n");
            logger.info(SQL_LoadFile);
            File file = new File(prop.getProperty("sql.bulk_load") + "./Bulk_Load_" + System.getProperty("tmp_tbl_name") + ".sql");

            FileWriter writer;
            writer = new FileWriter(file, false);
            writer.write(SQL_LoadFile);
            writer.close();
            logger.info("Write SQL bulk load file to " + file.getPath());
            return SQL_LoadFile;
        }
        logger.warning("System.getProperty \"FileName\" is Null!");
        log.info("\n System.getProperty \"FileName\" is Null! \n\n");

        return null;
    }
}

