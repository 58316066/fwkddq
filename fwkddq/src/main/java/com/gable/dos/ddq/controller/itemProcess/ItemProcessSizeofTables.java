package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Config_Datetime;
import com.gable.dos.ddq.library.Connection_executes_result_set;
import com.gable.dos.ddq.library.GetLastVersion;
import com.gable.dos.ddq.model.SizeofTables;
import com.gable.dos.ddq.model.TableAudit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.mongodb.core.MongoOperations;

import java.sql.ResultSet;
import java.util.Date;

import static com.gable.dos.ddq.controller.SetQueryGetTableConfig.check_setQuery;
import static com.gable.dos.ddq.controller.SetQueryGetTableConfig.result_list;
import static com.gable.dos.ddq.library.Config_Datetime.date_string;

@Slf4j
@Data
public class ItemProcessSizeofTables implements JobExecutionListener, StepExecutionListener, ItemProcessor<TableAudit, Object> {
    private MongoOperations mongoOperations;
    private String collection_name;
    private int lastVersion = 0;
    private Connection_executes_result_set connectionLibrary;
    private final String LOCATION_PATH = "datasource/context-Microsoft-SQL-Server.xml";
    private final String CONTEXT_BEAN = "SqlServer";
    private String SQL;
    private ResultSet rs;
    private static String jobName = "";
    /*new class Config_Datetime.java*/
    private Config_Datetime config_datetime = new Config_Datetime();

    /*get Date Config*/
    private Date date = config_datetime.dateConfig();
    private Date dateString = Config_Datetime.dateString;


    @Override
    public Object process(TableAudit tableAudit) throws Exception {

        connectionLibrary = new Connection_executes_result_set();
        SQL = "SELECT \n" +
                "    t.NAME AS TableName,\n" +
                "    s.Name AS SchemaName,\n" +
                "    p.rows AS RowCounts,\n" +
                "    --SUM(a.total_pages) * 8 AS TotalSpaceKB, \n" +
                "    CAST(ROUND(((SUM(a.total_pages) * 8) / 1024.00), 2) AS NUMERIC(36, 2)) AS TotalSpaceMB,\n" +
                "    --SUM(a.used_pages) * 8 AS UsedSpaceKB, \n" +
                "    CAST(ROUND(((SUM(a.used_pages) * 8) / 1024.00), 2) AS NUMERIC(36, 2)) AS UsedSpaceMB, \n" +
                "    --(SUM(a.total_pages) - SUM(a.used_pages)) * 8 AS UnusedSpaceKB,\n" +
                "    CAST(ROUND(((SUM(a.total_pages) - SUM(a.used_pages)) * 8) / 1024.00, 2) AS NUMERIC(36, 2)) AS UnusedSpaceMB\n" +
                "FROM \n" +
                "    sys.tables t\n" +
                "INNER JOIN      \n" +
                "    sys.indexes i ON t.OBJECT_ID = i.object_id\n" +
                "INNER JOIN \n" +
                "    sys.partitions p ON i.object_id = p.OBJECT_ID AND i.index_id = p.index_id\n" +
                "INNER JOIN \n" +
                "    sys.allocation_units a ON p.partition_id = a.container_id\n" +
                "LEFT OUTER JOIN \n" +
                "    sys.schemas s ON t.schema_id = s.schema_id\n" +
                "WHERE \n" +
                "\t\tt.name = '" + tableAudit.getTable_name() + "'\n" +
                "\tAND t.is_ms_shipped = 0\n" +
                "    AND i.OBJECT_ID > 255 \n" +
                "GROUP BY \n" +
                "    t.Name, s.Name, p.Rows\n" +
                "ORDER BY \n" +
                "    t.Name\n";

        rs = connectionLibrary.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);

//        log.info("SQL = " + SQL);

        SizeofTables sizeofTables = new SizeofTables();

//        tableAuditCountRecord.setProcessDate(new Date());
//        tableAuditCountRecord.setJob_id("AuditSizeofTables");
//        tableAuditCountRecord.setMonth_id(new Date().getMonth() + 1);

        while (rs.next()) {
            System.out.println("TableName = " + rs.getString("TableName"));
            System.out.println("SchemaName = " + rs.getString("SchemaName"));
            System.out.println("RowCounts = " + rs.getInt("RowCounts"));
            System.out.println("TotalSpaceMB = " + rs.getDouble("TotalSpaceMB"));
            System.out.println("UsedSpaceMB = " + rs.getDouble("UsedSpaceMB"));
            System.out.println("UnusedSpaceMB = " + rs.getDouble("UnusedSpaceMB"));

            sizeofTables.setTable_name(rs.getString("TableName"));
            sizeofTables.setSchema(rs.getString("SchemaName"));
            sizeofTables.setCountRecord(rs.getInt("RowCounts"));
            sizeofTables.setTotalSpaceMB(rs.getDouble("TotalSpaceMB"));
            sizeofTables.setUsedSpaceMB(rs.getDouble("UsedSpaceMB"));
            sizeofTables.setUnusedSpaceMB(rs.getDouble("UsedSpaceMB"));
            sizeofTables.setJob_id(jobName);
            sizeofTables.setProcessDate(date);
            sizeofTables.setDate(dateString);
//            sizeofTables.setMonth_id(new Date().getMonth() + 1);
            sizeofTables.setDatasourceName(tableAudit.getDatasourceName());
            sizeofTables.setTable_type("N/A");
            sizeofTables.setDate_string(date_string);
            sizeofTables.setVersion_No(lastVersion + 1);

            if (result_list.size() != 0) {
                check_setQuery = false;
                result_list.remove(0);
            } else {
                check_setQuery = true;
            }

            return sizeofTables;

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
