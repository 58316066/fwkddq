package com.gable.dos.ddq.controller.itemProcess;

import com.gable.dos.ddq.library.Connection_executes_result_set;
import com.gable.dos.ddq.model.TableAudit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.sql.ResultSet;
import java.util.Date;

@Slf4j
public class ItemProcessTestDataSource implements ItemProcessor<TableAudit, Object> {
    private Connection_executes_result_set connectionLibrary;
    private final String LOCATION_PATH = "datasource/context-Microsoft-SQL-Server.xml";
    private final String CONTEXT_BEAN = "SqlServer";
    private String SQL;
    private ResultSet rs;

    @Override
    public Object process(TableAudit tableAudit) throws Exception {
        connectionLibrary = new Connection_executes_result_set();

        SQL = "SELECT count(*) as RecordNo FROM [" + tableAudit.getSchema() + "].[" + tableAudit.getTable_name() + "]";

        rs = connectionLibrary.getBeanContext(LOCATION_PATH, CONTEXT_BEAN, SQL);

        log.info("SQL = " + SQL);

        tableAudit.setProcessDate(new Date());
        tableAudit.setJob_id("TestDataSource");
//        tableAudit.setMonth_id(new Date().getMonth() + 1);


        while (rs.next()) {
            System.out.println("setRecordNumber = " + rs.getInt("RecordNo"));
//            tableAudit.setRecordNumber(rs.getInt("RecordNo"));
            return tableAudit;
        }

        return null;
    }
}
