package com.gable.dos.ddq.controller.itemReader.rowMapper;

import com.gable.dos.ddq.model.TableRecNo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@PropertySource("classpath:mongoconfig.properties")
public class CountRecodeMapper implements RowMapper<TableRecNo> {

    @Autowired
    Environment env;

    @Value("${collection.name}")
    private Properties properties;

    @Value("${schema.name}")
    private Properties pro_schema;

    @Override
    public TableRecNo mapRow(ResultSet rs, int rowNum) throws SQLException {
        TableRecNo tableRecNo = new TableRecNo();
//
//        System.setProperty("initialize.Datasource", "true");
//        String initDB = env.getProperty("initialize.Datasource");
//        log.info("initialize.Datasource = " + initDB);
//
//
//        Record record = new Record();
//        log.info("listlist" + list.size());

//        for (SchemaTableLists schemaTableLists : dailyItemReaders) {
//            System.setProperty("collection.name", table.getName());
//            System.setProperty("schema.name", table.getSchema());
//
//            Set<String> a = schemaTableLists.getSchema().getTableList().keySet();
//            log.info("schemaTableLists: " + schemaTableLists.getSchema().getTableList().keySet());




//        }
        tableRecNo.setTable_name("gg");
        tableRecNo.setSchema("schema");
        tableRecNo.setTableRecNo(rs.getInt(1));
//      log.info("JOBNAME=" + rs.getString("JOB_NAME"));

        return tableRecNo;
    }
}