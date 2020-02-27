package com.gable.dos.ddq.controller.itemReader.rowMapper;

import com.gable.dos.ddq.controller.itemProcess.ItemLogsProgress;
import com.gable.dos.ddq.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class Map_FieldList implements RowMapper<TableFieldLists> {


    @Override
    public TableFieldLists mapRow(ResultSet rs, int rowNum) throws SQLException {

        /* new model SchemaList */
        TableFieldLists tableFieldLists = new TableFieldLists();

        /* new Object ArrayList */
        ArrayList<Columns> column_list_obj = new ArrayList<>();

        /* get object_list(static) from ItemLogsProgress.class */
        ArrayList<Columns> columns_list = new ArrayList<>(ItemLogsProgress.columns_list);
//        log.info("columns_list: " + columns_list);


        tableFieldLists.setCreateDate(new Date());
        tableFieldLists.setCreateRecord(new Date());
        tableFieldLists.setUpdateDate(new Date());
        tableFieldLists.setTable_name(rs.getString("TABLE_NAME"));
        tableFieldLists.setDesc("Description");


        for (Columns columns : columns_list) {
//            log.info("columns_list.size = " + ItemLogsProgress.columns_list.size());

            if (tableFieldLists.getTable_name().equals(columns.getTable_name())) {

//                System.out.println("SchemaName is : [" + columns.getSchema() + "]");
                column_list_obj.add(columns);
                ItemLogsProgress.object_list.remove(columns);

            }
        }
        tableFieldLists.setFieldList(column_list_obj);


//        log.info("this schemaList : "+ schemaList);

        return tableFieldLists;
    }
}
