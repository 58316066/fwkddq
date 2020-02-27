package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Config_Datetime;
import com.gable.dos.ddq.library.Connection_executes_result_set;
import com.gable.dos.ddq.model.TableAuditStructure;
import com.gable.dos.ddq.model.TableAuditCountRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.sql.ResultSet;
import java.util.Date;

@Slf4j
public class itemLogsProcessorTestLib implements ItemProcessor<TableAuditCountRecord, TableAuditStructure> {

    private Connection_executes_result_set connectionLibrary;
    private final String LOCATION_PATH = "datasource/context-postgres.xml";
    private final String CONTEXT_BEAN = "myDataSource";
    private String SQL;
    private ResultSet rs;

    /*new class Config_Datetime.java*/
    private Config_Datetime config_datetime = new Config_Datetime();

    /*get Date Config*/
    private Date date = config_datetime.dateConfig();

    private Date dateString = Config_Datetime.dateString;


    @Override
    public TableAuditStructure process(TableAuditCountRecord item) throws Exception {
        connectionLibrary = new Connection_executes_result_set();

        SQL = "select count(a.COLUMN_NAME), a.table_name " +
                "from information_schema.COLUMNS a " +
                "where a.TABLE_SCHEMA = '" + item.getSchema() + "' " +
                "and a.TABLE_NAME   = '" + item.getTable_name() + "' " +
                "group by a.TABLE_NAME;";

        rs = connectionLibrary.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);

        while (rs.next()) {
            TableAuditStructure auditStructure = new TableAuditStructure();
            auditStructure.setProcessDate(date);
            auditStructure.setDate(dateString);
//            auditStructure.setMonth_id(date.getMonth()+1);
            auditStructure.setFieldNo(rs.getInt("count"));
            auditStructure.setSchema(item.getSchema());
            auditStructure.setTable_name(rs.getString("table_name"));
            log.info("PleaseWait...");
            return auditStructure;
        }
        return null;
    }
}
