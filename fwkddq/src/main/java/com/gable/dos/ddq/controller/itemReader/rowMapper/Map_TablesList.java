package com.gable.dos.ddq.controller.itemReader.rowMapper;

import com.gable.dos.ddq.library.Config_Datetime;
import com.gable.dos.ddq.controller.itemProcess.ItemLogsProgress;
import com.gable.dos.ddq.controller.itemProcess.ItemSaveTableLists;
import com.gable.dos.ddq.model.SchemaTableLists;
import com.gable.dos.ddq.model.Schema;
import com.gable.dos.ddq.model.Tables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Slf4j
public class Map_TablesList implements RowMapper<SchemaTableLists> {

    /*new class Config_Datetime.java*/
    private Config_Datetime config_datetime = new Config_Datetime();

    /*get Date Config*/
    private Date date = config_datetime.dateConfig();

    private Date dateString = Config_Datetime.dateString;

    @Override
    public SchemaTableLists mapRow(ResultSet rs, int rowNum) throws SQLException {
        /* Clear list result_return */
        ItemLogsProgress.result_return.clear();
        ItemLogsProgress.object_list.clear();

        /* new model SchemaList */
        SchemaTableLists schemaTableLists = new SchemaTableLists();

//        MainOutput mainOutput = new MainOutput();

        /* new model TablesList */
        Schema schema = new Schema();
        HashMap<String, String> list_table = new HashMap<>();

        ArrayList<Tables> list = ItemSaveTableLists.tables_lists;

        schema.setName(rs.getString("TABLE_SCHEMA"));
        schema.setDesc("descriptions");


        for (Tables tables : list) {
//           log.info("table_lists.size = " + ItemLogsProgress.object_list.size());
            if (schema.getName().equals(tables.getSchema())) {
//              System.out.println("SchemaName is : [" + tables.getSchema() + "]");
                list_table.put(tables.getTable_name(), "Descriptions");
//              list_table.put(list);
//              ItemLogsProgress.object_list.remove(tables);

            }
        }
        schema.setTableList(list_table);

        schemaTableLists.setProcessDate(date);
        schemaTableLists.setDate(dateString);
//        schemaTableLists.setMonth_id(date.getMonth()+1);
        schemaTableLists.setJob_id("initialAllTables");
        schemaTableLists.setSchema(schema);

//        mainOutput.setObject(schema);
//        schemaTableLists.setCreateDate(new Date());

//        schemaTableLists.setJob_id("initialAllTables");
//        schemaTableLists.setSchema(schema);

        log.info("this tableName : " + rs.getString("TABLE_NAME"));

        return schemaTableLists;
    }
}
