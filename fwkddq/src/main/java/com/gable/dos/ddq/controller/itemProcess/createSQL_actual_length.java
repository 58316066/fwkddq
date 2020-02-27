package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Connection_executes_result_set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import static com.gable.dos.ddq.controller.SpringConfig.logger;
import static com.gable.dos.ddq.controller.itemWriter.WriterPreparation.hashMap_header;

@Slf4j
public class createSQL_actual_length implements ItemProcessor<String, Object> {
    @Override
    public String process(String sql) throws Exception {
        final String LOCATION_PATH = "datasource/context-datasource.xml";
        final String CONTEXT_BEAN = "dataSource";
        ResultSet rs;
        Connection_executes_result_set connection_executes = new Connection_executes_result_set();

        String TableName = System.getProperty("tmp_tbl_name");
        String fileName = TableName.replace("_tmp", "");
        String new_TableName = fileName.toUpperCase();
        System.setProperty("newTable_Name", new_TableName);

        System.out.println("sql = " + sql);
        HashMap<String, ArrayList<String>> instanceHashMap = hashMap_header;

        System.out.println("instanceHashMap = " + instanceHashMap);

        if (instanceHashMap == null) {
            log.error("instanceHashMap is null!!!");
            return null;
        }

        rs = connection_executes.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, sql);

        logger.info("Create DDL Drop and Create table actual");
        String DDL = "DROP TABLE IF EXISTS `" + new_TableName + "`; \n\n";
        DDL = DDL.concat("CREATE TABLE `" + new_TableName + "` ( \n");
        while (rs.next()) {
            int rsColumnCount = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= rsColumnCount; i++) {
                for (int j = i - 1; j < i; j++) {
                    System.out.println("cellHeaders : " + instanceHashMap.get(System.getProperty("tmp_tbl_name")).get(j));
                    String p1 = instanceHashMap.get(System.getProperty("tmp_tbl_name")).get(j);

                    DDL = DDL.concat("`" + p1 + "` varchar(" + rs.getInt(i) + "), \n");
                }
            }
            int cutIndex = DDL.lastIndexOf(",", DDL.length());
            String sub_ddl = DDL.substring(0, cutIndex);
            sub_ddl = sub_ddl.concat("\n );");
            System.out.println("DDL Script = " + sub_ddl);
            logger.info(sub_ddl);
            return sub_ddl;
        }
        return null;
    }
}
