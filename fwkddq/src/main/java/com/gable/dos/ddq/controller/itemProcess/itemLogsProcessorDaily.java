
package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Config_Datetime;
import com.gable.dos.ddq.library.Connection_executes_result_set;
import com.gable.dos.ddq.model.*;
import lombok.Data;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


@Data
@Slf4j
@EnableScheduling
public class itemLogsProcessorDaily implements JobExecutionListener, ItemProcessor<Object, Object> {

    private static String jobName = "";
    public static ArrayList<Tables> object_list = new ArrayList<>();
    public static ArrayList<Columns> columns_list = new ArrayList<>();
    public static ArrayList<String> result_return, result_return2 = new ArrayList<>();

    //    private ApplicationContext context = new ClassPathXmlApplicationContext("datasource/context-postgres.xml");
    private int recordNumber = 0;
    private DriverManagerDataSource contextBean;

    private Connection_executes_result_set connectionLibrary;
    private final String LOCATION_PATH = "datasource/context-Microsoft-SQL-Server.xml";
    private final String CONTEXT_BEAN = "SqlServer";
    //    private final String LOCATION_PATH = "datasource/context-postgres.xml";
//    private final String CONTEXT_BEAN = "myDataSource";
    private String SQL, SQL2;
    private ResultSet rs, rs2;

    /*new class Config_Datetime.java*/
    private Config_Datetime config_datetime = new Config_Datetime();

    /*get Date Config*/
    private Date date = config_datetime.dateConfig();
    private Date dateString = Config_Datetime.dateString;


    @Override
    public Object process(Object result) throws Exception {

//        log.info("(Start Process)" + result.getClass().getName());
        if (result.getClass() == Columns.class) {
            columns_list.add((Columns) result);

        } else if (result.getClass() == Tables.class) {
            object_list.add((Tables) result);

        } else if (result.getClass() == TableAudit.class) {
            if (result != null) {
//                TableAudit tableAudit = (TableAudit) result;
//                tableAudit.setJob_id(jobName);
//                tableAudit.setMonth_id(new Date().getMonth() + 1);
//                tableAudit.setProcessDate(date);
//                tableAudit.setDate(dateString);
                return FuncQueryPostgres((TableAudit) result);
            }

//            return mapListAllTable((TableAudit) result);

        } else if (result.getClass() == SchemaTableLists.class) {

            String schema = (((SchemaTableLists) result).getSchema()).getName();
            log.info("getSchema : [" + schema + "]");

            if (!result_return.contains(schema)) {
                result_return.add(schema);
                return result;

            } else {
                return null;
            }
        }
        return result;
    }


    private Object FuncQueryPostgres(TableAudit table) throws ParseException {

        log.info("(Start Process FuncQueryPostgres this : )"+table);

        // new class ProcessTestInterface
        connectionLibrary = new Connection_executes_result_set();

        // config sql_Query (1)

        SQL = "SELECT count(*) as RecordNo FROM [" + table.getSchema() + "].[" + table.getTable_name() + "]";

        // config sql_Query (2)
        SQL2 = "select count(a.column_name) as FieldNo from information_schema.COLUMNS a " +
                "where  a.TABLE_SCHEMA = '" + table.getSchema() + "' " +
                "and a.TABLE_NAME   = '" + table.getTable_name() + "';";

        //call to method getBeanContext (1)
        rs = connectionLibrary.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);

        try {
            TableAuditCountRecord tableAuditCountRecord = new TableAuditCountRecord();
            while (rs.next()) {

                tableAuditCountRecord.setSchema(table.getSchema());
                tableAuditCountRecord.setTable_name(table.getTable_name());
                tableAuditCountRecord.setTable_type(table.getTable_type());
                tableAuditCountRecord.setProcessDate(date);
                tableAuditCountRecord.setDate(dateString);
//                tableAuditCountRecord.setMonth_id(date.getMonth() + 1);
//                tablePostgres.setProcessDate(new Date());
                tableAuditCountRecord.setCountRecord(rs.getInt("RecordNo"));
                tableAuditCountRecord.setDatasourceName(table.getDatasourceName());
            }
            // call to method getBeanContext (2)
            rs2 = connectionLibrary.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL2);
            while (rs2.next()) {
                tableAuditCountRecord.setFieldNo(rs2.getInt("FieldNo"));
            }
            tableAuditCountRecord.setJob_id(jobName);
            log.info("return => " + tableAuditCountRecord);
            return tableAuditCountRecord;

        } catch (Exception e) {
            System.err.println("Query || Got an exception! ");
            System.err.println(e.getMessage());
        }

        return null;
    }


    private SchemaTableLists mapListAllTable(TableAudit result) {

        String schema = ((result).getSchema());

        if (!result_return2.contains(schema)) {
            result_return2.add(schema);

            log.info("mapListAllTable getSchema : [" + schema + "]");

            /* new model SchemaList */
            SchemaTableLists schemaTableLists2 = new SchemaTableLists();

//        MainOutput mainOutput = new MainOutput();

            /* new model TablesList */
            Schema schema2 = new Schema();
            HashMap<String, String> list_table = new HashMap<>();

            ArrayList<TableAudit> list = ItemSaveTableLists.tablesAudit_lists;

            schema2.setName(result.getSchema());
            schema2.setDesc("descriptions");

//            log.info("tablesAudit_lists = " + list.size() + " ==> " + list);

            for (TableAudit tables : list) {
                if (schema2.getName().equals(tables.getSchema())) {

                    list_table.put(tables.getTable_name(), "Descriptions");

                }
            }
            schema2.setTableList(list_table);

            schemaTableLists2.setProcessDate(date);
            schemaTableLists2.setDate(dateString);
//            schemaTableLists2.setMonth_id(date.getMonth() + 1);
            schemaTableLists2.setJob_id("initialAllTables");
            schemaTableLists2.setSchema(schema2);

            log.info("this List tableName initialAllTables: " + schemaTableLists2);

            return schemaTableLists2;
        }
        return null;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobName = jobExecution.getJobInstance().getJobName();
        log.info("This JobName = " + jobName);

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        result_return2.clear();
        ItemSaveTableLists.tablesAudit_lists.clear();
    }
}
