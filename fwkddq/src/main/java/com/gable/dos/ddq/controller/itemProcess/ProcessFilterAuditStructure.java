package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Config_Datetime;
import com.gable.dos.ddq.library.Connection_executes_result_set;
import com.gable.dos.ddq.library.GetLastVersion;
import com.gable.dos.ddq.model.TableAudit;
import com.gable.dos.ddq.model.TableAuditStructure;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.ResultSet;
import java.util.*;

import static com.gable.dos.ddq.controller.SetQueryGetTableConfig.check_setQuery;
import static com.gable.dos.ddq.controller.SetQueryGetTableConfig.result_list;
import static com.gable.dos.ddq.library.Config_Datetime.date_string;

@Slf4j
@Data
public class ProcessFilterAuditStructure implements ItemProcessor<TableAudit, TableAuditStructure>, JobExecutionListener, StepExecutionListener {
    private MongoOperations mongoOperations;
    private String collection_name;
    private int lastVersion = 0;
    private static String jobName = "";
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
    public TableAuditStructure process(TableAudit item) throws Exception {

        // config sql_Query (1)
        SQL = "select count(a.COLUMN_NAME) as count, a.table_name " +
                "from information_schema.COLUMNS a " +
                "where a.TABLE_SCHEMA = '" + item.getSchema() + "' " +
                "and a.TABLE_NAME  = '" + item.getTable_name() + "' " +
                "group by a.TABLE_NAME;";

        SQL2 = "select  a.COLUMN_NAME ,a.data_type, concat('(',case when a.character_maximum_length is null then a.numeric_precision else a.character_maximum_length end, ')') as concat " +
                "from information_schema.COLUMNS a " +
                "where  a.TABLE_SCHEMA = '" + item.getSchema() + "' " +
                "and a.TABLE_NAME   = '" + item.getTable_name() + "' " +
                "order by a.ORDINAL_POSITION; ";


        return queryStructure(item);

    }

    private TableAuditStructure queryStructure(TableAudit item) {

        connectionLibrary = new Connection_executes_result_set();

        // Process SQL 1
        rs = connectionLibrary.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);
        try {
            TableAuditStructure tableAuditStructure = new TableAuditStructure();

            while (rs.next()) {
                tableAuditStructure.setTable_name(rs.getString("table_name"));
                tableAuditStructure.setSchema(item.getSchema());
                tableAuditStructure.setFieldNo(rs.getInt("count"));
                tableAuditStructure.setProcessDate(date);
                tableAuditStructure.setDate(dateString);
//                tableAuditStructure.setMonth_id(date.getMonth() + 1);
                tableAuditStructure.setDatasourceName(item.getDatasourceName());
                tableAuditStructure.setDate_string(date_string);
            }

            // Process SQL 2
            rs2 = connectionLibrary.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL2);
            ArrayList<Object> arrayList = new ArrayList<>();

//            Set name = new HashSet();
//            List type = new ArrayList();
            while (rs2.next()) {

                HashMap<String, Object> field = new HashMap<>();
                field.put("index",rs2.getRow());
                field.put("name", rs2.getString("column_name"));
                field.put("type", rs2.getString("data_type") + "" + rs2.getString("concat"));

                arrayList.add(field);

//                name.add(rs2.getString("column_name"));
//                type.add(rs2.getString("data_type") + "" + rs2.getString("concat"));

                tableAuditStructure.setFieldList(arrayList);
                tableAuditStructure.setJob_id(jobName);
                tableAuditStructure.setTable_type("N/A");
                tableAuditStructure.setVersion_No(lastVersion + 1);


            }

//            tableAuditStructure.setName(name);
//            tableAuditStructure.setType(type);
//            log.info("(Start Process TableAuditStructure.class)");
            log.info("return => " + tableAuditStructure);
//            log.info("ProcessDate = " + tableAuditStructure.getProcessDate());

            if (result_list.size() != 0) {
                check_setQuery = false;
                result_list.remove(0);
            } else {
                check_setQuery = true;
            }
            return tableAuditStructure;

        } catch (Exception e) {
            System.err.println("Query || Got an exception! ");
            System.err.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobName = jobExecution.getJobInstance().getJobName();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("BeforeStep Started");
        GetLastVersion getLastVersion = new GetLastVersion();
        lastVersion = getLastVersion.getLast_version(mongoOperations, collection_name, stepExecution.getJobExecution().getJobInstance().getJobName());
        System.out.println("lastVersion : " + lastVersion);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
