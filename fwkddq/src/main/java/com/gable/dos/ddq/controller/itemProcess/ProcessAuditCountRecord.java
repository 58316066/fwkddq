package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Config_Datetime;
import com.gable.dos.ddq.library.Connection_executes_result_set;
import com.gable.dos.ddq.library.GetLastVersion;
import com.gable.dos.ddq.model.TableAudit;
import com.gable.dos.ddq.model.TableAuditCountRecord;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.mongodb.core.MongoOperations;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Date;

import static com.gable.dos.ddq.controller.SetQueryGetTableConfig.check_setQuery;
import static com.gable.dos.ddq.controller.SetQueryGetTableConfig.result_list;
import static com.gable.dos.ddq.library.Config_Datetime.date_string;

@Slf4j
@Data
public class ProcessAuditCountRecord implements JobExecutionListener, StepExecutionListener, ItemProcessor<Object, Object> {
    private MongoOperations mongoOperations;
    private String collection_name;
    private int lastVersion = 0;
    private static String jobName = "";
    private Connection_executes_result_set connectionLibrary;
    private final String LOCATION_PATH = "datasource/context-Microsoft-SQL-Server.xml";
    private final String CONTEXT_BEAN = "SqlServer";
    private String SQL, SQL2;
    private ResultSet rs, rs2;

    /*new class Config_Datetime.java*/
    private Config_Datetime config_datetime = new Config_Datetime();

    /*get Date Config*/
    private Date date = config_datetime.dateConfig();
    private Date dateString = Config_Datetime.dateString;

    @Override
    public Object process(Object object) throws Exception {

        if (object.getClass().equals(TableAudit.class)) {
            return FuncQueryCountRecord((TableAudit) object);
        }
        return null;
    }


    private Object FuncQueryCountRecord(TableAudit table) throws ParseException {


        // new class ProcessTestInterface
        connectionLibrary = new Connection_executes_result_set();

        // config sql_Query (1)

        SQL = "SELECT count(*) as RecordNo FROM [" + table.getSchema() + "].[" + table.getTable_name() + "]";


        // config sql_Query (2)
        SQL2 = "select count(a.column_name) as FieldNo from information_schema.COLUMNS a " +
                "where  a.TABLE_SCHEMA = '" + table.getSchema() + "' " +
                "and a.TABLE_NAME   = '" + table.getTable_name() + "';";

        System.out.println("SQL = " + SQL);
        System.out.println("SQL2  = " + SQL2);


        //call to method getBeanContext (1)
        rs = connectionLibrary.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);

        try {
            TableAuditCountRecord tableAuditCountRecord = new TableAuditCountRecord();
            while (rs.next()) {

                tableAuditCountRecord.setSchema(table.getSchema());
                tableAuditCountRecord.setTable_name(table.getTable_name());
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
            tableAuditCountRecord.setDate_string(date_string);
            tableAuditCountRecord.setTable_type("N/A");
            tableAuditCountRecord.setVersion_No(lastVersion + 1);
            log.info("return => " + tableAuditCountRecord);

            if (result_list.size() != 0) {
                check_setQuery = false;
                result_list.remove(0);
            } else {
                check_setQuery = true;
            }


            return tableAuditCountRecord;

        } catch (Exception e) {
            System.err.println("Query || Got an exception! ");
            System.err.println(e.getMessage());
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
