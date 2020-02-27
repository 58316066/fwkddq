package com.gable.dos.ddq.controller.itemReader.rowMapper;

import com.gable.dos.ddq.model.TableAudit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
@Slf4j
public class RowMapTestDataSource implements RowMapper {
    @Override
    public TableAudit mapRow(ResultSet rs, int rowNum) throws SQLException {

        TableAudit tableAudit = new TableAudit();
        tableAudit.setTable_name(rs.getString("TABLE_NAME"));
        tableAudit.setSchema(rs.getString("TABLE_SCHEMA"));


        log.info("Date Time = " + 	System.currentTimeMillis());
        log.info("RowMap rs {TABLE_NAME} = " + tableAudit.getTable_name());
        log.info("RowMap rs {TABLE_SCHEMA} = " + tableAudit.getSchema());
        return tableAudit;
    }
}
